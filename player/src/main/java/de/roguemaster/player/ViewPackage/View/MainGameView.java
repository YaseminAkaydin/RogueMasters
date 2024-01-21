package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.GameState;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;
import de.roguemaster.player.ViewPackage.View.MainActions.*;


import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The MainGameView class is responsible for displaying the main game view on the terminal. The main game
 */
public class MainGameView extends ViewComponent {

    private final Map<Integer, MainAction> optionMappings;

    private static final int ROOM_WIDTH = 35;
    private static final int ROOM_HEIGHT = 17;
    private static final int ROOM_START_X = 2;
    private static final int ROOM_START_Y = 2;
    private static final int OPTIONS_START_X = ROOM_START_X + ROOM_WIDTH + 5;
    private static final int OPTIONS_START_Y = 2;
    private static final int STATS_Y_OFFSET = -1;
    private static final int DOOR_OFFSET = ROOM_WIDTH / 2;


    private Set<Point> uniqueCoordinates;

    /**
     * Constructs a new MainGameView with the specified Terminal and GameState. Initializes
     * the option mappings and generates unique coordinates for display elements within the room.
     *
     * @param terminal The Terminal object used for displaying this view.
     * @param gameState The current state of the game, containing all necessary information.
     */
    public MainGameView(Terminal terminal, GameState gameState) {
        super(terminal, gameState);
        optionMappings = new HashMap<>();
        uniqueCoordinates = generateUniqueCoordinates(5, ROOM_WIDTH, ROOM_HEIGHT, ROOM_START_X, ROOM_START_Y);

    }

    /**
     * Generates a set of unique coordinates within a specified area. The area is defined by its width,
     * height, and start coordinates. This method is used to position elements like players or items
     * within the game view.
     *
     * @param count The number of unique coordinates to generate.
     * @param width The width of the area.
     * @param height The height of the area.
     * @param startX The starting X coordinate of the area.
     * @param startY The starting Y coordinate of the area.
     * @return A Set of Point objects representing the unique coordinates.
     */
    private Set<Point> generateUniqueCoordinates(int count, int width, int height, int startX, int startY) {
        Set<Point> coordinates = new HashSet<>();
        while (coordinates.size() < count) {
            int x = random.nextInt(width - 2) + startX + 1;
            int y = random.nextInt(height - 2) + startY + 1;
            coordinates.add(new Point(x, y));
        }
        return coordinates;
    }

    /**
     * Displays the main game view. This includes drawing the room, local player, other players,
     * monsters, doors, items, and displaying game options and player stats. The method handles
     * IOExceptions internally.
     */
    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            drawRoom(tg);
            drawLocalPlayer(tg);
            drawPlayers(tg);
            drawMonster(tg);
            drawMonserInfo(tg);
            drawDoors(tg);
            drawItems(tg);
            drawItemInfo(tg);
            displayOptions(tg);
            displayPlayerStats(tg);
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws other players in the room on the terminal. Players are represented by symbols at unique
     * coordinates, excluding the local player.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawPlayers(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        int i = 0;
        // Über die GameState playerRoomMap gehen und schauen wenn ein player im selben raum ist wie der lokale spieler
        for (PlayerData player : gameState.getRoomData().getPlayers()) {
            if (player.getId() == gameState.getLocalPlayer().getId()) {
                continue;
            }
            tg.putString(uniqueCoordinates.stream().toList().get(i).x, uniqueCoordinates.stream().toList().get(i).y, "X");
            i++;
        }
    }

    /**
     * Displays information about the monster in the room, if present. This includes the monster's
     * name, health, danger level, attack, and defense stats.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @throws IOException If an I/O error occurs.
     */
    private void drawMonserInfo(TextGraphics tg) throws IOException {
        if (gameState.getRoomData().getEnemy() == null) {
            return;
        }
        int statsStartY = terminal.getTerminalSize().getRows() - 3; // Below the room
        tg.setForegroundColor(TextColor.ANSI.RED);
        // print NAME HP: hp/MaxHP, Danger-Level: dangerLevel
        tg.putString(1, statsStartY, "MONSTER INFO: " + gameState.getRoomData().getEnemy().getName() +
                "- " + gameState.getRoomData().getEnemy().getHp() +
                "/" + gameState.getRoomData().getEnemy().getMaxHp() +
                "HP - Danger-Level: " + gameState.getRoomData().getEnemy().getDangerLevel() +
                " - Attack: " + gameState.getRoomData().getEnemy().getAttack() +
                " - Defense: " + gameState.getRoomData().getEnemy().getDefense());

    }

    /**
     * Displays information about the item in the room, if present. This includes the item's name,
     * description, and attributes.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @throws IOException If an I/O error occurs.
     */
    private void drawItemInfo(TextGraphics tg) throws IOException {
        if (gameState.getRoomData().getItem() == null) {
            return;
        }
        int statsStartY = terminal.getTerminalSize().getRows() - 2; // Below the room
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        // print NAME HP: hp/MaxHP, Danger-Level: dangerLevel
        tg.putString(1, statsStartY, "ITEM INFO: " + gameState.getRoomData().getItem().getName() +
                " - " + gameState.getRoomData().getItem().getDescription() +
                " - Attribute: " + gameState.getRoomData().getItem().getAttributeName() +
                " " + gameState.getRoomData().getItem().getAttributes());

    }

