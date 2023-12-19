package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

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

