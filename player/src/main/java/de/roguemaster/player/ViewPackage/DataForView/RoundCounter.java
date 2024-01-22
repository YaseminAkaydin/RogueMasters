package de.roguemaster.player.ViewPackage.DataForView;

public class RoundCounter {
    private static RoundCounter instance = null;
    private int roundCounter;

    private RoundCounter() {
        roundCounter = 0;
    }

    public static RoundCounter getInstance() {
        if (instance == null) {
            instance = new RoundCounter();
        }
        return instance;
    }

    public void incrementRoundCounter() {
        roundCounter++;
    }

    public int getRoundCounter() {
        return roundCounter;
    }
}