    /**
     * Draws the room on the terminal using ASCII characters. The room is defined by its start
     * coordinates and dimensions.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawRoom(TextGraphics tg) {
        for (int y = ROOM_START_Y; y < ROOM_START_Y + ROOM_HEIGHT; y++) {
            for (int x = ROOM_START_X; x < ROOM_START_X + ROOM_WIDTH; x++) {
                String symbol = (x == ROOM_START_X || x == ROOM_START_X + ROOM_WIDTH - 1) ? "|" :
                        (y == ROOM_START_Y || y == ROOM_START_Y + ROOM_HEIGHT - 1) ? "-" : ".";
                tg.putString(x, y, symbol);
            }
        }
    }

    /**
     * Draws the local player in the center of the room on the terminal.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawLocalPlayer(TextGraphics tg) {
        int playerX = ROOM_START_X + ROOM_WIDTH / 2;
        int playerY = ROOM_START_Y + ROOM_HEIGHT / 2;
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(playerX, playerY, "X");
    }

    /**
     * Draws the monster in the room, if present, using specific symbols based on the monster type.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawMonster(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.RED);
        if (gameState.getRoomData().getEnemy() != null) {
            String monsterSymbol = switch (gameState.getRoomData().getEnemy().getName()) {
                case "Skeleton" -> "S";
                case "Zombie" -> "Z";
                case "Devil" -> "B";
                default -> "?";
            };
            tg.putString(uniqueCoordinates.stream().toList().get(3).x, uniqueCoordinates.stream().toList().get(3).y, monsterSymbol);
        }
    }

    /**
     * Draws doors in the room on the terminal, indicating directions to adjacent rooms.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawDoors(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        gameState.getRoomData().getAdjacentRooms().forEach((direction, adjacentRoomId) -> {

            if (adjacentRoomId != -1) {
                int doorX = switch (direction) {
                    case "North", "South" -> ROOM_START_X + DOOR_OFFSET;
                    case "East" -> ROOM_START_X + ROOM_WIDTH - 1;
                    case "West" -> ROOM_START_X;
                    default -> -1;
                };
                int doorY = switch (direction) {
                    case "North" -> ROOM_START_Y;
                    case "South" -> ROOM_START_Y + ROOM_HEIGHT - 1;
                    case "East", "West" -> ROOM_START_Y + ROOM_HEIGHT / 2;
                    default -> -1;
                };
                if (doorX != -1 && doorY != -1) {
                    tg.putString(doorX, doorY, "+");
                }
            }
        });
    }

    /**
     * Draws items in the room, if present, on the terminal.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void drawItems(TextGraphics tg) {
        if (gameState.getRoomData().getItem() != null) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(uniqueCoordinates.stream().toList().get(4).x, uniqueCoordinates.stream().toList().get(4).y, "I");
        }
    }

    /**
     * Displays available game options on the terminal based on the current game state. This includes
     * options for attacking, moving, fleeing, and picking up items. Updates the option mappings for
     * user input processing.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     */
    private void displayOptions(TextGraphics tg) {
        AtomicInteger optionsStartY = new AtomicInteger(OPTIONS_START_Y);
        AtomicInteger optionNumber = new AtomicInteger(1); // Start with option number 1

        // Display upper MGV bar
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        // Display the current room ID and adjacent room IDs
        String roomInfo = "CurrentRoomID: " + gameState.getRoomData().getId() +
                "| North: " + getAdjacentRoomId("North") +
                "| South: " + getAdjacentRoomId("South") +
                "| East: " + getAdjacentRoomId("East") +
                "| West: " + getAdjacentRoomId("West");
        tg.putString(ROOM_START_X, ROOM_START_Y - 1, roomInfo);


        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), "Options:");

        // Clear previous mappings
        optionMappings.clear();

        // Display 'Attack' option if a monster is present
        if (gameState.getRoomData().getEnemy() != null) {
            tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), optionNumber + ". Attack");
            optionMappings.put(optionNumber.getAndIncrement(), new AttackAction(gameState.getRoomData().getEnemy().getId()));
        }
        // Display 'Attack' playerID for each player in the room
        gameState.getRoomData().getPlayers().forEach(player -> {
            if (player.getId() != gameState.getLocalPlayer().getId()) {
                tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), optionNumber + ". Attack Player " + player.getId());
                optionMappings.put(optionNumber.getAndIncrement(), new AttackAction(player.getId()));
            }
        });


        // Display movement options based on available doors, Display "Flee" instead of move if there is another player in the room
        gameState.getRoomData().getAdjacentRooms().forEach((direction, adjacentRoomId) -> {
            if (adjacentRoomId != -1) {
                if (gameState.getRoomData().getPlayers().size() > 1 || gameState.getRoomData().getEnemy() != null) {
                    String moveOptionText = optionNumber + ". Flee " + direction; // 'N', 'S', 'E', 'W'
                    tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), moveOptionText);
                    optionMappings.put(optionNumber.getAndIncrement(), new FleeAction(adjacentRoomId));
                } else {
                    String moveOptionText = optionNumber + ". Move " + direction; // 'N', 'S', 'E', 'W'
                    tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), moveOptionText);
                    optionMappings.put(optionNumber.getAndIncrement(), new MoveAction(adjacentRoomId));
                }

            }
        });

        // Display 'Pick Up Item' option if items are present AND if the is no other player in the room
        if (gameState.getRoomData().getItem() != null && gameState.getRoomData().getPlayers().size() == 1 ) {
            tg.putString(OPTIONS_START_X, optionsStartY.get(), optionNumber + ". Pick Up Item");
            optionMappings.put(optionNumber.getAndIncrement(), new PickupAction(gameState.getRoomData().getItem().getId()));
        }
    }

    // Getters and Setters
    public Map<Integer, MainAction> getOptionMappings() {
        return optionMappings;
    }

    public int getAdjacentRoomId(String direction) {
        return Integer.parseInt(gameState.getRoomData().getAdjacentRooms().getOrDefault(direction, -1).toString());
    }

}
