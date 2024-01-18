package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Dummy Platzhalter für die "Lobby"
 */
public class StartingLobbyView extends ViewComponent {

    public StartingLobbyView(Terminal terminal) {
        super(terminal);
    }

    /**
     *
     */
    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Loading the Game...");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

