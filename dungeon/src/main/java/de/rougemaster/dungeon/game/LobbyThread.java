package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.lobby.Lobby;
import de.rougemaster.dungeon.lobby.LobbyFacade;

import static java.lang.System.currentTimeMillis;

public class LobbyThread implements Runnable{
    Lobby lobby;

    public LobbyThread(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void run() {
        while (true) {

            long startTime = currentTimeMillis(); // Aufnahme der Startzeit
            lobby.getGame().getTurnManager().executeTurn();
            long endTime = currentTimeMillis(); // Aufnahme der Endzeit

            long executionTime = endTime - startTime; // Berechnung der Ausführungszeit

            System.out.println("ExecuteTurn() hat " + executionTime + " Millisekunden gedauert.");
            lobby.nextTurn();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}

