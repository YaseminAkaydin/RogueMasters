package de.roguemaster.player.ViewPackage;

public class Command {
    private String command;
    private String target;

    // Constructor
    public Command(String command, String target) {
        this.command = command;
        this.target = target;
    }
    public Command(String command) {
        this.command = command;
        this.target = "/";
    }
    //new builder
    public static Command newBuilder() {
        return new Command("","");
    }

    // Getters
    public String getCommand() { return command; }
    public String getTarget() { return target; }

    public void setTarget(String target) { this.target = target;}
    public void setCommand(String command) {this.command = command; }

    // Factory methods for different game actions
    public static Command attackCommand(int attackedId) {
        return new Command("attackUsingEquipment", "a" + attackedId);
    }
    public static Command fleeCommand(int roomId) {
        return new Command("flee", "r" + roomId);
    }
    public static Command moveCommand(int roomId) {
        return new Command("move", "r" + roomId);
    }
    public static Command pickupCommand(int itemId) {
        return new Command("takeItem", "i" + itemId);
    }
    public static Command doNothingCommand() {
        return new Command("doNothing");
    }
    public static Command useItemCommand(int itemId) {
        return new Command("useItem", "i" + itemId);
    }
    public static Command startLobby( ) {
        return new Command("0");
    }
    public static Command initialize( ) {
        return new Command("initialize");
    }
    public static Command joinLobby(String lobbyCode) {
        return new Command(lobbyCode);
    }
    public static Command dropItemCommand(int itemId) {
        return new Command("dropItem", "i" + itemId);
    }
}

