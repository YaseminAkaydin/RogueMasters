package de.rougemaster.dungeon.lobby;

import com.example.grpc.GameCommandResponse;
import com.google.gson.reflect.TypeToken;
import de.rougemaster.dungeon.DungeonApplication;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.lobby.messageData.GameStateMessage;
import de.rougemaster.dungeon.lobby.messageData.JoinLobbyResponseMessage;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.util.*;

//Singleton Pattern
public class LobbyFacade {
    //singleton instance
    private static LobbyFacade instance = null;
    private final LobbyBroker lobbyBroker;
    private final LobbyFactory lobbyFactory;

    private List<Integer> clientIds = new ArrayList<>();

    Server server;
    private DungeonApplication.GameServiceImpl gameService;

    private static Map<Integer, StreamObserver<GameCommandResponse>> clientsToConnection = new HashMap<>();
    //private static final Map<StreamObserver<MessageResponse>, int> ConnectionToClients

    //private constructor
    private LobbyFacade() {
        lobbyFactory = new LobbyFactory();
        lobbyBroker = LobbyBroker.getLobbyBroker();
    }

    //get singleton instance
    public static LobbyFacade getInstance() {
        if (instance == null) {
            instance = new LobbyFacade();
        }
        return instance;
    }

    public JoinLobbyResponseMessage joinLobby(int lobbyId, LobbyCharType lobbyCharType){
        int clientId = createClientId();
        int characterId;

        if(lobbyId == 0){
            lobbyId = lobbyFactory.createLobby().getLobbyId();
        }

        if(lobbyBroker.getLobbyBrokerRegister().checkLobbyIdExist(lobbyId)){
            lobbyBroker.registerUser(clientId, lobbyId);
            characterId = lobbyBroker.forwardToLobby(clientId).joinClientLobby(clientId, lobbyCharType);
            if(characterId > 0){
                return new JoinLobbyResponseMessage(characterId, clientId, lobbyId, true);
            }
        }

        lobbyBroker.unregisterUser(clientId);
        return new JoinLobbyResponseMessage(-1, -1, lobbyId, false);
    }

    /**
     * Sets the GameCommand in the Lobby
     * @param clientId the id of the Client
     * @param lobbyMessage the Message that should to be sent
     */
    public void setGameCommand(int clientId, LobbyMessage lobbyMessage){
        Lobby lobby = lobbyBroker.forwardToLobby(clientId);
        lobby.enterCommand(clientId, lobbyMessage);
    }

    /**
     * Sends the GameState to the Client
     * @param clientIds the id of the Client
     * @param gameState the GameState that should to be sent
     */
    public void sendNextTurn(Set<Integer> clientIds, GameState gameState){
        GameStateMessage gameStateMessage = new GameStateMessage(gameState);
        String message = new JSONManager<GameStateMessage>(new TypeToken<>() {
        }).write(gameStateMessage);

        for(Integer clientId: clientIds) {
            if(clientsToConnection.containsKey(clientId)){
                System.out.println("Sending to Client: " + clientId);
                clientsToConnection.get(clientId).onNext(GameCommandResponse.newBuilder().setMessage(message).build());
            }
        }
        //Stub.startGameStateUpdates();
        //TODO: Send NextTurn to Client via ServerStub
    }

    public boolean isClientIdExisting (int clientId) {
        return lobbyBroker.isUserRegisterd(clientId);
    }

    private int createClientId(){
        int clientId;
        do{
            clientId = 9999 + new Random( System.currentTimeMillis() ).nextInt(90001);
        }while (clientIds.contains(clientId));
        clientIds.add(clientId);
        return clientId;
    }

    public GameStateMessage getGameStateMessage(int clientId){
        return new GameStateMessage(lobbyBroker.forwardToLobby(clientId).getGameState());
    }

    public void setServer (Server server) {
        this.server = server;
    }

    public void setGameService(DungeonApplication.GameServiceImpl gameService) {
        this.gameService = gameService;

    }

    public void setClientsToConnection(Map<Integer, StreamObserver<GameCommandResponse>> clientsToConnection) {
        LobbyFacade.clientsToConnection = clientsToConnection;
    }
}
