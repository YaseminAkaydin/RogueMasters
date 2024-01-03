package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Dummy Platzhalter fürs Lobby joinen
 * Die View ansich ist theoretisch feritg, es muss nur noch die Antwort vom Server verarbeitet werden wo?
 * TODO: Klären von funktionalität, wenn jemand jetzt lobby joinen will, wo wird das verarbeitet?
 * TODO: verschänern
 */
public class JoiningLobbyView extends ViewComponent {

    public JoiningLobbyView(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Waiting for Lobby to start");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

