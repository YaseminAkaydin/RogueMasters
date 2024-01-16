package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.game.GameState;
import io.grpc.stub.StreamObserver;

//Singleton Pattern
public class LobbyFacade {
    //singleton instance
    private static LobbyFacade instance = null;
    private final LobbyBroker lobbyBroker;
    private final LobbyFactory lobbyFactory;

    //private static final Map<int, StreamObserver<MessageResponse>> clientsToConnection
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

    public int joinLobby(int clientId, int lobbyId){
        if(lobbyId == 0){
            return lobbyFactory.createLobby().getLobbyId();
        }
        else if(lobbyBroker.getLobbyBrokerRegister().checkLobbyIdExist(lobbyId)){
            lobbyBroker.registerUser(clientId, lobbyId);
            return lobbyId;
        }
        else{
            return -1;
        }
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
     * @param clientId the id of the Client
     * @param gameState the GameState that should to be sent
     */
    public void sendNextTurn(int clientId, GameState gameState){
        //TODO: Send NextTurn to Client via ServerStub
    }
}
