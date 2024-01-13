package de.rougemaster.dungeon.game;

public class TurnManagerThread implements Runnable{
    TurnManager turnManager;


    public TurnManagerThread(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public void run() {
        while (true) {

            long startTime = System.currentTimeMillis(); // Aufnahme der Startzeit
            turnManager.executeTurn();
            long endTime = System.currentTimeMillis(); // Aufnahme der Endzeit

            long executionTime = endTime - startTime; // Berechnung der Ausführungszeit
            System.out.println("\n executeTurn() hat " + executionTime + " Millisekunden gedauert. \n");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }



}

