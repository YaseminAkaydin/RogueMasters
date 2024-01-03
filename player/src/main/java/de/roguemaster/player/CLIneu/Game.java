package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.roguemaster.player.CLIneu.View.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Dummy Version des Game Main Views
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
    private ViewBuilder viewBuilder;

    Logger logger = Logger.getLogger(getClass().getName());

    private AtomicBoolean running = new AtomicBoolean(true);
    private AtomicBoolean gameStarted = new AtomicBoolean(false);

    public Game(Terminal terminal) {
        this.terminal = terminal;
        this.viewBuilder = new ViewBuilder(terminal);
        startScreenView = viewBuilder.getStartScreenView();
        startingLobbyView = viewBuilder.getStartingLobbyView();
        joiningLobbyView = viewBuilder.getJoiningLobbyView();
        leaderBoardView = viewBuilder.getLeaderBoardView();
        currentView = startScreenView;
    }

    public void run2() throws IOException {

        // Seperate Thread for inputhandling
        Thread inputThread = new Thread(this::handleInput);
        inputThread.start();

        while (running.get()) {
            try {
                // Display the current view
                currentView.display();

                // Init Game when starting own lobby
                if (currentView instanceof StartingLobbyView && (viewBuilder.getDungeonData() != null)) {
                    this.dungeonMapView = viewBuilder.getDungeonMapView();
                    this.mainGameView = viewBuilder.getMainGameView();
                    this.inventoryView = viewBuilder.getInventoryView();
                    gameStarted.set(true);
                    currentView = mainGameView;
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
                running.set(false);
            }
        }

        inputThread.interrupt();
        terminal.close();
    }

    private void handleInput() {
        while (running.get()) {
            try {
                KeyStroke keyStroke = terminal.pollInput();
                if (keyStroke != null) {
                    processInput(keyStroke);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Inventarview hinzufügen
    private void processInput(KeyStroke keyStroke) {
        // Handle different inputs for each view
        if (currentView instanceof StartScreenView) {
            switch (keyStroke.getCharacter()) {
                case '1':
                    logger.info("Creating Dungeon and switch to startingLobbyView");
                    startingLobbyView = viewBuilder.buildStartingLobbyView(terminal);
                    this.currentView = startingLobbyView;
                    // Simulate receiving dungeon data from the server

                    break;
                case '2':
                    logger.info("TBU");
                    //currentView = joiningLobbyView; // TODO: mechanics hier implementeiren
                    break;
                case '3':
                    currentView = leaderBoardView;
                    break;
                case '4':
                    running.set(false); // Exit the game
                    break;
                default:
                    break;
            }
        }
        if (currentView instanceof LeaderBoardView && (keyStroke.getCharacter() == 'b')) currentView = startScreenView;
        if (currentView instanceof MainGameView) {

            Map<Integer, String> options = mainGameView.getOptionMappings();
            char inputChar = keyStroke.getCharacter();
            int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;

            if (options.containsKey(selectedOption)) {
                String action = options.get(selectedOption);
                switch (action) {
                    case "Attack":
                        logger.info("Send Attack to Server"); // TODO: RPC ATTACK
                        break;
                    case "Do Nothing":
                        logger.info("Do Nothing");
                        break;
                    // Add cases for other actions like "Move NORTH", "Move SOUTH", etc.
                    case "Move NORTH":
                        logger.info("Move N");
                        mainGameView.updateRoom("NORTH"); // TODO: Move klären
                        break;
                    case "Move SOUTH":
                        logger.info("Move S");
                        mainGameView.updateRoom("SOUTH");
                        break;
                    case "Move EAST":
                        logger.info("Move E");
                        mainGameView.updateRoom("EAST");
                        break;
                    case "Move WEST":
                        logger.info("Move W");
                        mainGameView.updateRoom("WEST");
                        break;
                    case "Pick Up Item":
                        logger.info("Pick up Item");
                        break;
                    default:
                        logger.info("Default: Do Nothing");
                        break;
                }
            }
        }
        if (gameStarted.get()) {
            switch (keyStroke.getCharacter()) {
                case 'i':
                    currentView = inventoryView;
                    break;
                case 's':
                    currentView = mainGameView;
                    break;
                case 'm':
                    currentView = dungeonMapView;
                    break;
                default:
                    break;
            }

        }
    }

    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();

            Game game = new Game(terminal);
            game.run2();

            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
