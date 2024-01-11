package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class MainGameView extends ViewComponent {

    private final Map<Integer, String> optionMappings;

    private static final int ROOM_WIDTH = 35;
    private static final int ROOM_HEIGHT = 17;
    private static final int ROOM_START_X = 2;
    private static final int ROOM_START_Y = 2;
    private static final int OPTIONS_START_X = ROOM_START_X + ROOM_WIDTH + 5;
    private static final int OPTIONS_START_Y = 2;
    private static final int STATS_Y_OFFSET = -1;
    private static final int DOOR_OFFSET = ROOM_WIDTH / 2;

    private int monsterX;
    private int monsterY;
    private int itemX;
    private int itemY;

    public MainGameView(Terminal terminal, DungeonData dungeonData, PlayerData playerData) {
        super(terminal, dungeonData.getRooms().get(playerData.getCurrentRoomID() - 1), playerData, dungeonData);
        optionMappings = new HashMap<>();
        monsterX = random.nextInt(ROOM_WIDTH - 2) + ROOM_START_X + 1;
        monsterY = random.nextInt(ROOM_HEIGHT - 2) + ROOM_START_Y + 1;
        itemX = random.nextInt(ROOM_WIDTH - 2) + ROOM_START_X + 1;
        itemY= random.nextInt(ROOM_HEIGHT - 2) + ROOM_START_Y + 1;

        //System.out.println(roomData.toString());

    }

    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            drawRoom(tg);
            drawPlayer(tg);
            drawMonster(tg);
            drawDoors(tg);
            drawItems(tg);
            displayOptions(tg);
            displayPlayerStats(tg);
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawRoom(TextGraphics tg) {
        for (int y = ROOM_START_Y; y < ROOM_START_Y + ROOM_HEIGHT; y++) {
            for (int x = ROOM_START_X; x < ROOM_START_X + ROOM_WIDTH; x++) {
                String symbol = (x == ROOM_START_X || x == ROOM_START_X + ROOM_WIDTH - 1) ? "|" :
                        (y == ROOM_START_Y || y == ROOM_START_Y + ROOM_HEIGHT - 1) ? "-" : ".";
                tg.putString(x, y, symbol);
            }
        }
    }

    // TODO: Draw with max. 4 Players
    private void drawPlayer(TextGraphics tg) {
        int playerX = ROOM_START_X + ROOM_WIDTH / 2;
        int playerY = ROOM_START_Y + ROOM_HEIGHT / 2;
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(playerX, playerY, "X");
    }

    private void drawMonster(TextGraphics tg) {

        tg.setForegroundColor(TextColor.ANSI.RED);
        if (roomData.getMonster() != null) {
            String monsterSymbol = switch (roomData.getMonster()) {
                case "Skeleton" -> "S";
                case "Zombie" -> "Z";
                case "Boss" -> "B";
                default -> "?";
            };
            tg.putString(monsterX, monsterY, monsterSymbol);
        }
    }

    private void drawDoors(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        roomData.getAdjacentRooms().forEach((direction, adjacentRoomId) -> {

            if (adjacentRoomId != null) {
                int doorX = switch (direction) {
                    case "NORTH", "SOUTH" -> ROOM_START_X + DOOR_OFFSET;
                    case "EAST" -> ROOM_START_X + ROOM_WIDTH - 1;
                    case "WEST" -> ROOM_START_X;
                    default -> -1;
                };
                int doorY = switch (direction) {
                    case "NORTH" -> ROOM_START_Y;
                    case "SOUTH" -> ROOM_START_Y + ROOM_HEIGHT - 1;
                    case "EAST", "WEST" -> ROOM_START_Y + ROOM_HEIGHT / 2;
                    default -> -1;
                };
                if (doorX != -1 && doorY != -1) {
                    tg.putString(doorX, doorY, "+");
                }
            }
        });
    }

    private void drawItems(TextGraphics tg) {
        if (roomData.getItems() != null) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(itemX, itemY, "I");
        }
    }

    private void displayOptions(TextGraphics tg) {
        AtomicInteger optionsStartY = new AtomicInteger(OPTIONS_START_Y);
        AtomicInteger optionNumber = new AtomicInteger(1); // Start with option number 1

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        // Display the current room ID and adjacent room IDs
        String roomInfo = "CurrentRoomID: " + roomData.getId() +
                "| North: " + getAdjacentRoomId("NORTH") +
                "| South: " + getAdjacentRoomId("SOUTH") +
                "| East: " + getAdjacentRoomId("EAST") +
                "| West: " + getAdjacentRoomId("WEST");
        tg.putString(ROOM_START_X, ROOM_START_Y - 1, roomInfo);

        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), "Options:");

        // Clear previous mappings
        optionMappings.clear();

        // Display 'Attack' option if a monster is present
        if (roomData.getMonster() != null) {
            tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), optionNumber + ". Attack");
            optionMappings.put(optionNumber.getAndIncrement(), "Attack");
        }

        // Display movement options based on available doors
        roomData.getAdjacentRooms().forEach((direction, adjacentRoomId) -> {
            if (adjacentRoomId != null) {
                String moveOptionText = optionNumber + ". Move " + direction.charAt(0); // 'N', 'S', 'E', 'W'
                tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), moveOptionText);
                optionMappings.put(optionNumber.getAndIncrement(), "Move " + direction);
            }
        });

        // Display 'Do Nothing' option
        tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), optionNumber + ". Do Nothing");
        optionMappings.put(optionNumber.getAndIncrement(), "Do Nothing");

        // Display 'Pick Up Item' option if items are present
        if (roomData.getItems()!= null) {
            tg.putString(OPTIONS_START_X, optionsStartY.get(), optionNumber + ". Pick Up Item");
            optionMappings.put(optionNumber.getAndIncrement(), "Pick Up Item");
        }
    }
    public Map<Integer, String> getOptionMappings() {
        return optionMappings;
    }
    public void updateRoom(String direction) {
        Integer newRoomId = roomData.getAdjacentRooms().get(direction.toUpperCase());
        if (newRoomId != null) {
            playerData.setCurrentRoomID(newRoomId); // Assuming PlayerData has a method setCurrentRoomID
            roomData = dungeonData.getRooms().get(newRoomId - 1); // Update roomData to the new room
            itemX = random.nextInt(ROOM_WIDTH - 2) + ROOM_START_X + 1;
            itemY= random.nextInt(ROOM_HEIGHT - 2) + ROOM_START_Y + 1;
            monsterX = random.nextInt(ROOM_WIDTH - 2) + ROOM_START_X + 1;
            monsterY = random.nextInt(ROOM_HEIGHT - 2) + ROOM_START_Y + 1;
        }
    }

    private String getAdjacentRoomId(String direction) {
        return roomData.getAdjacentRooms().getOrDefault(direction, -1).toString();
    }


}
