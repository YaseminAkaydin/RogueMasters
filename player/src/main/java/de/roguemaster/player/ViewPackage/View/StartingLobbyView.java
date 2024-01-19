package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Dummy Platzhalter für die "Lobby"
 */
public class StartingLobbyView extends ViewComponent {

    private static final String[] LOADING_FRAMES = new String[]{
            "Loading the Game.  ",
            "Loading the Game.. ",
            "Loading the Game..."
    };

    public StartingLobbyView(Terminal terminal) {
        super(terminal);
    }

    /**
     *
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
        } /*catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}

