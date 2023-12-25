package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.DungeonData;
import de.roguemaster.player.CLIneu.DataForView.ItemData;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;

import java.io.IOException;
import java.util.*;

//TODO: Räume anders darstellen
public class DungeonMapView implements ViewComponent {
    private Terminal terminal;
    private PlayerData playerData;
    private List<RoomData> rooms;
    private int playerRoomID;


    public DungeonMapView(Terminal terminal, PlayerData playerData, DungeonData dungeonData) {
        this.terminal = terminal;
        this.playerData = playerData;
        this.rooms = dungeonData.getRooms();
        this.playerRoomID = playerData.getCurrentRoomID();

    }

    @Override
    public void display() {
        this.playerRoomID = playerData.getCurrentRoomID();
        try {
            terminal.clearScreen();
            TextGraphics tg = terminal.newTextGraphics();
            TerminalSize size = terminal.getTerminalSize();

            // Set initial positions for the first room
            int startX = size.getColumns() / 2;
            int startY = 3; // Start a bit from the top
            int layerHeight = 7; // Vertical spacing between layers of rooms

            // Draw the first room
            drawRoom(tg, rooms.get(0), startX, startY);

            // Draw connected rooms
            drawConnectedRooms(tg, rooms.get(0), startX, startY, layerHeight, new HashSet<>());

            displayPlayerStats(tg); // Display player stats
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayPlayerStats(TextGraphics tg) throws IOException {
        int statsStartY = terminal.getTerminalSize().getRows() - 1; // Below the room
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, statsStartY, "LEVEL: " + playerData.getLevel() +
                " HP: " + playerData.getHp() +
                "/10 EXP: " + playerData.getExp() +
                "  -- 'M' = Map -- 'I' = Inventory -- 'S' = RoomView");
    }

    private void drawRoom(TextGraphics tg, RoomData room, int x, int y) {
        if (room.getId() == playerRoomID) tg.setForegroundColor(TextColor.ANSI.CYAN);
        else setColorBasedOnRoomType(tg, room.getRoomType());


        // Draw the room as a 5x5 box
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (i == x - 2 || i == x + 2 || j == y - 2 || j == y + 2) {
                    tg.setCharacter(i, j, (i == x - 2 || i == x + 2) ? '|' : '-');
                }
            }
        }

        // Print room ID in the center of the room
        tg.putString(x, y, Integer.toString(room.getId()));

        // Reset color to default
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    private void setColorBasedOnRoomType(TextGraphics tg, String roomType) {

        // Set color based on room type
        switch (roomType) {
            case "BossRoom":
                tg.setForegroundColor(TextColor.ANSI.RED);
                break;
            case "TreasureRoom":
                tg.setForegroundColor(TextColor.ANSI.YELLOW);
                break;
            default:
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                break;
        }
    }

    private void drawConnectedRooms(TextGraphics tg, RoomData room, int x, int y, int layerHeight, Set<Integer> visitedRooms) {
        if (visitedRooms.contains(room.getId())) return;
        visitedRooms.add(room.getId());

        int numConnections = room.getAdjacentRooms().size();
        int horizontalSpacing = 12; // Horizontal spacing between rooms

        // Starting position for the first connected room
        int childX = x - (numConnections - 1) * horizontalSpacing / 2;
        int childY = y + layerHeight;

        for (Integer adjacentRoomId : room.getAdjacentRooms().values()) {
            RoomData childRoom = findRoomById(adjacentRoomId);
            if (childRoom != null && !visitedRooms.contains(childRoom.getId())) {
                // Draw corridor from parent room to child room
                tg.drawLine(x, y + 2, childX, childY - 2, '+');

                // Draw the child room
                drawRoom(tg, childRoom, childX, childY);

                // Recursively draw the next layer of connected rooms
                drawConnectedRooms(tg, childRoom, childX, childY, layerHeight, visitedRooms);

                // Move to the position for the next connected room
                childX += horizontalSpacing;
            }
        }
    }

    private RoomData findRoomById(int id) {
        for (RoomData room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    public void setPlayerRoomID(int playerRoomID) {
        this.playerRoomID = playerRoomID;
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
