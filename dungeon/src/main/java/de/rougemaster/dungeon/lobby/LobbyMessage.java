package de.rougemaster.dungeon.lobby;

public class LobbyMessage {
    int clientId;
    String command;
    String target;

    public LobbyMessage(String command, String target) {
        this.command = command;
        this.target = target;
    }
}
