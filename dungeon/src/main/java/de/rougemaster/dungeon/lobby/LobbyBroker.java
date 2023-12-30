package de.rougemaster.dungeon.lobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Forwards a Player to their Lobby.
 * LobbyBroker is designed after the SingletonPattern
 */
public class LobbyBroker {
    Map<Integer,Lobby> userMap;
    private static LobbyBroker lobbyBroker;
    private final LobbyBrokerRegister lobbyBrokerRegister;


    private LobbyBroker (){
        this.lobbyBrokerRegister = new LobbyBrokerRegister();
        lobbyBroker = this;
    }

    public LobbyBrokerRegister getLobbyBrokerRegister() {
        return lobbyBrokerRegister;
    }

    public static LobbyBroker getLobbyBroker() {
        if(lobbyBroker == null) {
            new LobbyBroker();
        }
        return lobbyBroker;
    }

    /**
     * Forwards the LobbyCommand to the appropriate Lobby
     * @param clientId the id of the Client
     * @param lobbyCommand the Command that should be sen
     */
    public void forwardToLobby(int clientId , LobbyCommand lobbyCommand) {
        userMap.get(clientId);
        //TODO: use enterCommand to enter the Command in Lobby
    }

    /**
     * Registers a User to their Lobby.
     * The Lobby has to be Registered in LobbyBrokerRegister
     * @param clientId the Identifikator of the Client
     * @param LobbyId the Identifikator of the Lobby
     */
    public void registerUser(int clientId, int LobbyId) {

    }

    public static class LobbyBrokerRegister {
        private final Map<Integer, Lobby> lobbyMap;

        private final static int lowestAllowedLobbyId = 9999;
        private final static int highestAllowedLobbyId = 100000;

        private LobbyBrokerRegister () {
            lobbyMap = new HashMap<>();
        }

        /**
         * Registers a Lobby with a fitting LobbyId in LobbyBroker.
         * LobbyId has to be a five-digit number
         * @param lobbyId a lobby Identifier that is at least five digit long
         * @param lobby the lobby that has to be registered
         * @throws IllegalArgumentException if Lobby is null
         */
        public void registerLobby (int lobbyId ,Lobby lobby) throws IllegalArgumentException{
            if(lobby == null){
                throw new IllegalArgumentException("Lobby can't be null");
            }

            if(checkIfLobbyIdIsLegal(lobbyId)){
                return;
            }

            if(checkLobbyIdExist(lobbyId)){
                return;
            }

            lobbyMap.put(lobbyId, lobby);
        }

        /**
         * Removes a Lobby with its LobbyId from the register.
         * @param lobbyId the LobbyId that is to be removed from the register.
         */
        public void removeLobby(int lobbyId){
            if(checkLobbyIdExist(lobbyId)){
                lobbyMap.remove(lobbyId);
            }
        }

        /**
         * Generates a random valid LobbyId. It doesn't guarantee that the number isn't taken in the meantime.
         * @return a random valid LobbyId.
         */
        public int generateValidLobbyId(){
            int randomLobbyId;
            do{
                randomLobbyId = 9999 + new Random( System.currentTimeMillis() ).nextInt(90001);
            }while (checkLobbyIdExist(randomLobbyId));
            return randomLobbyId;
        }

        /**
         * Checks if a given LobbyId is already registered in LobbyBroker
         * @param lobbyId a given lobbyId
         * @return true if lobbyId is registered else false
         */
        public boolean checkLobbyIdExist(int lobbyId){
            return lobbyMap.containsKey(lobbyId);
        }

        private boolean checkIfLobbyIdIsLegal(int lobbyId){
            return lobbyId > lowestAllowedLobbyId && lobbyId < highestAllowedLobbyId;
        }
    }
}
