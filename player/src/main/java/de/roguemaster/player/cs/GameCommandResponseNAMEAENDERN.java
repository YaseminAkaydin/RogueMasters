package de.roguemaster.player.cs;

public class GameCommandResponseNAMEAENDERN {
    private final boolean success;
    private final String message;

    // Constructor
    public GameCommandResponseNAMEAENDERN(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    // Getters
    public boolean getSuccess() { return success; }
    public String getMessage() { return message; }

    // Factory methods for different game actions

}
