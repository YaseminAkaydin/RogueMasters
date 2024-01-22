package de.roguemaster.player.ViewPackage;

import com.google.gson.reflect.TypeToken;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;


import de.roguemaster.player.ViewPackage.View.Actions.InventoryActions.GoBackAction;
import de.roguemaster.player.ViewPackage.View.Actions.InventoryActions.ItemAction;
import de.roguemaster.player.ViewPackage.View.MainActions.MainAction;
import de.roguemaster.player.cs.DataContainer;
import de.roguemaster.player.cs.JSONManager;
import de.roguemaster.player.ViewPackage.DataForView.GameState;
import de.roguemaster.player.ViewPackage.View.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Main class for the game. It handles the game loop and user input.
 * It also initializes and updates the game state based on the JSON string
 * received from the server.
 */
public class Game {
    private final Terminal terminal;
    private ViewComponent currentView; // View getting displayed

    private MainGameView mainGameView;
    private DungeonMapView dungeonMapView;
    private InventoryView inventoryView;

    private final StartScreenView startScreenView;
    private final StartingLobbyView startingLobbyView;
    private final JoiningLobbyView joiningLobbyView;
    private final LeaderBoardView leaderBoardView;
    private final GameOverView gameOverView;

    Logger logger = Logger.getLogger(getClass().getName());
    private final ConcurrentLinkedQueue<Command> commandQueue = new ConcurrentLinkedQueue<>();

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean gameStarted = new AtomicBoolean(false);

    private String currentInput = "";
    private boolean successJoinLobby = false;

    private GameState gameState;
    private int localPlayerID;
    private int lobbyID;

    public Game(Terminal terminal) {
        this.terminal = terminal;
        this.startScreenView = new StartScreenView(terminal);
        this.startingLobbyView = new StartingLobbyView(terminal);
        this.joiningLobbyView = new JoiningLobbyView(terminal);
        this.leaderBoardView = new LeaderBoardView(terminal);
        this.gameOverView = new GameOverView(terminal);

        this.currentView = startScreenView;
    }

    /**
     * Initializes and starts the main game loop. It creates a separate thread for handling input
     * and displays the current view. The loop continues as long as the game is running.
     * If the current view is a StartingLobbyView and the game state is null, it initializes
     * the game views and sets the game as started. The method also handles InterruptedException
     * by stopping the input thread and closing the terminal.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void run() throws IOException {
        // Seperate Thread for inputhandling
        Thread inputThread = new Thread(this::handleInput);
        inputThread.start();

        while (running.get()) {
            try {
                currentView.display();

                if (currentView instanceof StartingLobbyView && gameState == null) {
                    initializeGame();
                }
                Thread.sleep(10);

            } catch (InterruptedException e) {
                running.set(false);
                Thread.currentThread().interrupt();

            }
        }
        inputThread.interrupt();
    }

    /**
     * Initializes the game views and sets the game as started. It waits until the game state
     * @throws InterruptedException
     */
    public void initializeGame() throws InterruptedException {
        while (gameState == null) {
            logger.info("Waiting for DungeonData...");
            currentView.display();
            Thread.sleep(1000);
        }
        this.dungeonMapView = new DungeonMapView(terminal, this.gameState);
        this.mainGameView = new MainGameView(terminal, this.gameState);
        this.inventoryView = new InventoryView(terminal, this.gameState);
        gameState.setLocalPlayerID(localPlayerID);
        gameState.setLobbyID(lobbyID);
        gameStarted.set(true);
        currentView = mainGameView;
    }


    /**
     * Continuously checks for and processes user input while the game is running.
     * If the 'ESC' key is pressed, it exits the game and interrupts the input handling thread.
     * Also handles IOExceptions that might occur during input processing.
     */
    private void handleInput() {
        while (running.get()) {
            try {
                if (successJoinLobby) {
                    logger.info("Command was good, game start");
                    currentView = startingLobbyView;
                    successJoinLobby = false;
                }
                KeyStroke keyStroke = terminal.pollInput();
                // IF user presses ESC, exit the game
                if (keyStroke != null && keyStroke.getKeyType() == KeyType.Escape) {
                    System.out.println("ESCAPE PRESSED");
                    running.set(false);
                    gameStarted.set(false);
                    // stop the thread for handling input
                    Thread.currentThread().interrupt();
                }
                if (keyStroke != null) {
                    processInput(keyStroke);
                }
            } catch (IOException e) {
                logger.log(java.util.logging.Level.SEVERE, "Interrupted while handling input", e);
                running.set(false);
            }
        }
    }

