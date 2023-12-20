package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Dummy Platzhalter für die "Lobby"
 * TODO: Startgame vom Server abrufen
 * TODO: Antwort vom Server verarbeiten und als Daten speichern
 * TODO: Zuständigkeit klären, funktionalität hier in die "view" oder in die main oder in den "controller"?
 */
public class StartingLobbyView implements ViewComponent {
    private Terminal terminal;

    public StartingLobbyView(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     *
     */
    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Successfully started Lobby");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

