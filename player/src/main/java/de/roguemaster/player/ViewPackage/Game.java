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
 * EIGENE TESTUMGEBUNG FÜR DUMMY MAINLOOP
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

    Logger logger = Logger.getLogger(getClass().getName());
    private final ConcurrentLinkedQueue<Command> commandQueue = new ConcurrentLinkedQueue<>();

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean gameStarted = new AtomicBoolean(false);

    private String currentInput = "";
    private boolean successJoinLobby = false;

    private GameState gameState;
    private int localPlayerID;

    public Game(Terminal terminal) {
        this.terminal = terminal;
        this.startScreenView = new StartScreenView(terminal);
        this.startingLobbyView = new StartingLobbyView(terminal);
        this.joiningLobbyView = new JoiningLobbyView(terminal);
        this.leaderBoardView = new LeaderBoardView(terminal);

        this.currentView = startScreenView;
    }

    public void run() throws IOException {

        // Seperate Thread for inputhandling
        Thread inputThread = new Thread(this::handleInput);
        inputThread.start();

        while (running.get()) {
            try {
                currentView.display();
                // Init Game when starting own lobby
                if ((currentView instanceof StartingLobbyView) && (gameState == null)) {
                    while (gameState == null) {
                        logger.info("Waiting for DungeonData...");
                        currentView.display();
                        Thread.sleep(1000);
                    }
                    this.dungeonMapView = new DungeonMapView(terminal, this.gameState);
                    this.mainGameView = new MainGameView(terminal, this.gameState);
                    this.inventoryView = new InventoryView(terminal, this.gameState);
                    gameState.setLocalPlayerID(localPlayerID);
                    gameStarted.set(true);
                    currentView = mainGameView;
                }

                Thread.sleep(100);

            } catch (InterruptedException e) {
                logger.log(java.util.logging.Level.SEVERE, "Interrupted while sleeping", e);
                inputThread.interrupt();
                terminal.close();
                running.set(false);
            }
        }

        inputThread.interrupt();
        terminal.close();
    }

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

    public void updateGameState(String gameState) {
        System.out.println("Player: " + localPlayerID + " received GameState");
        JSONManager<GameState> jsonManager = new JSONManager<>(new TypeToken<DataContainer<GameState>>() {
        });
        this.gameState = jsonManager.read(gameState);
        this.gameState.setLocalPlayerID(localPlayerID);
        this.gameState.initGameState();
        this.dungeonMapView = new DungeonMapView(terminal, this.gameState);
        this.mainGameView = new MainGameView(terminal, this.gameState);
        this.inventoryView = new InventoryView(terminal, this.gameState);
        System.out.println(gameState);
        updateCurrentView();


    }

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

    private void processLeaderBoardViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        if (keyStroke.getCharacter() == 'b') {
            currentView = startScreenView;
        }
    }

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

    private void processInventoryViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        char inputChar = keyStroke.getCharacter();
        int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;

        if (currentView.getCurrentState() == ViewComponent.State.DROP_ITEM) {
            Map<Integer, ItemAction> dropItemOptionMappings = inventoryView.getDropItemOptionMappings();
            processDropAndUse(selectedOption, dropItemOptionMappings);
        }
        if (inventoryView.getCurrentState() == InventoryView.State.USE_CONSUMABLE) {
            Map<Integer, ItemAction> useConsumableOptionMappings = inventoryView.getUseConsumableOptionMappings();
            processDropAndUse(selectedOption, useConsumableOptionMappings);

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
                default:
                    logger.info("Default: Do Nothing MAINOPTIONS");
                    break;
            }

        }
    }
    // Function for processing drop and use actions
    private void processDropAndUse(int selectedOption, Map<Integer, ItemAction> ItemOptionMappings) {
        if (ItemOptionMappings.containsKey(selectedOption)) {
            ItemAction action = ItemOptionMappings.get(selectedOption);
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

    public boolean isGameStarted() {
        return gameStarted.get();
    }

    public boolean getRunning() {
        return running.get();
    }

    public void setSuccessJoinLobby(boolean successJoinLobby) {
        this.successJoinLobby = successJoinLobby;
    }

    public int getLocalPlayerID() {
        return localPlayerID;
    }

    public void setLocalPlayerID(int localPlayerID) {
        this.localPlayerID = localPlayerID;
    }

}
