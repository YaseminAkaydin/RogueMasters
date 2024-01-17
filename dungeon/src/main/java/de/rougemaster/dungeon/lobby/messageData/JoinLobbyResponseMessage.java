package de.rougemaster.dungeon.lobby.messageData;

public class JoinLobbyResponseMessage {
    private int characterId;
    private int clientId;
    private int lobbyId;
    private boolean success;

    public JoinLobbyResponseMessage(int characterId, int userId, int lobbyId, boolean success) {
        this.characterId = characterId;
        this.clientId = userId;
        this.lobbyId = lobbyId;
        this.success = success;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public boolean isSuccess() {
        return success;
    }
}
