package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * The LeaderBoardView class is responsible for displaying the Leader Board view on the terminal.
 */
public class LeaderBoardView extends ViewComponent {

    /**
     * Constructs a new LeaderBoardView with the specified Terminal.
     *
     * @param terminal The Terminal object used for displaying this view.
     */
    public LeaderBoardView(Terminal terminal) {
        super(terminal);
    }

    /**
     * Displays the Leader Board View. Clears the screen, draws the title, and shows instructions
     * to go back along with a placeholder for displaying the leaderboard. The method handles IOExceptions
     * internally.
     */
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            terminal.clearScreen();

            drawTitle(TextColor.ANSI.RED);
            String[] lines = asciiArt.split("\n");

            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

            tg.putString(2, lines.length + 4, "Press 'B' to go back");
            tg.putString(2, lines.length + 5, "Showing Leaderboard");

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

