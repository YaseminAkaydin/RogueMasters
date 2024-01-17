package de.roguemaster.player.ViewPackage;

import com.google.gson.reflect.TypeToken;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


import de.roguemaster.player.DataContainer;
import de.roguemaster.player.JSONManager;
import de.roguemaster.player.GameState;
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
    private final ViewBuilder viewBuilder;

    Logger logger = Logger.getLogger(getClass().getName());
    private ConcurrentLinkedQueue<Command> commandQueue = new ConcurrentLinkedQueue<>();

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean gameStarted = new AtomicBoolean(false);

    private String currentInput = "";


    public Game(Terminal terminal) {
        this.terminal = terminal;
        this.viewBuilder = new ViewBuilder(terminal);
        this.startScreenView = viewBuilder.getStartScreenView();
        this.startingLobbyView = viewBuilder.getStartingLobbyView();
        this.joiningLobbyView = viewBuilder.getJoiningLobbyView();
        this.leaderBoardView = viewBuilder.getLeaderBoardView();
        this.currentView = startScreenView;
    }

    public void run() throws IOException {

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
                logger.log(java.util.logging.Level.SEVERE, "Interrupted while sleeping", e);
                inputThread.interrupt();
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
                logger.log(java.util.logging.Level.SEVERE, "Interrupted while handling input", e);
                running.set(false);
            }
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
            processInventoryViewInput((InventoryView) currentView, keyStroke);
        }
        if (gameStarted.get()) {
            processGlobalInput(keyStroke);
        }
    }

    private void processJoinLobbyViewInput(KeyStroke keyStroke) {

        Character inputChar = keyStroke.getCharacter();
        Command command = null;

        // Überprüfen, ob eine gültige Nummernziffer eingegeben wurde
        if (inputChar != null && Character.isDigit(inputChar)) {
            currentInput += inputChar;
            joiningLobbyView.setCurrentID(currentInput);

            // Wenn die Länge fünf erreicht, verarbeiten
            if (currentInput.length() == 5) {
                processLobbyCode(currentInput);
                currentView = startingLobbyView;
                command = Command.joinLobby(currentInput);
                currentInput = ""; // Zurücksetzen der Eingabe für den nächsten Versuch
            }
        }
        // Add command to the queue
        if (command != null) {
            commandQueue.add(command);
        }
    }

    private void processLobbyCode(String lobbyCode) {
        // Beispiel: Umwandeln des Strings in eine Nummer und überprüfen, ob sie gültig ist
        try {
            int code = Integer.parseInt(lobbyCode);
            // Weitere Logik für Lobby-Code
        } catch (NumberFormatException e) {
            // Ungültige Eingabe, Fehlerbehandlung
        }
    }

    private void processStartScreenViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        Command command = null;
        switch (keyStroke.getCharacter()) {
            case '1':
                logger.info("Creating Dungeon and switch to startingLobbyView");
                this.currentView = startingLobbyView;
                // Simulate receiving dungeon data from the server
                //JoinLobby-CLIENT(n(sololobbystarten) ODER "LobbyCode Zahl 5stellig") --> success, lobbyID, charID
                command = Command.startLobby();
                break;
            case '2':
                logger.info("TBU");
                currentView = joiningLobbyView; // TODO: mechanics hier implementeiren,
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

    /*command: attack, move, pickupitem, donothig, use item
          target: /, r+{rID}, /, /, i{iID}*/
    private void processMainGameViewInput(KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        Map<Integer, String> options = mainGameView.getOptionMappings();
        char inputChar = keyStroke.getCharacter();
        int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;
        Command command = null;
        if (options.containsKey(selectedOption)) {
            String action = options.get(selectedOption);
            switch (action) {
                case "Attack":
                    logger.info("Send Attack to Server");
                    command = Command.attackCommand();
                    break;
                case "Do Nothing":
                    logger.info("Do Nothing");
                    command = Command.doNothingCommand();
                    break;
                // Add cases for other actions like "Move NORTH", "Move SOUTH", etc.
                case "Move NORTH":
                    logger.info("Move N");
                    mainGameView.updateRoom("NORTH");
                    command = Command.moveCommand(mainGameView.getCurrentRoomId());
                    break;
                case "Move SOUTH":
                    logger.info("Move S");
                    mainGameView.updateRoom("SOUTH");
                    command = Command.moveCommand(mainGameView.getCurrentRoomId());
                    break;
                case "Move EAST":
                    logger.info("Move E");
                    mainGameView.updateRoom("EAST");
                    command = Command.moveCommand(mainGameView.getCurrentRoomId());
                    break;
                case "Move WEST":
                    logger.info("Move W");
                    mainGameView.updateRoom("WEST");
                    command = Command.moveCommand(mainGameView.getCurrentRoomId());
                    break;
                case "Pick Up Item":
                    logger.info("Pick up Item");
                    command = Command.pickupCommand(mainGameView.getRoomData().getItems().getId());
                    break;
                default:
                    logger.info("Default: Do Nothing");
                    command = Command.doNothingCommand();
                    break;
            }
        }
        // Add command to the queue
        if (command != null) {
            commandQueue.add(command);
        }
    }


    private void processInventoryViewInput(InventoryView inventoryView, KeyStroke keyStroke) {
        if (keyStroke.getCharacter() == null) return;
        char inputChar = keyStroke.getCharacter();
        int selectedOption = Character.isDigit(inputChar) ? Character.getNumericValue(inputChar) : -1;
        Command command = null;
        if (inventoryView.getCurrentState() == InventoryView.State.DROP_ITEM) {
            Map<Integer, String> dropItemOptionMappings = inventoryView.getDropItemOptionMappings();
            if (dropItemOptionMappings.containsKey(selectedOption)) {
                String action = dropItemOptionMappings.get(selectedOption);
                switch (action) {
                    case "Drop 1":
                        logger.info("DROP_ITEM: send drop 1 to server");
                        //command = Command.dropItemCommand(); TODO: xx
                        break;
                    case "Drop 2":
                        logger.info("DROP_ITEM: send drop 2 to server");
                        //command = Command.dropItemCommand();
                        break;
                    // Add cases for other actions like "Move NORTH", "Move SOUTH", etc.
                    case "Drop 3":
                        logger.info("DROP_ITEM: send drop 3 to server");
                        //command = Command.dropItemCommand();
                        break;
                    case "Drop 4":
                        logger.info("DROP_ITEM: send drop 4 to server");
                        //command = Command.dropItemCommand();
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
                String action = useConsumableOptionMappings.get(selectedOption);
                switch (action) {
                    case "Use Book":
                        logger.info("USE_CONSUMABLE: send Use Book to server");
                        break;
                    case "Use Potion":
                        logger.info("USE_CONSUMABLE: send Use Potion to server");
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
        Map<Integer, String> options = inventoryView.getOptionMappings();
        if (inventoryView.getCurrentState() == InventoryView.State.MAIN_OPTIONS && (options.containsKey(selectedOption))) {
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
            case 'm':
                currentView = dungeonMapView;
                break;
            default:
                break;
        }
    }

    // Add a method to retrieve and remove a command from the queue
    public Command getNextCommand() {
        return commandQueue.poll();
    }

    public boolean isGameStarted() {
        return gameStarted.get();
    }

    /**
     * Update der Daten für den ViewBuilder mit den Incoming daten nach dem vorgegebenen Format
     * @param gameState
     */
    public void updateGameState(String gameState) {
        logger.info("Thread UpdateGameState started...");
        // Item: id, typ, name, description, itemAttribute
        // enemie: id, name, dangerLevel, maxHp, hp, id
        // playerliste max 4 player: level, experience, maxExperience, inventory, maxHp, hp, attack, defense, id
        // roomList: n räume: id, ein item, ein enemie, 4 adjazente räume SOUTH x ID


        JSONManager<GameState> jsonManager =new JSONManager<>(new TypeToken<DataContainer<GameState>>(){});
        // Use read to get the data out of the EXAMPLE JSON
        GameState gameState1 = jsonManager.read(gameState);

        logger.info("Thread UpdateGameState finished...");

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
