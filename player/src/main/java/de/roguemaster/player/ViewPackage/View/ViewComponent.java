package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class ViewComponent {
    // ALl
    protected final Terminal terminal;
    protected TextGraphics tg;

    protected final Random random = new Random();


    protected GameState gameState;

    public abstract void display();


    protected ViewComponent(Terminal terminal, GameState gameState) {
        this.terminal = terminal;
        this.gameState = gameState;
    }

    protected ViewComponent(Terminal terminal) {
        this.terminal = terminal;
    }

    protected void displayPlayerStats(TextGraphics tg) throws IOException {
        int statsStartY = terminal.getTerminalSize().getRows() - 1; // Below the room
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, statsStartY,
                "LEVEL: " + gameState.getLocalPlayer().getLevel() +
                " - " + gameState.getLocalPlayer().getHp() +
                "/" + gameState.getLocalPlayer().getMaxHp() +
                " - EXP: " + gameState.getLocalPlayer().getExperience() +
                "  -- 'M' = Map -- 'I' = Inventory -- 'S' = RoomView");
    }

    protected void clearAndInitializeGraphics() throws IOException {
        terminal.clearScreen();
        tg = terminal.newTextGraphics();
    }

    public RoomData getRoomData() {
        return gameState.getRoomData();
    }
}
