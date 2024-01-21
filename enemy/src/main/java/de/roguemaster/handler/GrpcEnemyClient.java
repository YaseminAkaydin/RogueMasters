package de.roguemaster.handler;

import com.example.grpc.*;
import de.roguemaster.enemy.CommandHolder;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

/**
 * Join Lobby zu LobbyCode
 * Send GameCommands to server
 * Receive GameStates from server
 */
public class GrpcEnemyClient {
    private static final Logger logger = Logger.getLogger(GrpcEnemyClient.class.getName());

    private GameServiceGrpc.GameServiceStub asyncStub; // Async stub, for commands and 5sekGamestate from server
    private ManageServiceGrpc.ManageServiceBlockingStub blockingStub; // For joining lobbies/creating lobbies
    private StreamObserver<GameCommandRequest> requestObserver;

    private final int lobbyID;
    private int mobID;

    private EnemyHandler enemyHandler;

    public GrpcEnemyClient(int lobbyID, Channel blockingChannel, Channel asyncChannel, EnemyHandler enemyHandler) {
        this.lobbyID = lobbyID;
        this.blockingStub = ManageServiceGrpc.newBlockingStub(blockingChannel);
        this.asyncStub = GameServiceGrpc.newStub(asyncChannel);
        this.enemyHandler = enemyHandler;
    }


    public JoinLobbyResponse sendJoinLobbyRequest(int lobbyID, String clientTyp) {
        JoinLobbyRequest request = convertToJoinLobbyRequest(lobbyID, clientTyp);
        return blockingStub.joinLobby(request);
    }

    // Method to convert a Command to a JoinLobbyRequest
    private JoinLobbyRequest convertToJoinLobbyRequest(int lobbyID, String clientTyp) {
        return JoinLobbyRequest.newBuilder().
                setLobbyID(lobbyID).
                setClientTyp(clientTyp).
                build();
    }

    // Method to convert a Command to a GameCommandRequest
    private GameCommandRequest convertToGameCommandRequest(String command, String target) {
        return GameCommandRequest.newBuilder().
                setCommand(command).
                setTarget(target).
                setUserId(mobID).
                build();
    }

    public void initStream() {
        if (requestObserver != null) {
            logger.info("Stream already initialized");
        }
        System.out.println("Creating MOB stream to server");
        requestObserver = asyncStub.sendGameCommand(new StreamObserver<GameCommandResponse>() {
            @Override
            public void onNext(GameCommandResponse response) { // Das ruft der Server aus!
                // Handle incoming game state
                // UpdateGamestete hier
                enemyHandler.nextCommand(response);
            }

            @Override
            public void onError(Throwable t) {
                // Connection to server lostc
                logger.warning("RPC MOB failed, server crashed?: " + t.getMessage());
                //TODO: somehow reconnect to server??
            }

            @Override
            public void onCompleted() {
                // Server has completed sending messages to this client
                logger.info("Server has completed sending messages");
            }
        });
    }

    public void sendCommand(CommandHolder commandHolder) {
        requestObserver.onNext(convertToGameCommandRequest(commandHolder.getCommand(), commandHolder.getTarget()));
    }


    public void shutdown() {
        requestObserver.onCompleted();
    }
}
