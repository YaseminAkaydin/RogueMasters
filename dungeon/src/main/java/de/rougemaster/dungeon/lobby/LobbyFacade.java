package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.lobby.messageData.GameStateMessage;
import de.rougemaster.dungeon.lobby.messageData.JoinLobbyResponseMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Singleton Pattern
public class LobbyFacade {
    //singleton instance
    private static LobbyFacade instance = null;
    private final LobbyBroker lobbyBroker;
    private final LobbyFactory lobbyFactory;

    private List<Integer> clientIds = new ArrayList<>();

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

    public JoinLobbyResponseMessage joinLobby(int lobbyId){
        int clientId = createClientId();
        int characterId;

        if(lobbyId == 0){
            lobbyId = lobbyFactory.createLobby().getLobbyId();
            lobbyBroker.registerUser(clientId, lobbyId);
            characterId = lobbyBroker.forwardToLobby(clientId).createCharacter(clientId);
            return new JoinLobbyResponseMessage(characterId, clientId, lobbyId, true);
        }
        else if(lobbyBroker.getLobbyBrokerRegister().checkLobbyIdExist(lobbyId)){
            lobbyBroker.registerUser(clientId, lobbyId);
            characterId = lobbyBroker.forwardToLobby(clientId).createCharacter(clientId);
            return new JoinLobbyResponseMessage(characterId, clientId, lobbyId, true);
        }
        else{
            lobbyBroker.unregisterUser(clientId);
            return new JoinLobbyResponseMessage(-1, -1, lobbyId, false);
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
        //Stub.startGameStateUpdates();
        //TODO: Send NextTurn to Client via ServerStub
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
}
