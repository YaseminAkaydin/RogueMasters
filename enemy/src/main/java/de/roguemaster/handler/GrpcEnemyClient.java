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
    private int clientID;

    private EnemyHandler enemyHandler;

    /**
     * Constructs a GrpcEnemyClient for managing enemy interactions in a game lobby. Initializes gRPC
     * stubs for communication and sets up an enemy handler.
     *
     * @param lobbyID The ID of the game lobby.
     * @param blockingChannel The gRPC blocking channel.
     * @param asyncChannel The gRPC asynchronous channel.
     * @param enemyHandler The handler for processing enemy actions.
     */
    public GrpcEnemyClient(int lobbyID, Channel blockingChannel, Channel asyncChannel, EnemyHandler enemyHandler) {
        this.lobbyID = lobbyID;
        this.blockingStub = ManageServiceGrpc.newBlockingStub(blockingChannel);
        this.asyncStub = GameServiceGrpc.newStub(asyncChannel);
        this.enemyHandler = enemyHandler;
    }

    /**
     * Sends a join lobby request to the server. Converts the lobby ID and client type into a JoinLobbyRequest
     * and sends it using the blocking stub.
     *
     * @param lobbyID The ID of the lobby to join.
     * @param clientTyp The type of client joining the lobby.
     * @return The JoinLobbyResponse received from the server.
     */
    public JoinLobbyResponse sendJoinLobbyRequest(int lobbyID, String clientTyp) {
        JoinLobbyRequest request = convertToJoinLobbyRequest(lobbyID, clientTyp);
        return blockingStub.joinLobby(request);
    }

    /**
     * Converts lobby ID and client type information into a JoinLobbyRequest object.
     *
     * @param lobbyID The ID of the lobby to join.
     * @param clientTyp The type of client joining the lobby.
     * @return A JoinLobbyRequest object.
     */
    private JoinLobbyRequest convertToJoinLobbyRequest(int lobbyID, String clientTyp) {
        return JoinLobbyRequest.newBuilder().
                setLobbyID(lobbyID).
                setClientTyp(clientTyp).
                build();
    }

    /**
     * Converts command and target information into a GameCommandRequest object. Includes the client ID in the request.
     *
     * @param command The game command to be sent.
     * @param target The target of the command.
     * @return A GameCommandRequest object.
     */
    private GameCommandRequest convertToGameCommandRequest(String command, String target) {
        return GameCommandRequest.newBuilder().
                setCommand(command).
                setTarget(target).
                setUserId(clientID).
                build();
    }

    /**
     * Initializes the stream for sending game commands and receiving responses. Sets up a StreamObserver
     * to handle incoming game command responses and connection errors.
     */
    public void initStream() {
        if (requestObserver != null) {
            logger.info("Stream already initialized");
        }
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

    /**
     * Sends a game command to the server. Converts the CommandHolder object into a GameCommandRequest
     * and sends it using the request observer stream.
     *
     * @param commandHolder The holder object containing the command and target information.
     */
    public void sendCommand(CommandHolder commandHolder) {
        requestObserver.onNext(convertToGameCommandRequest(commandHolder.getCommand(), commandHolder.getTarget()));
    }

    public void shutdown() {
        requestObserver.onCompleted();
    }

    // Getter and Setter
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
