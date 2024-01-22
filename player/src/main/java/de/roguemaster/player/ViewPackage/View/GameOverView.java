package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * The GameOverView class is responsible for displaying the game over screen. It is a subclass of
 */
public class GameOverView extends ViewComponent{

    String gameOverStyle = "\n" +
            "    /$$$$$$   /$$$$$$  /$$      /$$ /$$$$$$$$        /$$$$$$  /$$    /$$ /$$$$$$$$ /$$$$$$$ \n" +
            "   /$$__  $$ /$$__  $$| $$$    /$$$| $$_____/       /$$__  $$| $$   | $$| $$_____/| $$__  $$\n" +
            "  | $$  \\__/| $$  \\ $$| $$$$  /$$$$| $$            | $$  \\ $$| $$   | $$| $$      | $$  \\ $$\n" +
            "  | $$ /$$$$| $$$$$$$$| $$ $$/$$ $$| $$$$$         | $$  | $$|  $$ / $$/| $$$$$   | $$$$$$$/\n" +
            "  | $$|_  $$| $$__  $$| $$  $$$| $$| $$__/         | $$  | $$ \\  $$ $$/ | $$__/   | $$__  $$\n" +
            "  | $$  \\ $$| $$  | $$| $$\\  $ | $$| $$            | $$  | $$  \\  $$$/  | $$      | $$  \\ $$\n" +
            "  |  $$$$$$/| $$  | $$| $$ \\/  | $$| $$$$$$$$      |  $$$$$$/   \\  $/   | $$$$$$$$| $$  | $$\n" +
            "   \\______/ |__/  |__/|__/     |__/|________/       \\______/     \\_/    |________/|__/  |__/\n" +
            "                                                                                            \n" +
            "                                                                                            \n" +
            "                                                                                            \n";

    /**
     * Constructs a new GameOverView with the specified Terminal.
     *
     * @param terminal The Terminal object used for displaying the Game Over screen.
     */
    public GameOverView(Terminal terminal) {
        super(terminal);
    }
    /**
     * Displays the game over screen. Clears and initializes the graphics, then draws the 'Game Over'
     * message with a specified color. The method handles IOExceptions and throws a RuntimeException
     * in case of an error during display.
     */
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            drawGameOver(TextColor.ANSI.MAGENTA_BRIGHT);
            // set the terminal size to 400x800
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Draws the 'Game Over' message on the terminal in the specified text color. The message style is
     * determined by the 'gameOverStyle' string which is rendered line by line.
     *
     * @param textColor The color to be used for the 'Game Over' text.
     */
    protected void drawGameOver(TextColor textColor) {
        tg.setForegroundColor(textColor);
        String[] lines = gameOverStyle.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tg.putString(2, 2 + i, lines[i]);
        }
    }
}
