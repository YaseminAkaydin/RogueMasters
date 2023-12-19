package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;

import java.io.IOException;
import java.util.Random;

public class MainGameView implements ViewComponent {
    // Attributes for room, options, player stats
    private Terminal terminal;
    private RoomData roomData;
    private Random random = new Random();
    private PlayerData playerData;

    public MainGameView(Terminal terminal, RoomData roomData, PlayerData playerData) {
        this.terminal = terminal;
        this.roomData = roomData;
        this.playerData = playerData;
    }

    public void updateRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            TextGraphics tg = terminal.newTextGraphics();

            // Room dimensions and position
            int roomWidth = 35;
            int roomHeight = 17;
            int roomStartX = 2; // Adjust as needed
            int roomStartY = 2; // Adjust as needed

            // Draw the room
            for (int y = roomStartY; y < roomStartY + roomHeight; y++) {
                for (int x = roomStartX; x < roomStartX + roomWidth; x++) {
                    if (x == roomStartX || x == roomStartX + roomWidth - 1) {
                        tg.putString(x, y, "|");
                    } else if (y == roomStartY || y == roomStartY + roomHeight - 1) {
                        tg.putString(x, y, "-");
                    } else {
                        tg.putString(x, y, ".");
                    }
                }
            }

            // Player in the middle (green)
            int playerX = roomStartX + roomWidth / 2;
            int playerY = roomStartY + roomHeight / 2;
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(playerX, playerY, "X");

            // Example Monster (red)
            int monsterX = random.nextInt(roomWidth - 2) + roomStartX + 1;
            int monsterY = random.nextInt(roomHeight - 2) + roomStartY + 1;
            tg.setForegroundColor(TextColor.ANSI.RED);
            if (roomData.getMonster().equals("skeleton"))
                tg.putString(monsterX, monsterY, "S");
            else if (roomData.getMonster().equals("zombie"))
                tg.putString(monsterX, monsterY, "Z"); // Replace 'Z' with actual monster type
            else if (roomData.getMonster().equals("boss"))
                tg.putString(monsterX, monsterY, "B"); // Replace 'B' with actual monster type

            // Example Door (middle of top wall)
            tg.setForegroundColor(TextColor.ANSI.CYAN);
            if (roomData.getDoors().contains("north")) {
                int doorX = roomStartX + roomWidth / 2;
                int doorY = roomStartY;
                tg.putString(doorX, doorY, "+");
            }
            if (roomData.getDoors().contains("south")) {
                int doorX = roomStartX + roomWidth / 2;
                int doorY = roomStartY + roomHeight - 1;
                tg.putString(doorX, doorY, "+");
            }
            if (roomData.getDoors().contains("east")) {
                int doorX = roomStartX + roomWidth - 1;
                int doorY = roomStartY + roomHeight / 2;
                tg.putString(doorX, doorY, "+");
            }
            if (roomData.getDoors().contains("west")) {
                int doorX = roomStartX;
                int doorY = roomStartY + roomHeight / 2;
                tg.putString(doorX, doorY, "+");
            }

            // Example Item (yellow)
            int itemX = random.nextInt(roomWidth - 2) + roomStartX + 1;
            int itemY = random.nextInt(roomHeight - 2) + roomStartY + 1;
            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(itemX, itemY, "I");

            // Player Options
            int optionsStartY = 2;
            int optionsStartX = roomStartX + roomWidth + 5; // Adjust as needed
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(optionsStartX, optionsStartY++, "Options:");
            if (roomData.getMonster() != null) {
                tg.putString(optionsStartX, optionsStartY++, "1. Attack");
            }
            tg.putString(optionsStartX, optionsStartY++, "2. Move");
            tg.putString(optionsStartX, optionsStartY++, "3. Do Nothing");
            if (roomData.getItem() != null) {
                tg.putString(optionsStartX, optionsStartY, "4. Pick Up Item");
            }
            // Player Stats
            displayPlayerStats(tg, playerData, roomStartX, roomStartY, roomHeight);


            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayPlayerStats(TextGraphics tg, PlayerData playerData, int roomStartX, int roomStartY, int roomHeight) {
        int statsStartY = roomStartY + roomHeight + 2; // Below the room
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(roomStartX, statsStartY, "LEVEL: " + playerData.getLevel() +
                " HP: " + playerData.getHp() +
                "/10 EXP: " + playerData.getExp() +
                "  -------  Press 'M' for Map or 'I' for Inventory");
    }
}
