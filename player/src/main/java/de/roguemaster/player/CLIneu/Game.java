package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.View.*;

import java.io.IOException;

/**
 * Dummy Version des Game Main Views
 * TODO: Dummy MainLoop löschen und überarbeiten + Multithreaded für inputs lesen (Alle 5 Sek senden wir daten)
 */
public class Game {
    private Terminal terminal;
    private ViewComponent currentView;
    private MainGameView mainGameView;
    private DungeonMapView dungeonMapView;
    private InventoryView inventoryView;
    private StartScreenView startScreenView;
    private StartingLobbyView startingLobbyView;
    private JoiningLobbyView joiningLobbyView;
    private LeaderBoardView leaderBoardView;

    private InventoryAction inventoryAction = InventoryAction.VIEWING;

    private boolean inInventoryOption = false;
    private int inventoryOption = 0;

    public Game(Terminal terminal) {
        this.terminal = terminal;
        // Initialize views
        ViewBuilder viewBuilder = new ViewBuilder(terminal);
        mainGameView = viewBuilder.getMainGameView();
        dungeonMapView = viewBuilder.getDungeonMapView();
        inventoryView = viewBuilder.getInventoryView();
        startScreenView = viewBuilder.getStartScreenView();
        startingLobbyView = viewBuilder.getStartingLobbyView();
        joiningLobbyView = viewBuilder.getJoiningLobbyView();
        leaderBoardView = viewBuilder.getLeaderBoardView();
        currentView = mainGameView;
    }


    public void run() throws IOException {
        boolean running = true;
        while (running) {
            System.out.println("Current view: " + currentView.getClass().getSimpleName());
            currentView.display();
            KeyStroke keyStroke = terminal.readInput();
            if(keyStroke.getCharacter() == 'i') currentView = inventoryView;
            if(keyStroke.getCharacter() == 's') currentView = mainGameView;
            if(keyStroke.getCharacter() == 'm') currentView = dungeonMapView;
            if (currentView instanceof StartScreenView) {
                if (keyStroke.getKeyType() == KeyType.Character) {
                    switch (keyStroke.getCharacter()) {
                        case '1': // Start Lobby
                            currentView = startingLobbyView;
                            currentView.display();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            currentView = mainGameView;
                            break;
                        case '2': // Join Lobby
                            currentView = joiningLobbyView;
                            currentView.display();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            currentView = mainGameView;
                            break;
                        case '3': // See Leaderboard
                            currentView = leaderBoardView;
                            break;
                        case '4': // Exit
                            running = false;
                            break;
                    }
                }
            } else if (currentView instanceof InventoryView) {
                InventoryView inventoryView = (InventoryView) currentView;
                // Handle inventory options based on InventoryAction
                handleInventoryInput(keyStroke, inventoryView);
            }
        }
    }

    /**
     * Handles the inventory input based on the current action.
     *
     * @param keyStroke     The keystroke from the user.
     * @param inventoryView The inventory view to update and display.
     * @throws IOException if there is an input/output error.
     */
    private void handleInventoryInput(KeyStroke keyStroke, InventoryView inventoryView) throws IOException {
        if (keyStroke.getKeyType() == KeyType.Character) {
            if (inventoryAction == InventoryAction.VIEWING) {
                switch (keyStroke.getCharacter()) {
                    case '1':
                        inventoryAction = InventoryAction.DROP_ITEM;
                        inventoryView.displayDropOptions();
                        break;
                    case '2':
                        inventoryAction = InventoryAction.USE_CONSUMABLE;
                        // inventoryView.displayUseConsumableOptions();
                        break;
                    case '3':
                        inventoryAction = InventoryAction.VIEWING;
                        currentView = mainGameView;
                        break;
                }
            } else if (inventoryAction == InventoryAction.DROP_ITEM) {
                char itemIndexChar = keyStroke.getCharacter();
                int itemIndex = Character.getNumericValue(itemIndexChar) - 1;
                inventoryView.dropItem(itemIndex);
                inventoryAction = InventoryAction.VIEWING;
                inventoryView.display(); // Refresh the inventory view
            }
            // Add logic for USE_CONSUMABLE
        }
    }

    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();

            Game game = new Game(terminal);
            game.run();

            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
