package de.roguemaster.player;

import com.example.grpc.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.Command;
import de.roguemaster.player.ViewPackage.Game;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SimpleClient {
    private static final Logger logger = Logger.getLogger(SimpleClient.class.getName());

    private final Game game;
    private Thread gameThread;

    private final GameServiceGrpc.GameServiceStub asyncStub; // Async stub, for commands and 5sekGamestate from server
    private final ManageServiceGrpc.ManageServiceBlockingStub blockingStub; // For joining lobbies/creating lobbies

    StreamObserver<GameCommandRequest> requestObserver; //

    public SimpleClient(Channel asyncChannel, Game game, Channel blockingChannel) {
        this.blockingStub = ManageServiceGrpc.newBlockingStub(blockingChannel);
        this.game = game;
        this.asyncStub = GameServiceGrpc.newStub(asyncChannel);
    }

    public void startGame() {
        // Start the game in a new thread
        gameThread = new Thread(() -> {
            try {
                game.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();



        // Loop for checking commands and sending them to the server
        while (!gameThread.isInterrupted()) {
            Command command = game.getNextCommand();
            // only initiliate requestObserver if game.gameStarted == true and requestObserver == null
            if (requestObserver == null && game.isGameStarted()) {
                logger.info("Starting gameState updater...");
                requestObserver = asyncStub.sendGameCommand(new StreamObserver<GameCommandResponse>() {
                    @Override
                    public void onNext(GameCommandResponse response) {
                        // Handle incoming game state
                        System.out.println("GS: " + response.getMessage());
                        new Thread(() -> {
                            try {
                                game.updateGameState(response.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.warning("RPC failed: " + t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        // Server has completed sending messages
                    }
                });
            }

            if (command != null) {
                System.out.println("Sent to server: " + command.getCommand() + " " + command.getTarget());

                // IF we want to start a lobby or join a lobby
                if (Objects.equals(command.getCommand(), "0") || command.getCommand().length() == 5) {
                    JoinLobbyRequest joinLobbyRequest = JoinLobbyRequest
                            .newBuilder()
                            .setLobbyID(Integer.parseInt(command.getCommand()))
                            .build();
                    JoinLobbyResponse response = blockingStub.joinLobby(joinLobbyRequest);

                    System.out.println("Lobby joined: " + response.getLobbyID() + " " + response.getSuccess() + " " + response.getCharacterID());
                } else {
                    // Create a gamecommand
                    GameCommandRequest request = convertToGameCommandRequest(command);
                    requestObserver.onNext(request);
                }
            }
            try {
                Thread.sleep(100); // Sleep for a short duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop if interrupted
            } /*finally {
                requestObserver.onCompleted(); // Complete the request stream
            }*/
        }
    }


    private GameCommandRequest convertToGameCommandRequest(Command command) {
        return GameCommandRequest.newBuilder().
                setCommand(command.getCommand()).
                setTarget(command.getTarget()).
                build();
    }

    // Method to stop the game
    public void stopGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8811";
        ManagedChannel asyncChannel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        ManagedChannel blockingChannel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();
            Game game = new Game(terminal);

            SimpleClient client = new SimpleClient(asyncChannel, game, blockingChannel);
            client.startGame();

            // You can add some logic here to wait for the game to finish
            // For example, a simple input to stop the game
            System.out.println("Press Enter to stop the game...");
            System.in.read();

            client.stopGame(); // Stop the game and the game thread
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            asyncChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
            blockingChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
        }
    }

}

