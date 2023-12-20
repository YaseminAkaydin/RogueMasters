package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class DungeonMapView implements ViewComponent{
    private Terminal terminal;

    public DungeonMapView(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();

        terminal.setCursorPosition(0, 0);
        terminal.putString("This is the Map");
        terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
