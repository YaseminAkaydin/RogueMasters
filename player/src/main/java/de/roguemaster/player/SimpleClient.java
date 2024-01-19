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
    private int userID; // Id der Clients zum befehle verarbeiten

    private final GameServiceGrpc.GameServiceStub asyncStub; // Async stub, for commands and 5sekGamestate from server
    private final ManageServiceGrpc.ManageServiceBlockingStub blockingStub; // For joining lobbies/creating lobbies

    StreamObserver<GameCommandRequest> requestObserver; //

    public SimpleClient(Channel asyncChannel, Game game, Channel blockingChannel) {
        this.blockingStub = ManageServiceGrpc.newBlockingStub(blockingChannel);
        this.game = game;
        this.asyncStub = GameServiceGrpc.newStub(asyncChannel);
    }

    public void startGame() {
        // Start the g ame in a new thread
        startGameAsThread();

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
            JoinLobbyResponse response = blockingStub.joinLobby(joinLobbyRequest);
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

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8811";
        ManagedChannel asyncChannel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        ManagedChannel blockingChannel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            terminalFactory.setTerminalEmulatorTitle("RogueMaster");
            Terminal terminal = terminalFactory.createTerminal();
            // Chane title of terminal window
            Game game = new Game(terminal);

            SimpleClient client = new SimpleClient(asyncChannel, game, blockingChannel);
            client.startGame();

            // You can add some logic here to wait for the game to finish
            // For example, a simple input to stop the game
            System.out.println("Press Enter to stop the game...");
            terminal.flush();
            terminal.putString("Press Enter to stop the game...");
            System.in.read();

            client.stopGame(); // Stop the game and the game thread
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down Client...");
            asyncChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
            blockingChannel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
        }
    }

}