    /**
     * Updates the game state based on a given JSON string. Initializes various game views
     * (like DungeonMapView, MainGameView, InventoryView) using the new game state and updates
     * the current view.
     *
     * @param gameState The JSON string representing the new game state.
     */
    public boolean updateGameState(String gameState) {
        System.out.println("Player: " + localPlayerID + " received GameState");
        JSONManager<GameState> jsonManager = new JSONManager<>(new TypeToken<DataContainer<GameState>>() {
        });
        this.gameState = jsonManager.read(gameState);
        this.gameState.setLocalPlayerID(localPlayerID);
        this.gameState.initGameState();
        if (this.gameState.getLocalPlayer() == null){
            currentView = gameOverView;
            gameStarted.set(false);
            return false;
        }
        this.dungeonMapView = new DungeonMapView(terminal, this.gameState);
        this.mainGameView = new MainGameView(terminal, this.gameState);
        this.inventoryView = new InventoryView(terminal, this.gameState);
        System.out.println(gameState);
        updateCurrentView();
        return true;
    }
    /**
     * Updates the current view of the game based on the instance of the view.
     * It switches the current view to one of the main game views such as MainGameView,
     * DungeonMapView, InventoryView, or StartingLobbyView.
     */
    private void updateCurrentView() {
        if (currentView instanceof MainGameView) {
            currentView = mainGameView;
        } else if (currentView instanceof DungeonMapView) {
            currentView = dungeonMapView;
        } else if (currentView instanceof InventoryView) {
            currentView = inventoryView;
        } else if (currentView instanceof StartingLobbyView) {
            currentView = mainGameView;
        }
    }
    /**
     * Processes a given keystroke based on the current view of the game.
     * It delegates the processing to specific methods depending on whether the
     * current view is StartScreenView, JoiningLobbyView, LeaderBoardView,
     * MainGameView, or InventoryView. Additionally, it processes global inputs
     * if the game has started.
     *
     * @param keyStroke The KeyStroke object to be processed.
     */
    private void processInput(KeyStroke keyStroke) {
        if (currentView instanceof StartScreenView) {
            processStartScreenViewInput(keyStroke);
        } else if (currentView instanceof JoiningLobbyView) {
            processJoinLobbyViewInput(keyStroke);
        } else if (currentView instanceof LeaderBoardView) {
            processLeaderBoardViewInput(keyStroke);
        } else if (currentView instanceof MainGameView) {
            processMainGameViewInput(keyStroke);
        } else if (currentView instanceof InventoryView) {
            processInventoryViewInput(keyStroke);
        }
        if (gameStarted.get()) {
            processGlobalInput(keyStroke);
        }
    }
    /**
     * Processes user input in the Join Lobby View. If 'b' is pressed, switches to Start Screen View.
     * Handles digit input for lobby ID entry. Processes the lobby join command and updates the view
     * based on the success of joining the lobby. Adds the command to the command queue if valid.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processJoinLobbyViewInput(KeyStroke keyStroke) {

        Character inputChar = keyStroke.getCharacter();
        Command command = null;

        if (keyStroke.getCharacter() == 'b') {
            currentView = startScreenView;
        }
        // Überprüfen, ob eine gültige Nummernziffer eingegeben wurde
        if (inputChar != null && Character.isDigit(inputChar)) {
            currentInput += inputChar;
            joiningLobbyView.setCurrentID(currentInput);

            // Wenn die Länge fünf erreicht, verarbeiten
            if (currentInput.length() == 5) {
                command = Command.joinLobby(currentInput);
                currentInput = ""; // Zurücksetzen der Eingabe für den nächsten Versuch
                if (successJoinLobby) {
                    logger.info("Command was good, game start");
                    currentView = startingLobbyView;
                    successJoinLobby = false;
                } else {
                    logger.info("Command was bad, game not start");
                    joiningLobbyView.setCurrentID("");
                }
            }
        }
        // Add command to the queue
        if (command != null) {
            logger.info("Command added to queue");
            commandQueue.add(command);
        }

    }

    /**
     * Processes user input on the Start Screen View. Handles various commands like starting a lobby,
     * joining a lobby, viewing the leaderboard, or exiting the game based on the key pressed.
     * Adds valid commands to the command queue.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processStartScreenViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        Command command = null;
        switch (keyStroke.getCharacter()) {
            case '1':
                command = Command.startLobby();
                System.out.println("StartLobbyCommand sent" + command.getCommand() + "\n");
                break;
            case '2':
                currentView = joiningLobbyView;
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
        // Add command to the queue
        if (command != null) {
            commandQueue.add(command);
        }
    }

    /**
     * Processes user input in the Leader Board View. If 'b' is pressed, switches back to the Start
     * Screen View.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processLeaderBoardViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        if (keyStroke.getCharacter() == 'b') {
            currentView = startScreenView;
        }
    }

    /**
     * Processes user input in the Main Game View. Interprets digit input as a selection of game options
     * and executes corresponding actions. Adds generated commands from actions to the command queue.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processMainGameViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        char inputChar = keyStroke.getCharacter();
        int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;
        Map<Integer, MainAction> options = mainGameView.getOptionMappings();
        if (options.containsKey(selectedOption)) {
            MainAction action = options.get(selectedOption);
            action.createCommand();
            commandQueue.add(action.createCommand());
        }
    }

    /**
     * Processes user input in the Inventory View. Handles different states like dropping an item or using
     * a consumable, as well as navigation through the inventory menu. Executes actions or adds commands
     * to the command queue based on the selected options.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processInventoryViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        char inputChar = keyStroke.getCharacter();
        int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;

        if (currentView.getCurrentState() == ViewComponent.State.DROP_ITEM) {
            Map<Integer, ItemAction> dropItemOptionMappings = inventoryView.getDropItemOptionMappings();
            processItemAction(selectedOption, dropItemOptionMappings);
        }
        if (inventoryView.getCurrentState() == InventoryView.State.USE_CONSUMABLE) {
            Map<Integer, ItemAction> useConsumableOptionMappings = inventoryView.getUseConsumableOptionMappings();
            processItemAction(selectedOption, useConsumableOptionMappings);
        }
        if (inventoryView.getCurrentState() == InventoryView.State.EQUIP_ITEM) {
            Map<Integer, ItemAction> equipItemOptionMappings = inventoryView.getEquipItemOptionMappings();
            processItemAction(selectedOption, equipItemOptionMappings);
        }


        // Handles the menu state within inventory view
        Map<Integer, String> options = inventoryView.getOptionMappings();
        if (inventoryView.getCurrentState() == InventoryView.State.MAIN_OPTIONS && (options.containsKey(selectedOption))) {
            String action = options.get(selectedOption);
            switch (action) {
                case "Drop Item":
                    inventoryView.setCurrentState(InventoryView.State.DROP_ITEM);
                    break;
                case "Use Consumable":
                    inventoryView.setCurrentState(ViewComponent.State.USE_CONSUMABLE);
                    break;
                case "Equip Item":
                    inventoryView.setCurrentState(ViewComponent.State.EQUIP_ITEM);
                    break;
                default:
                    logger.info("Default: Do Nothing MAINOPTIONS");
                    break;
            }

        }
    }

    /**
     * Processes the selection of drop and use actions in the inventory based on user input. Executes
     * internal actions directly or adds external command actions to the command queue.
     *
     * @param selectedOption The selected option index.
     * @param itemOptionMappings A map of item actions indexed by option numbers.
     */
    private void processItemAction(int selectedOption, Map<Integer, ItemAction> itemOptionMappings) {
        if (itemOptionMappings.containsKey(selectedOption)) {
            ItemAction action = itemOptionMappings.get(selectedOption);
            if (action.isInternalAction()) {
                if (action instanceof GoBackAction) {
                    ((GoBackAction) action).execute(inventoryView);
                    currentView.display();
                }
            } else {
                commandQueue.add(action.createCommand());
            }
        }
    }

    /**
     * Processes global input commands applicable in any game state. Handles switching between different
     * views like inventory, main game, and dungeon map based on the user input.
     *
     * @param keyStroke The KeyStroke object representing the user input.
     */
    private void processGlobalInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        switch (keyStroke.getCharacter()) {
            case 'i':
                currentView = inventoryView;
                inventoryView.setCurrentState(InventoryView.State.MAIN_OPTIONS);
                break;
            case 's':
                currentView = mainGameView;
                break;
            case 'm', 'd':
                currentView = dungeonMapView;
                break;
            default:
                break;
        }
    }

    // Getter & Setter
    public Command getNextCommand() {
        return commandQueue.poll();
    }

    public boolean getRunning() {
        return running.get();
    }

    public void setSuccessJoinLobby(boolean successJoinLobby) {
        this.successJoinLobby = successJoinLobby;
    }

    public void setLocalPlayerID(int localPlayerID) {
        this.localPlayerID = localPlayerID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }
}
