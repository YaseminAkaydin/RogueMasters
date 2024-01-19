package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Wenn das Spiel Gestartet wird, wird diese View angezeigt.
 * TODO: Verschönern
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
            // What do you want do to? 1. Start Lobby 2. Join Lobby 3. See LeaderBoard 4. Exit
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
