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

    protected static final String asciiArt = "\n" +            " (                              *                                   \n" +
            " )\\ )                         (  `                 )                \n" +
            "(()/(      (  (     (     (   )\\))(      )      ( /(   (   (        \n" +
            " /(_)) (   )\\))(   ))\\   ))\\ ((_)()\\  ( /(  (   )\\()) ))\\  )(   (   \n" +
            "(_))   )\\ ((_))\\  /((_) /((_)(_()((_) )(_)) )\\ (_))/ /((_)(()\\  )\\  \n" +
            "| _ \\ ((_) (()(_)(_))( (_))  |  \\/  |((_)_ ((_)| |_ (_))   ((_)((_) \n" +
            "|   // _ \\/ _` | | || |/ -_) | |\\/| |/ _` |(_-<|  _|/ -_) | '_|(_-< \n" +
            "|_|_\\\\___/\\__, |  \\_,_|\\___| |_|  |_|\\__,_|/__/ \\__|\\___| |_|  /__/ \n" +
            "          |___/                                                     \n";


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

    protected void drawTitle(TextColor textColor) throws IOException {
        tg.setForegroundColor(textColor);
        String[] lines = asciiArt.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tg.putString(2, 2 + i, lines[i]);
        }
    }

    public RoomData getRoomData() {
        return gameState.getRoomData();
    }
}
