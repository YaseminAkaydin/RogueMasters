package de.roguemaster.player.cs;

public class GameCommandRequestNAMEAENDERN {
    private String command;
    private String target;

    // Constructor
    public GameCommandRequestNAMEAENDERN(String command, String target) {
        this.command = command;
        this.target = target;
    }
    public GameCommandRequestNAMEAENDERN(String command) {
        this.command = command;
        this.target = "/";
    }
    //new builder
    public static GameCommandRequestNAMEAENDERN newBuilder() {
        return new GameCommandRequestNAMEAENDERN("","");
    }

    // Getters
    public String getCommand() { return command; }
    public String getTarget() { return target; }

    public void setTarget(String target) { this.target = target;}
    public void setCommand(String command) {this.command = command; }

    // Factory methods for different game actions
    public static GameCommandRequestNAMEAENDERN attackCommand() {
        return new GameCommandRequestNAMEAENDERN("attack");
    }
    public static GameCommandRequestNAMEAENDERN moveCommand(String roomId) {
        return new GameCommandRequestNAMEAENDERN("move", "r" + roomId);
    }
    public static GameCommandRequestNAMEAENDERN pickupCommand(String itemId) {
        return new GameCommandRequestNAMEAENDERN("pickup", "i" + itemId);
    }
    public static GameCommandRequestNAMEAENDERN doNothingCommand() {
        return new GameCommandRequestNAMEAENDERN("doNothing");
    }
    public static GameCommandRequestNAMEAENDERN useItemCommand(String itemId) {
        return new GameCommandRequestNAMEAENDERN("useItem", "i" + itemId);
    }
    public static GameCommandRequestNAMEAENDERN startLobby( ) {
        return new GameCommandRequestNAMEAENDERN("startLobby");
    }
    public static GameCommandRequestNAMEAENDERN ujoinLobby( ) {
        return new GameCommandRequestNAMEAENDERN("ujoinLobby");
    }
}

