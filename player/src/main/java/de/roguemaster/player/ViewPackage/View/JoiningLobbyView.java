package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * The JoiningLobbyView class is responsible for displaying the Joining Lobby view on the terminal.
 */
public class JoiningLobbyView extends ViewComponent {

    private String currentID = "";
    private boolean success = true;

    /**
     * Constructs a new JoiningLobbyView with the specified Terminal.
     *
     * @param terminal The Terminal object used for displaying this view.
     */
    public JoiningLobbyView(Terminal terminal) {
        super(terminal);
    }

    /**
     * Displays the Joining Lobby View. Clears the screen, draws the title, and shows a prompt for
     * entering the lobby code. If the previously entered code was invalid, displays an error message.
     * The method handles IOExceptions internally.
     */
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            terminal.clearScreen();
            drawTitle(TextColor.ANSI.RED);
            String[] lines = asciiArt.split("\n");
            tg.putString(2, lines.length + 4, "Input Lobbycode: " + currentID);

            if (!success) {
                tg.putString(2, lines.length + 6, "INVALID CODE TRY AGAIN!");
            }
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Getter and Setter
    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public void setSuccess(boolean b) {
        this.success = b;
    }
}

