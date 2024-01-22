package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.GameState;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;
import java.io.IOException;
import java.util.*;

/**
 * Displays the dungeon map view. The map is drawn based on the current dungeon layout and the player's
 */
public class DungeonMapView extends ViewComponent {

    /**
     * Represents a point in a 2D space with x and y coordinates.
     */
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

    /**
     * Represents a corridor in the dungeon, defined by its start and end points.
     */
    private static class Corridor {
        Point start;
        Point end;

        Corridor(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

    }

    private final Set<Corridor> corridors = new HashSet<>();

    /**
     * Constructs a new DungeonMapView with the specified Terminal and GameState.
     *
     * @param terminal The Terminal object used for displaying this view.
     * @param gameState The current state of the game, containing all necessary information about the dungeon.
     */
    public DungeonMapView(Terminal terminal, GameState gameState) {
        super(terminal, gameState);
    }

    /**
     * Displays the dungeon map view. Clears the screen, calculates room positions, draws the map and
     * player stats. The method handles IOExceptions and throws a RuntimeException in case of display errors.
     */
    @Override
    public void display() {
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

    /**
     * Draws the dungeon map on the terminal. It adjusts the starting position based on the map extents
     * and draws each room and corridors connecting them.
     *
     * @param roomPositions A map of room positions and corresponding room data.
     */
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

    /**
     * Calculates the positions of all rooms in the dungeon. It uses a recursive method to position each
     * room based on its relative position to adjacent rooms.
     *
     * @return A map of Point objects representing room positions and corresponding RoomData.
     */
    private Map<Point, RoomData> calculateRoomPositions() {
        Map<Point, RoomData> roomPositions = new HashMap<>();
        Set<Integer> visitedRooms = new HashSet<>();
        int horizontalSpacing = 12;
        int verticalSpacing = 7;
        calculatePositionsRecursive(gameState.getRoomList().get(0), 0, 0, visitedRooms, roomPositions, horizontalSpacing, verticalSpacing);
        return roomPositions;
    }

    /**
     * Recursively calculates the positions of rooms in the dungeon. It traverses the dungeon layout
     * starting from a given room and marks rooms as visited to avoid duplication.
     *
     * @param room The current room data.
     * @param x The x coordinate for the current room.
     * @param y The y coordinate for the current room.
     * @param visitedRooms A set of visited room IDs.
     * @param roomPositions A map to store calculated room positions.
     * @param horizontalSpacing The spacing between rooms horizontally.
     * @param verticalSpacing The spacing between rooms vertically.
     */
    private void calculatePositionsRecursive(RoomData room, int x, int y, Set<Integer> visitedRooms,
                                             Map<Point, RoomData> roomPositions, int horizontalSpacing, int verticalSpacing) {
        // Wenn der aktuelle Raum bereits besucht wurde, beenden wir die Rekursion für diesen Pfad.
        if (visitedRooms.contains(room.getId())) return;

        // Markieren Sie den aktuellen Raum als besucht.
        visitedRooms.add(room.getId());

        // Fügen Sie den aktuellen Raum und seine Position zur Map hinzu.
        roomPositions.put(new Point(x, y), room);

        // Gehen Sie die benachbarten Räume durch, die im aktuellen Raum gespeichert sind.
        for (Map.Entry<String, Integer> entry : room.getAdjacentRooms().entrySet()) {
            // Richtung und ID des benachbarten Raums.
            String direction = entry.getKey();
            Integer adjacentRoomId = entry.getValue();

            // Finden Sie den Raumdatensatz für die benachbarte Raum-ID.
            RoomData childRoom = findRoomById(adjacentRoomId);

            // Wenn ein benachbarter Raum existiert und noch nicht besucht wurde...
            if (childRoom != null && !visitedRooms.contains(childRoom.getId())) {
                Point newRoomPoint;
                int startX, startY, endX, endY;
                switch (direction) {
                    case "North":
                        // Setze den neuen Punkt oberhalb des aktuellen Raums.
                        newRoomPoint = new Point(x, y - verticalSpacing);
                        // Koordinaten für den Beginn und das Ende des Korridors einstellen.
                        startX = x;
                        startY = y - 2; // Beginnend von der nördlichen Wand des Raumes
                        endX = newRoomPoint.x;
                        endY = newRoomPoint.y + 2; // Endend an der südlichen Wand des angrenzenden Raums
                        break;
                    case "South":
                        newRoomPoint = new Point(x, y + verticalSpacing);
                        startX = x;
                        startY = y + 2; // Starting from the southern wall
                        endX = newRoomPoint.x;
                        endY = newRoomPoint.y - 2; // Ending at the northern wall
                        break;
                    case "East":
                        newRoomPoint = new Point(x + horizontalSpacing, y);
                        startX = x + 2;
                        startY = y; // Starting from the eastern wall
                        endX = newRoomPoint.x - 2;
                        endY = newRoomPoint.y; // Ending at the western wall
                        break;
                    case "West":
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

    /**
     * Draws a single room on the terminal at the specified coordinates. The appearance of the room
     * changes based on its type and whether it's the current room.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @param room The room data to be drawn.
     * @param x The x coordinate where the room is to be drawn.
     * @param y The y coordinate where the room is to be drawn.
     */
    private void drawRoom(TextGraphics tg, RoomData room, int x, int y) {
        if (room.getId() == gameState.getLocalPlayer().getCurrentRoomId()) tg.setForegroundColor(TextColor.ANSI.CYAN);
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

    /**
     * Draws corridors on the terminal, connecting rooms based on their positions.
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @param offsetX The offset on the x-axis for drawing.
     * @param offsetY The offset on the y-axis for drawing.
     */
    private void drawCorridors(TextGraphics tg, int offsetX, int offsetY) {
        for (Corridor corridor : corridors) {
            Point start = corridor.start;
            Point end = corridor.end;
            tg.drawLine(start.x + offsetX, start.y + offsetY, end.x + offsetX, end.y + offsetY, '+');
        }
    }

    /**
     * Sets the color for drawing a room based on its type (e.g., BossRoom, TreasureRoom).
     *
     * @param tg TextGraphics object used for rendering text on the terminal.
     * @param roomType The type of the room.
     */
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

    /**
     * Finds a room in the game state by its ID.
     *
     * @param id The ID of the room to find.
     * @return The RoomData for the specified ID, or null if not found.
     */
    private RoomData findRoomById(int id) {
        for (RoomData room : gameState.getRoomList()) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }




}
