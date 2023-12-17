package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.View.*;

import java.io.IOException;

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

    private boolean inInventoryOption = false;
    private int inventoryOption = 0;

    public Game(Terminal terminal) {
        this.terminal = terminal;
        // Initialize views
        ViewBuilder viewBuilder = new ViewBuilder();
        mainGameView = viewBuilder.buildMainGameView(terminal);
        dungeonMapView = viewBuilder.buildDungeonMapView(terminal);
        inventoryView = viewBuilder.buildInventoryView(terminal);
        startScreenView = viewBuilder.buildStartScreenView(terminal);
        startingLobbyView = viewBuilder.buildStartingLobbyView(terminal);
        joiningLobbyView = viewBuilder.buildJoiningLobbyView(terminal);
        leaderBoardView = viewBuilder.buildLeaderBoardView(terminal);
        currentView = mainGameView;
    }

    public void run() throws IOException {
        boolean running = true;
        while (running) {
            currentView.display();
            KeyStroke keyStroke = terminal.readInput();

            if (currentView instanceof StartScreenView) {
                if (keyStroke.getKeyType() == KeyType.Character) {
                    switch (keyStroke.getCharacter()) {
                        case '1': // Start Lobby
                            currentView = startingLobbyView;
                            break;
                        case '2': // Join Lobby
                            currentView = joiningLobbyView;
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
                if (!inInventoryOption) {
                    // We are viewing the inventory, waiting for the user to select an option
                    if (keyStroke.getKeyType() == KeyType.Character) {
                        switch (keyStroke.getCharacter()) {
                            case '1':
                                // Drop item option
                                inInventoryOption = true;
                                inventoryOption = 1;
                                break;
                            case '2':
                                // Use consumable option
                                inInventoryOption = true;
                                inventoryOption = 2;
                                break;
                            case '3':
                                // Go back to main view
                                currentView = mainGameView;
                                break;
                        }
                    }
                } else {
                    // Here we handle the sub-options for "Drop Item"
                    if (keyStroke.getKeyType() == KeyType.Character && inventoryOption == 1) {
                        // Assume '1', '2', '3', etc. are the keys for item indices in the inventory
                        char itemIndexChar = keyStroke.getCharacter();
                        int itemIndex = Character.getNumericValue(itemIndexChar);
                        //dropItem(itemIndex);  // Method to handle item dropping
                        inInventoryOption = false; // Reset the flag
                    }
                    // If we have other sub-options for inventory, handle them similarly here
                }
            }
        }
    }

}
