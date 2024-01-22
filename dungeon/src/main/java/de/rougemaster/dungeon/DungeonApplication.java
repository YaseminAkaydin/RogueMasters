package de.rougemaster.dungeon;

import com.example.grpc.*;
import de.rougemaster.dungeon.enemy.EnemyFacade;
import de.rougemaster.dungeon.lobby.LobbyCharType;
import de.rougemaster.dungeon.lobby.LobbyFacade;
import de.rougemaster.dungeon.lobby.LobbyMessage;
import de.rougemaster.dungeon.lobby.messageData.JoinLobbyResponseMessage;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Server class, starts the server and handles the incoming requests
 */
public class DungeonApplication {
    private static final Logger logger = Logger.getLogger(DungeonApplication.class.getName());
    private Server server;
    private LobbyFacade lobbyFacade;
    private int port = 8812;
        /**
     * The main method to start the DungeonApplication server. Creates a server instance, starts it, and
     * blocks until the server is shut down.
     *
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs during server start-up.
     * @throws InterruptedException If the shutdown process is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        DungeonApplication server = new DungeonApplication();
        server.start();
        server.blockUntilShutdown();
    }

    /**
     * Starts the DungeonApplication server. Initializes the lobby facade and game service, sets up
     * the gRPC server, and attaches a shutdown hook to gracefully stop the server upon JVM shutdown.
     *
     * @throws IOException If an I/O error occurs during server start-up.
     */
    private void start() throws IOException {
        lobbyFacade = LobbyFacade.getInstance();

        GameServiceImpl gameService = new GameServiceImpl(lobbyFacade);
        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(gameService)
                .addService(new ManageServiceImpl(lobbyFacade))
                .build()
                .start();

        lobbyFacade.setServer(server);
        lobbyFacade.setGameService(gameService);
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

    /**
     * Gracefully shuts down the server. Waits for up to 30 seconds for server termination.
     *
     * @throws InterruptedException If the shutdown process is interrupted.
     */
    private void stop() throws InterruptedException {
        if (this.server != null) {
            this.server.shutdown().awaitTermination(30L, TimeUnit.SECONDS);
        }
    }

    /**
     * Blocks the current thread until the server is terminated, allowing the server to keep running.
     *
     * @throws InterruptedException If the await process is interrupted.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

    /**
     * Implementation of the GameService gRPC service. Manages client connections and streams for game
     * command requests and responses. Utilizes a lobby facade for game logic operations.
     */
    public static class GameServiceImpl extends GameServiceGrpc.GameServiceImplBase {
        private final CopyOnWriteArrayList<StreamObserver<GameCommandResponse>> clients = new CopyOnWriteArrayList<>();
        private final Map<Integer, StreamObserver<GameCommandResponse>> clientToStreamConnection = new HashMap<>();
        private LobbyFacade lobbyFacade;


        /**
         * Constructs a new GameServiceImpl with the specified lobby facade. Initializes client connections.
         *
         * @param lobbyFacade The lobby facade used for game logic operations.
         */
        public GameServiceImpl(LobbyFacade lobbyFacade) {
            this.lobbyFacade = lobbyFacade;
            this.lobbyFacade.setClientsToConnection(clientToStreamConnection);
        }

        /**
         * Method to handle incoming game command requests. Manages client connections and streams, processes
         * game commands, and handles errors and completion of client streams.
         *
         * @param responseObserver The StreamObserver for sending responses back to the client.
         * @return A new StreamObserver for handling GameCommandRequest objects.
         */
        @Override
        public StreamObserver<GameCommandRequest> sendGameCommand(StreamObserver<GameCommandResponse> responseObserver) {
            clients.add(responseObserver);
            /*System.out.println("Client connected, total clients: " + clients.size());*/

            return new StreamObserver<GameCommandRequest>() {
                private Integer userId = null;

                @Override
                public void onNext(GameCommandRequest request) {
                    System.out.println("Received command: " + request.getCommand() + " " + request.getTarget() + " " + request.getUserId());


                    if (request.getCommand().equals("initialize") && lobbyFacade.isClientIdExisting(request.getUserId())) {
                        userId = request.getUserId();
                        clientToStreamConnection.put(userId, responseObserver);
                    } else if (clientToStreamConnection.containsKey(request.getUserId())) {
                        lobbyFacade.setGameCommand(request.getUserId(), new LobbyMessage(request.getCommand(), request.getTarget()));
                    }

                }

                @Override
                public void onError(Throwable t) {
                    logger.log(Level.INFO, "Error in sendGameCommand: " + t.getMessage());
                    // Remove the client from the list and the map
                    if (userId != null) {
                        clientToStreamConnection.remove(userId);
                        /*lobbyFacade.deleteUser(userId);*/
                    }
                    clients.remove(responseObserver);
                }

                @Override
                public void onCompleted() {
                    logger.info("Completed Server sendGameCommand, removing client");
                    // Remove the client from the list and the map
                    if (userId != null) {
                        clientToStreamConnection.remove(userId);
                        /*lobbyFacade.deleteUser(userId);*/
                    }
                    clients.remove(responseObserver);
                    responseObserver.onCompleted();
                }
            };
        }

    }

    /**
     * Implementation of the ManageService gRPC service. Manages lobby join requests and responses using
     * a lobby facade.
     */
    static class ManageServiceImpl extends ManageServiceGrpc.ManageServiceImplBase {
        private LobbyFacade lobbyFacade;
        private JoinLobbyResponseMessage lobbyResponseMessage;
        EnemyFacade enemyFacade = EnemyFacade.getInstance();

        /**
         * Constructs a new ManageServiceImpl with the specified lobby facade.
         *
         * @param lobbyFacade The lobby facade used for managing lobby operations.
         */
        public ManageServiceImpl(LobbyFacade lobbyFacade) {
            this.lobbyFacade = lobbyFacade;
        }

        /**
         * Handles join lobby requests from clients. Processes the request to join a lobby and sends the
         * response back to the client with lobby and character information.
         *
         * @param request The request to join a lobby.
         * @param responseObserver The StreamObserver for sending responses back to the client.
         */
        @Override
        public void joinLobby(JoinLobbyRequest request, StreamObserver<JoinLobbyResponse> responseObserver) {
            System.out.println("Received joinLobby request: " + request.getLobbyID());

            try {
                switch (request.getClientTyp()){
                    case ("Skeleton") -> lobbyResponseMessage = lobbyFacade.joinLobby(request.getLobbyID(), LobbyCharType.Skeleton);
                    case ("Zombie") -> lobbyResponseMessage = lobbyFacade.joinLobby(request.getLobbyID(), LobbyCharType.Zombie);
                    case ("Devil") -> lobbyResponseMessage = lobbyFacade.joinLobby(request.getLobbyID(), LobbyCharType.Devil);
                    case ("Player") -> lobbyResponseMessage = lobbyFacade.joinLobby(request.getLobbyID(), LobbyCharType.PlayableCharacter);
                    default -> throw new IllegalArgumentException("request.getClientTyp()");
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }

            // Bei Erfolg, success = true, lobbyID = LobbyID, characterID = CharacterID
            JoinLobbyResponse response = JoinLobbyResponse.newBuilder()
                    .setSuccess(lobbyResponseMessage.isSuccess())
                    .setLobbyID(lobbyResponseMessage.getLobbyId())
                    .setCharacterID(lobbyResponseMessage.getCharacterId()) // für jeden client seine lokalePlayerId
                    .setUserID(lobbyResponseMessage.getClientId())  // sende mit für befehle damit server weiß wer was macht
                    .build();

            // Send the response back to the client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            System.out.println("Sent joinLobby response: LOBBYID:" + response.getLobbyID() + " SUCCESS:" + response.getSuccess() + " CHARID:" + response.getCharacterID() + " USERID:" + response.getUserID());
        }
    }
}
