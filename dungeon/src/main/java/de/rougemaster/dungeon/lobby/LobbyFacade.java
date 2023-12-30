package de.rougemaster.dungeon.lobby;

//Singleton Pattern
public class LobbyFacade {
    //singleton instance
    private static LobbyFacade instance = null;
    //private constructor
    private LobbyFacade() {
        //TODO: Create openConnection method for GRPC connection
    }

    //get singleton instance
    public static LobbyFacade getInstance() {
        if (instance == null) {
            instance = new LobbyFacade();
        }
        return instance;
    }
}
