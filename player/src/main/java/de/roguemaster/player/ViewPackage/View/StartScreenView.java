package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * The StartScreenView class is responsible for displaying the Start Screen view on the terminal.
 *
 */
public class StartScreenView extends ViewComponent {

    public StartScreenView(Terminal terminal) {
        super(terminal);
    }
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            terminal.clearScreen();

            drawTitle(TextColor.ANSI.RED);
            String[] lines = asciiArt.split("\n");
            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

            tg.putString(2, lines.length + 4, "What do you want to do?");
            tg.putString(2, lines.length + 5, "1. Start Lobby");
            tg.putString(2, lines.length + 6, "2. Join Lobby");
            tg.putString(2, lines.length + 7, "3. See LeaderBoard");
            tg.putString(2, lines.length + 8, "4. Exit");

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
