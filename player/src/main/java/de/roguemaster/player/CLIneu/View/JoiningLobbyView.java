package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class JoiningLobbyView implements ViewComponent {
    private Terminal terminal;

    public JoiningLobbyView(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Successfully joined Lobby");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

