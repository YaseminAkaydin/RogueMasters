package de.rougemaster.dungeon;

import com.example.grpc.*;
import com.google.gson.reflect.TypeToken;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.lobby.JSONManager;
import de.rougemaster.dungeon.lobby.LobbyFacade;
import de.rougemaster.dungeon.lobby.LobbyMessage;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DungeonApplication {
    private static final Logger logger = Logger.getLogger(DungeonApplication.class.getName());
    private Server server;

    private void start() throws IOException {
        int port = 8811;
        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new GameServiceImpl())
                .addService(new ManageServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");

                try {
                    DungeonApplication.this.stop();
                } catch (InterruptedException var2) {
                    var2.printStackTrace(System.err);
                }

                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (this.server != null) {
            this.server.shutdown().awaitTermination(30L, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DungeonApplication server = new DungeonApplication();
        server.start();
        server.blockUntilShutdown();
    }

    static class GameServiceImpl extends GameServiceGrpc.GameServiceImplBase {
        private final CopyOnWriteArrayList<StreamObserver<GameCommandResponse>> clients = new CopyOnWriteArrayList<>();
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        GameServiceImpl() {
            startGameStateUpdates();
        }

        @Override
        public StreamObserver<GameCommandRequest> sendGameCommand(StreamObserver<GameCommandResponse> responseObserver) {
            clients.add(responseObserver);

            return new StreamObserver<GameCommandRequest>() {
                @Override
                public void onNext(GameCommandRequest request) {
                    // Handle the incoming message from the client
                    // For example, update the game state based on the command
                    System.out.println("Received command: " + request.getCommand() + " " + request.getTarget());

                }

                @Override
                public void onError(Throwable t) {
                    // Handle error
                    logger.info("Error: " + t.getMessage());
                    clients.remove(responseObserver);
                }

                @Override
                public void onCompleted() {
                    // Complete the response stream
                    clients.remove(responseObserver);
                    responseObserver.onCompleted();
                }
            };
        }

        private void startGameStateUpdates() {
            scheduler.scheduleAtFixedRate(() -> {
                if (clients.isEmpty()) return;
                GameCommandResponse response = buildGameStateResponse();
                for (StreamObserver<GameCommandResponse> client : clients) {
                    try {
                        client.onNext(response);
                    } catch (Exception e) {
                        clients.remove(client);
                    }
                }
            }, 0, 5, TimeUnit.SECONDS);
        }

        private GameCommandResponse buildGameStateResponse() {
            // Build response based on game state
            GameState gameState = new GameState(
                    List.of(new PlayableCharacter())
                    ,List.of()
                    ,new Dungeon(10, 2).getRoomList()
                    .stream()
                    .map((Room::getRoomMessage))
                    .toList());
            String message = new JSONManager<GameState>(new TypeToken<>(){}).write(gameState);
            return GameCommandResponse.newBuilder().setMessage(message).build();
        }

    }
    // Dungeon: { Room: { id: 10, connectionMap: {}}, Player: {id: 2, name: HossoDerBabo, attack: 1000}}

    static class ManageServiceImpl extends ManageServiceGrpc.ManageServiceImplBase {
        static int clientID = 0;

        @Override
        public void joinLobby(JoinLobbyRequest request, StreamObserver<JoinLobbyResponse> responseObserver) {
            int lobbyID = request.getLobbyID();
            boolean success = true; // or some logic to determine if joining was successful
            int characterID = clientID++; // Implement this method to generate a character ID
            System.out.println("Lobby joined: " + lobbyID + " " + success + " " + characterID);
            // Build the response
            JoinLobbyResponse response = JoinLobbyResponse.newBuilder()
                    .setSuccess(success)
                    .setLobbyID(lobbyID)
                    .setCharacterID(characterID)
                    .build();

            // Send the response back to the client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

    }


}
