package de.rougemaster.dungeon.game;

public class TurnManagerThread implements Runnable{
    TurnManager turnManager;

    public TurnManagerThread(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public void run() {
        while (true) {
            turnManager.executeTurn();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}

