package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * The StartingLobbyView class is responsible for displaying the Starting Lobby view on the terminal.
 */
public class StartingLobbyView extends ViewComponent {

    private static final String[] LOADING_FRAMES = new String[]{
            "Loading the Game.  ",
            "Loading the Game.. ",
            "Loading the Game..."
    };

    /**
     * Constructs a new StartingLobbyView with the specified Terminal.
     *
     * @param terminal The Terminal object used for displaying this view.
     */
    public StartingLobbyView(Terminal terminal) {
        super(terminal);
    }

    /**
     * Displays the Starting Lobby View. Clears the screen, draws the title, and shows a loading
     * animation. The method handles IOExceptions internally. Note: The Thread.sleep call for the
     * animation is currently commented out.
     */
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            terminal.clearScreen();

            drawTitle(TextColor.ANSI.RED);

            String[] lines = asciiArt.split("\n");
            // Create a loading animation
            for (String loadingFrame : LOADING_FRAMES) {
                tg.putString(2, lines.length + 4, loadingFrame);
                terminal.flush();
                /*Thread.sleep(500);*/
            }

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

