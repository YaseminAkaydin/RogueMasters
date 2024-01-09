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


    // TODO: komplexität reduzieren
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
                    // currentView = joiningLobbyView; // TODO: mechanics hier implementeiren
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
            /**
             * command: attack, move, pickupitem, donothig, use item
             * target: /, r+{rID}, /, /, i{iID},
             */

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
                        mainGameView.updateRoom("NORTH");
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

        if (currentView instanceof InventoryView) {
            // Ensure currentView is indeed an instance of InventoryView before casting
            InventoryView inventoryView = (InventoryView) currentView;

            Map<Integer, String> options = inventoryView.getOptionMappings();

            char inputChar = keyStroke.getCharacter();
            int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;

            if (inventoryView.getCurrentState() == InventoryView.State.DROP_ITEM) {
                Map<Integer, String> dropItemOptionMappings = inventoryView.getDropItemOptionMappings();
                if (dropItemOptionMappings.containsKey(selectedOption)) {
                    for (Map.Entry<Integer, String> entry : dropItemOptionMappings.entrySet()) {
                        System.out.println("DROP_ITEM: " + entry.getKey() + " " + entry.getValue());
                    }
                    String action = dropItemOptionMappings.get(selectedOption);
                    switch (action) {
                        case "Drop 1":
                            logger.info("DROP_ITEM: send drop 1 to server");
                            break;
                        case "Drop 2":
                            logger.info("DROP_ITEM: send drop 2 to server");
                            break;
                        // Add cases for other actions like "Move NORTH", "Move SOUTH", etc.
                        case "Drop 3":
                            logger.info("DROP_ITEM: send drop 3 to server");
                            break;
                        case "Drop 4":
                            logger.info("DROP_ITEM: send drop 4 to server");

                            break;
                        case "Go back":
                            logger.info("Go Back");
                            inventoryView.setCurrentState(InventoryView.State.MAIN_OPTIONS);
                            break;
                        default:
                            logger.info("Default: Do Nothing DROP_ITEM");
                            break;
                    }

                }

            }
            if (inventoryView.getCurrentState() == InventoryView.State.USE_CONSUMABLE) {
                Map<Integer, String> useConsumableOptionMappings = inventoryView.getUseConsumableOptionMappings();
                if (useConsumableOptionMappings.containsKey(selectedOption)) {
                    for (Map.Entry<Integer, String> entry : useConsumableOptionMappings.entrySet()) {
                        System.out.println("USE_CONSUMABLE: " + entry.getKey() + " " + entry.getValue());
                    }
                    String action = useConsumableOptionMappings.get(selectedOption);
                    // das nehmen als "Use n" useConsumableOptionMappings.get(selectedOption);
                    switch (action) {
                        case "Use 1":
                            logger.info("USE_CONSUMABLE: send use 1 to server");
                            break;
                        case "Use 2":
                            logger.info("USE_CONSUMABLE: send use 2 to server");
                            break;
                        // Add cases for other actions like "Move NORTH", "Move SOUTH", etc.
                        case "Use 3":
                            logger.info("USE_CONSUMABLE: send use 3 to server");
                            break;
                        case "Use 4":
                            logger.info("USE_CONSUMABLE: send use 4 to server");
                            break;
                        case "Go back":
                            logger.info("Go Back");
                            inventoryView.setCurrentState(InventoryView.State.MAIN_OPTIONS);
                            break;
                        default:
                            logger.info("Default: Do Nothing USE_CONSUMABLE");
                            break;
                    }

                }

            }
            if (inventoryView.getCurrentState() == InventoryView.State.MAIN_OPTIONS) {
                if (options.containsKey(selectedOption)) {
                    for (Map.Entry<Integer, String> entry : options.entrySet()) {
                        System.out.println("MAINOPTIONS: " + entry.getKey() + " " + entry.getValue());
                    }
                    String action = options.get(selectedOption);
                    switch (action) {
                        case "Drop Item":
                            inventoryView.setCurrentState(InventoryView.State.DROP_ITEM);
                            logger.info("MAINOPTIONS: state = DROP_ITEM");
                            currentView.display();
                            break;
                        case "Use Consumable":
                            inventoryView.setCurrentState(InventoryView.State.USE_CONSUMABLE);
                            logger.info("MAINOPTIONS: state = USE_CONSUMABLE");
                            currentView.display();
                            break;
                        default:
                            logger.info("Default: Do Nothing MAINOPTIONS");
                            break;
                    }
                }
            }


        }
        if (gameStarted.get()) {
            switch (keyStroke.getCharacter()) {
                case 'i':
                    currentView = inventoryView;
                    inventoryView.setCurrentState(InventoryView.State.MAIN_OPTIONS);
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
