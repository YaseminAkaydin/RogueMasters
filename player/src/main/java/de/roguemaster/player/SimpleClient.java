package de.roguemaster.player;

import com.example.grpc.GameCommandRequest;
import com.example.grpc.GameCommandResponse;
import com.example.grpc.GameServiceGrpc;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SimpleClient {
    private static final Logger logger = Logger.getLogger(SimpleClientALT.class.getName());

    private Game game; // Reference to the Game instance
    private Thread gameThread; // Thread for running the game

    private final GameServiceGrpc.GameServiceStub asyncStub;

    public SimpleClient(Channel channel, Game game) {
        this.game = game;
        this.asyncStub = GameServiceGrpc.newStub(channel);
    }

    public void startGame() {
        StreamObserver<GameCommandRequest> requestObserver = asyncStub.sendGameCommand(new StreamObserver<GameCommandResponse>() {
            @Override
            public void onNext(GameCommandResponse response) {
                // Handle incoming game state
                System.out.println("Game state received: " + response.getMessage());
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
            if (command != null) {
                System.out.println("Command: " + command.getCommand() + " " + command.getTarget());
                // Convert Command to GameCommandRequest and send it
                GameCommandRequest request = convertToGameCommandRequest(command);
                requestObserver.onNext(request);
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
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();
            Game game = new Game(terminal);

            SimpleClient client = new SimpleClient(channel, game);
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
            channel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
        }
    }

}

