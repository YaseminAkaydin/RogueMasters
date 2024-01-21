package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * The ViewComponent class is the abstract superclass for all views. It defines the basic structure
 */
public abstract class ViewComponent {

    protected State currentState;

    /**
     * The State enum represents the different states of the view. The view can be in the main options
     * state, the drop item state, or the use consumable state.
     */
    public enum State {
        MAIN_OPTIONS,
        DROP_ITEM,
        USE_CONSUMABLE,
        EQUIP_ITEM
    }

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

    /**
     * Abstract method to display the current view. Must be implemented by subclasses to define
     * how each specific view is rendered on the screen.
     */
    public abstract void display();

    /**
     * Constructs a ViewComponent with a reference to the Terminal and the current GameState.
     *
     * @param terminal The Terminal object used for displaying the view.
     * @param gameState The current state of the game, containing all necessary information.
     */
    protected ViewComponent(Terminal terminal, GameState gameState) {
        this.terminal = terminal;
        this.gameState = gameState;
    }

    /**
     * Constructs a ViewComponent with a reference to the Terminal.
     *
     * @param terminal The Terminal object used for displaying the view.
     */
    protected ViewComponent(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Displays player statistics such as level, health, experience, and available actions ('M' for Map,
     * 'I' for Inventory, 'S' for RoomView) at the bottom of the screen.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @throws IOException If an I/O error occurs.
     */
    protected void displayPlayerStats(TextGraphics tg) throws IOException {
        int statsStartY = terminal.getTerminalSize().getRows() - 1; // Below the room
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, statsStartY,
                "LEVEL: " + gameState.getLocalPlayer().getLevel() +
                " - " + gameState.getLocalPlayer().getHp() +
                "/" + gameState.getLocalPlayer().getMaxHp() +
                "HP - EXP: " + gameState.getLocalPlayer().getExperience() +
                "  -- 'M' = Map -- 'I' = Inventory -- 'S' = RoomView");
    }

    /**
     * Clears the screen and initializes a new TextGraphics object for drawing on the terminal.
     *
     * @throws IOException If an I/O error occurs during screen clearing or TextGraphics initialization.
     */
    protected void clearAndInitializeGraphics() throws IOException {
        terminal.clearScreen();
        tg = terminal.newTextGraphics();
    }

    /**
     * Draws the title in the specified text color. The title is represented by ASCII art stored in a string.
     *
     * @param textColor The color to be used for the title text.
     */
    protected void drawTitle(TextColor textColor) {
        tg.setForegroundColor(textColor);
        String[] lines = asciiArt.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tg.putString(2, 2 + i, lines[i]);
        }
    }

    // Getter
    public State getCurrentState() {
        return currentState;
    }
}
