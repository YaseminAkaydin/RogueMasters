package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;
import java.io.IOException;
import java.util.*;


public class DungeonMapView extends ViewComponent {

    private final Set<Corridor> corridors = new HashSet<>();
    private int playerRoomID;

    private final int horizontalSpacing = 12;
    private final int verticalSpacing = 7;

    public DungeonMapView(Terminal terminal, PlayerData playerData, DungeonData dungeonData) {
        super(terminal, playerData, dungeonData);
        this.playerRoomID = dungeonData.getCurrentRoomId(playerData.getId());
    }

    @Override
    public void display() {
        this.playerRoomID = dungeonData.getCurrentRoomId(playerData.getId());
        try {
            clearAndInitializeGraphics();
            Map<Point, RoomData> roomPositions = calculateRoomPositions();
            drawMap(roomPositions);
            displayPlayerStats(tg); // Display player stats
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException("Display Error DMV: " + e);
        }
    }

    private void drawMap(Map<Point, RoomData> roomPositions) {
        int additionalOffset = 5;
        // Find the extents of the map
        int minX = roomPositions.keySet().stream().mapToInt(p -> p.x).min().orElse(0);
        int minY = roomPositions.keySet().stream().mapToInt(p -> p.y).min().orElse(0);

        // Adjust starting position based on the extents
        int offsetX = minX < 0 ? -minX + additionalOffset : additionalOffset;
        int offsetY = minY < 0 ? -minY + additionalOffset : additionalOffset;

        // Now draw the rooms and corridors
        for (Map.Entry<Point, RoomData> entry : roomPositions.entrySet()) {
            Point p = entry.getKey();
            RoomData room = entry.getValue();
            drawRoom(tg, room, p.x + offsetX, p.y + offsetY);
            // Add code to draw corridors here
        }
        // Draw corridors
        drawCorridors(tg, offsetX, offsetY);
    }

    private Map<Point, RoomData> calculateRoomPositions() {
        Map<Point, RoomData> roomPositions = new HashMap<>();
        Set<Integer> visitedRooms = new HashSet<>();
        calculatePositionsRecursive(rooms.get(0), 0, 0, visitedRooms, roomPositions, horizontalSpacing, verticalSpacing);
        return roomPositions;
    }

    private void calculatePositionsRecursive(RoomData room, int x, int y, Set<Integer> visitedRooms, Map<Point, RoomData> roomPositions, int horizontalSpacing, int verticalSpacing) {
        if (visitedRooms.contains(room.getId())) return;
        visitedRooms.add(room.getId());
        roomPositions.put(new Point(x, y), room);

        for (Map.Entry<String, Integer> entry : room.getAdjacentRooms().entrySet()) {
            String direction = entry.getKey();
            Integer adjacentRoomId = entry.getValue();
            RoomData childRoom = findRoomById(adjacentRoomId);

            if (childRoom != null && !visitedRooms.contains(childRoom.getId())) {
                Point newRoomPoint;
                int startX, startY, endX, endY;
                switch (direction) {
                    case "NORTH":
                        newRoomPoint = new Point(x, y - verticalSpacing);
                        startX = x;
                        startY = y - 2; // Starting from the northern wall of the room
                        endX = newRoomPoint.x;
                        endY = newRoomPoint.y + 2; // Ending at the southern wall of the adjacent room
                        break;
                    case "SOUTH":
                        newRoomPoint = new Point(x, y + verticalSpacing);
                        startX = x;
                        startY = y + 2; // Starting from the southern wall
                        endX = newRoomPoint.x;
                        endY = newRoomPoint.y - 2; // Ending at the northern wall
                        break;
                    case "EAST":
                        newRoomPoint = new Point(x + horizontalSpacing, y);
                        startX = x + 2;
                        startY = y; // Starting from the eastern wall
                        endX = newRoomPoint.x - 2;
                        endY = newRoomPoint.y; // Ending at the western wall
                        break;
                    case "WEST":
                        newRoomPoint = new Point(x - horizontalSpacing, y);
                        startX = x - 2;
                        startY = y; // Starting from the western wall
                        endX = newRoomPoint.x + 2;
                        endY = newRoomPoint.y; // Ending at the eastern wall
                        break;
                    default:
                        continue; // Skip if direction is unknown
                }
                corridors.add(new Corridor(new Point(startX, startY), new Point(endX, endY)));
                calculatePositionsRecursive(childRoom, newRoomPoint.x, newRoomPoint.y, visitedRooms, roomPositions, horizontalSpacing, verticalSpacing);
            }
        }
    }

    private void drawRoom(TextGraphics tg, RoomData room, int x, int y) {
        if (room.getId() == dungeonData.getCurrentRoomId(playerData.getId())) tg.setForegroundColor(TextColor.ANSI.CYAN);
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

    private void drawCorridors(TextGraphics tg, int offsetX, int offsetY) {
        for (Corridor corridor : corridors) {
            Point start = corridor.start;
            Point end = corridor.end;
            tg.drawLine(start.x + offsetX, start.y + offsetY, end.x + offsetX, end.y + offsetY, '+');
        }
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

    private RoomData findRoomById(int id) {
        for (RoomData room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
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

    private static class Corridor {
        Point start;
        Point end;

        Corridor(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

    }
}
