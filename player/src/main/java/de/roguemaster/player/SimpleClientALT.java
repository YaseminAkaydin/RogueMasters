package de.roguemaster.player;

import com.example.grpc.GameCommandRequest;
import com.example.grpc.GameCommandResponse;
import com.example.grpc.GameServiceGrpc;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.Game;
import io.grpc.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleClientALT {
    private static final Logger logger = Logger.getLogger(SimpleClientALT.class.getName());
    private Game game; // Reference to the Game instance

    private final GameServiceGrpc.GameServiceBlockingStub blockingStub;


    public SimpleClientALT(Channel channel, Game game) {
        this.blockingStub = GameServiceGrpc.newBlockingStub(channel);
        this.game = game;
    }

    public void startGame() {
        logger.info("Starting game...");

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        GameCommandRequest request = GameCommandRequest.newBuilder().setCommand("startLobby").setTarget("/").build();
        GameCommandResponse response;
        Terminal terminal = null;
        try {
            terminal = terminalFactory.createTerminal();
            Game game = new Game(terminal);
            game.run();
            terminal.close();
            try {
                /*response = blockingStub.sendGameCommand(request);*/
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return;
            }
            // logger.info("RESPONSE FROM SERVER: " + response.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Terminalfactory createTerminal error MAYBE: " + e);
        }
        //logger.info("Response: " + response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8811";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();
            Game game = new Game(terminal);

            SimpleClientALT client = new SimpleClientALT(channel, game);
            client.startGame();

            game.run(); // Start the game loop
            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            channel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
        }
    }


    /*public static void main(String[] args) throws InterruptedException {
        // DEFAULT USER AND TARGET
        String user = "world";
        // Access a service running on the local machine on port 50051
        String target = "localhost:8811";


        // Allow passing in the user and target strings as command line arguments
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.err.println("Usage: [name [target]]");
                System.err.println("");
                System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
                System.err.println("  target  The server to connect to. Defaults to " + target);
                System.exit(1);
            }
            user = args[0];
        }
        if (args.length > 1) {
            target = args[1];
        }

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        //
        // For the example we use plaintext insecure credentials to avoid needing TLS certificates. To
        // use TLS, use TlsChannelCredentials instead.
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();

        try {
            SimpleClient client = new SimpleClient(channel);
            client.startGame();
        } finally {
            channel.shutdownNow().awaitTermination(5L, TimeUnit.SECONDS);
        }

    }*/

    /*public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8811)
                .usePlaintext()
                .build();

        GameServiceGrpc.GameServiceStub stub = GameServiceGrpc.newStub(channel);

        StreamObserver<GameCommandRequest> requestObserver = stub.sendGameCommand(new StreamObserver<GameCommandResponse>() {
            @Override
            public void onNext(GameCommandResponse response) {
                // Handle the response from the server
                System.out.println("Received game state: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Handle error
            }

            @Override
            public void onCompleted() {
                // Server has completed sending messages
                System.out.println("Stream completed.");
            }
        });

        // Send a command to the server
        int i = 0;

        GameCommandRequest request = GameCommandRequest.newBuilder()
                .setCommand("move")
                .setTarget("player1")
                .build();
        requestObserver.onNext(request);

        // Mark the end of requests
        requestObserver.onCompleted();

        // Wait for the server to shut down
        channel.shutdown();
    }*/

}
