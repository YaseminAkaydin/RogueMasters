package de.roguemaster.player;

import com.example.grpc.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.roguemaster.player.ViewPackage.Command;
import de.roguemaster.player.ViewPackage.Game;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SimpleClient {
    private static final Logger logger = Logger.getLogger(SimpleClient.class.getName());

    private final Game game;
    private Thread gameThread;
    private int userID; // Id der Clients zum befehle verarbeiten

    private final GameServiceGrpc.GameServiceStub asyncStub; // Async stub, for commands and 5sekGamestate from server
    private final ManageServiceGrpc.ManageServiceBlockingStub blockingStub; // For joining lobbies/creating lobbies

    StreamObserver<GameCommandRequest> requestObserver;

    public SimpleClient(Channel asyncChannel, Game game, Channel blockingChannel) {
        this.blockingStub = ManageServiceGrpc.newBlockingStub(blockingChannel);
        this.game = game;
        this.asyncStub = GameServiceGrpc.newStub(asyncChannel);
    }

    public void startGame() {
        // Start the g ame in a new thread
        startGameAsThread();
        System.out.println("Game started!\n");

        // Start the ClientLoop
        while (game.getRunning()) {
            Command command = game.getNextCommand();
            //checkGameStarted();
            if (command != null) {
                checkAndSendCommands(command);
            }
            if (requestObserver != null && !game.getRunning()) {
                System.out.println("Client shutting down, sending complete");
                stopGame();
                requestObserver.onCompleted(); // Complete the request stream
            }
        }
    }

    // Erstellen den Stream erst NUR wenn wir auch in einem game starten
    private void checkGameStarted() {
        if (requestObserver == null && game.getRunning()) {
            System.out.println("Creating stream to server");
            requestObserver = asyncStub.sendGameCommand(new StreamObserver<GameCommandResponse>() {
                @Override
                public void onNext(GameCommandResponse response) {
                    // Handle incoming game state
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
                    //TODO: somehow reconnect to server
                    requestObserver.onNext(convertToGameCommandRequest(new Command("initialize")));
                }

                @Override
                public void onCompleted() {
                    // Server has completed sending messages
                    logger.info("Server has completed sending messages");
                }
            });
        }
    }

    // Method to check if the command is a joinlobby or a gamecommand and send it to the server
    private void checkAndSendCommands(Command command) {

        if (Objects.equals(command.getCommand(), "0") || command.getCommand().length() == 5) {
            JoinLobbyRequest joinLobbyRequest = convertToJoinLobbyRequest(command);
            System.out.println("JoinLobbyRequest sent: " + command.getCommand() + " " + command.getTarget());
            System.out.println("Sent to serverIP and Port " + blockingStub.getChannel().authority() + "\n");
            JoinLobbyResponse response = null;
            try {
                response = blockingStub.joinLobby(joinLobbyRequest);
            } catch (StatusRuntimeException e) {
                System.out.println("RPC failed: " + e.getStatus());
                e.printStackTrace();
            }

            if (response.getSuccess()) {
                game.setSuccessJoinLobby(true);
                System.out.println("Lobby joined: " + response.getLobbyID() + " " + response.getSuccess() + " " + response.getCharacterID());
                game.setLocalPlayerID(response.getCharacterID());
                this.userID = response.getUserID();
                checkGameStarted();
                requestObserver.onNext(convertToGameCommandRequest(new Command("initialize"))); //init direkt nach dem erstellen des streams mitsenden für Init
            }
        } else {
            // Create a gamecommand
            GameCommandRequest request = convertToGameCommandRequest(command);
            System.out.println("GameCommandRequest to server: " + command.getCommand() + " " + command.getTarget());
            requestObserver.onNext(request);
        }
    }
    // Method to convert a Command to a JoinLobbyRequest
    private JoinLobbyRequest convertToJoinLobbyRequest(Command command) {
        return JoinLobbyRequest.newBuilder().
                setLobbyID(Integer.parseInt(command.getCommand())).
                setClientTyp("Player").
                build();
    }

    // Method to convert a Command to a GameCommandRequest
    private GameCommandRequest convertToGameCommandRequest(Command command) {
        return GameCommandRequest.newBuilder().
                setCommand(command.getCommand()).
                setTarget(command.getTarget()).
                setUserId(userID).
                build();
    }

    // Method to start the game in a new thread
    public void startGameAsThread() {
        gameThread = new Thread(() -> {
            try {
                game.run();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Game crashed: \n" + e.getMessage());
            }
        });
        gameThread.start();
    }
    // Method to stop the game
    public void stopGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }
    }

    // Main Accepts one target, with following syntax: [hostname]:[port]
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 2 && "DEBUG".equals(args[2])) {
            // Set logger to DEBUG level
            System.out.println("Starting the application\n");
        }

        ManagedChannel asyncChannel = ManagedChannelBuilder.forAddress(args[0], Integer.parseInt(args[1]))
                .usePlaintext()
                .build();
        ManagedChannel blockingChannel = ManagedChannelBuilder.forAddress(args[0], Integer.parseInt(args[1]))
                .usePlaintext()
                .build();

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setTerminalEmulatorTitle("RogueMaster");
            Terminal terminal = terminalFactory.createTerminal();
            // Chane title of terminal window
            Game game = new Game(terminal);
            SimpleClient client = new SimpleClient(asyncChannel, game, blockingChannel);

            client.startGame();
            client.stopGame(); // Stop the game and the game thread
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Game crashed try in main: \n" + e.getMessage());
        } finally {
            /*System.out.println("Shutting down Client...\n");*/
            /*asyncChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
            blockingChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);*/
        }
    }

}

