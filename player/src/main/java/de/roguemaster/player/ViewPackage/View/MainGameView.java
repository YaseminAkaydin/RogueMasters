package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.GameState;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;


import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class MainGameView extends ViewComponent{

    private final Map<Integer, String> optionMappings;

    private static final int ROOM_WIDTH = 35;
    private static final int ROOM_HEIGHT = 17;
    private static final int ROOM_START_X = 2;
    private static final int ROOM_START_Y = 2;
    private static final int OPTIONS_START_X = ROOM_START_X + ROOM_WIDTH + 5;
    private static final int OPTIONS_START_Y = 2;
    private static final int STATS_Y_OFFSET = -1;
    private static final int DOOR_OFFSET = ROOM_WIDTH / 2;



    private Set<Point> uniqueCoordinates;

    public MainGameView(Terminal terminal, GameState gameState) {
        super(terminal, gameState);
        optionMappings = new HashMap<>();
        uniqueCoordinates = generateUniqueCoordinates(5, ROOM_WIDTH, ROOM_HEIGHT, ROOM_START_X, ROOM_START_Y);

    }
    private Set<Point> generateUniqueCoordinates(int count, int width, int height, int startX, int startY) {
        Set<Point> coordinates = new HashSet<>();
        while (coordinates.size() < count) {
            int x = random.nextInt(width - 2) + startX + 1;
            int y = random.nextInt(height - 2) + startY + 1;
            coordinates.add(new Point(x, y));
        }
        return coordinates;
    }

    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            drawRoom(tg);
            drawLocalPlayer(tg);
            /*System.out.println(gameState.getCurrentRoomId());*/
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

    private void drawPlayers(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        int i = 0;
        // Über die GameState playerRoomMap gehen und schauen wenn ein player im selben raum ist wie der lokale spieler
        for (PlayerData player : gameState.getRoomData().getPlayers()) {
            if(player.getId() == gameState.getLocalPlayer().getId()){
                continue;
            }
            tg.putString(uniqueCoordinates.stream().toList().get(i).x, uniqueCoordinates.stream().toList().get(i).y, "X");
            i++;
        }
    }

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
                "HP - Danger-Level: " + gameState.getRoomData().getEnemy().getDangerLevel());

    }

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
                " "+gameState.getRoomData().getItem().getAttributes());

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

    private void drawLocalPlayer(TextGraphics tg) {
        int playerX = ROOM_START_X + ROOM_WIDTH / 2;
        int playerY = ROOM_START_Y + ROOM_HEIGHT / 2;
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(playerX, playerY, "X");
    }

    private void drawMonster(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.RED);
        if (gameState.getRoomData().getEnemy() != null) {
            String monsterSymbol = switch (gameState.getRoomData().getEnemy().getName()) {
                case "Skeleton" -> "S";
                case "Zombie" -> "Z";
                case "Boss" -> "B";
                default -> "?";
            };
            tg.putString(uniqueCoordinates.stream().toList().get(3).x, uniqueCoordinates.stream().toList().get(3).y, monsterSymbol);
        }
    }

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

    private void drawItems(TextGraphics tg) {
        if (gameState.getRoomData().getItem() != null) {
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(uniqueCoordinates.stream().toList().get(4).x, uniqueCoordinates.stream().toList().get(4).y, "I");
        }
    }

    private void displayOptions(TextGraphics tg) {
        AtomicInteger optionsStartY = new AtomicInteger(OPTIONS_START_Y);
        AtomicInteger optionNumber = new AtomicInteger(1); // Start with option number 1

        tg.setForegroundColor(TextColor.ANSI.CYAN);
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
            optionMappings.put(optionNumber.getAndIncrement(), "Attack");
        }

        // Display movement options based on available doors
        gameState.getRoomData().getAdjacentRooms().forEach((direction, adjacentRoomId) -> {
            if (adjacentRoomId != -1) {
                String moveOptionText = optionNumber + ". Move " + direction.charAt(0); // 'N', 'S', 'E', 'W'
                tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), moveOptionText);
                optionMappings.put(optionNumber.getAndIncrement(), "Move " + direction);
            }
        });

        // Display 'Do Nothing' option
        tg.putString(OPTIONS_START_X, optionsStartY.getAndIncrement(), optionNumber + ". Do Nothing");
        optionMappings.put(optionNumber.getAndIncrement(), "Do Nothing");

        // Display 'Pick Up Item' option if items are present
        if (gameState.getRoomData().getItem() != null) {
            tg.putString(OPTIONS_START_X, optionsStartY.get(), optionNumber + ". Pick Up Item");
            optionMappings.put(optionNumber.getAndIncrement(), "Pick Up Item");
        }
    }

    public Map<Integer, String> getOptionMappings() {
        return optionMappings;
    }

    public void updateRoom(String direction) {

        Integer newRoomId = gameState.getRoomData().getAdjacentRooms().get(direction.toUpperCase());
        System.out.println("Server sollte jetzt neuen gamestate senden um den raum zu updaten");
        /*if (newRoomId != null) {
            RoomData roomData1 = gameState.getRoomList().get(newRoomId - 1);
            dungeonData.updatePlayerRoom(playerData.getId(), roomData1);
            roomData = dungeonData.getRoomList().get(newRoomId - 1); // Update roomData to the new room
        }*/
    }

    public int getAdjacentRoomId(String direction) {
        return Integer.parseInt(gameState.getRoomData().getAdjacentRooms().getOrDefault(direction, -1).toString());
    }

    public int getCurrentRoomId() {
        return gameState.getCurrentRoomId();
    }

}
