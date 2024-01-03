package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.DungeonData;
import de.roguemaster.player.CLIneu.DataForView.ItemData;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;
import de.roguemaster.player.CLIneu.ViewBuilder;

import java.io.IOException;
import java.util.*;


public class DungeonMapView extends ViewComponent {


    private Set<Corridor> corridors = new HashSet<>();
    private int playerRoomID;

    public DungeonMapView(Terminal terminal, PlayerData playerData, DungeonData dungeonData) {
        super(terminal, playerData, dungeonData);
        this.playerRoomID = this.playerData.getCurrentRoomID();
    }

    @Override
    public void display() {
        this.playerRoomID = playerData.getCurrentRoomID();
        try {
            terminal.clearScreen();
            TextGraphics tg = terminal.newTextGraphics();
            int additionalOffset = 5; // Adjust this value as needed

            // Calculate room positions
            Map<Point, RoomData> roomPositions = new HashMap<>();
            Set<Integer> visitedRooms = new HashSet<>();
            int horizontalSpacing = 12;
            int verticalSpacing = 7;
            calculatePositionsRecursive(rooms.get(0), 0, 0, visitedRooms, roomPositions, horizontalSpacing, verticalSpacing);

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

            displayPlayerStats(tg); // Display player stats
            terminal.flush();
        } catch (IOException e) {
            throw new RuntimeException("Display Error DMV: " + e);
        }
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

    /*public static void main(String[] args) {

        try {
            // Initialize terminal
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            // Initialize player data and dungeon data
            PlayerData playerData = new PlayerData(
                    1,
                    10,
                    0,
                    List.of(new ItemData(0, "Sword", "A Sword only the mighty can wield", 20),
                            new ItemData(1, "Shield", "A Shield for weak individuals", 5),
                            new ItemData(2, "Potion", "Potion, heal yourself ffs", 10),
                            new ItemData(3, "Book", "Book, with a lot of pictures", 10)),
                    2); // Assuming a constructor exists
            DungeonData dungeonData;
            List<RoomData> rooms = new ArrayList<>();

            // Example items for the rooms
            List<ItemData> commonItems = List.of(
                    new ItemData(0, "Sword", "A Sword only the mighty can wield", 20),
                    new ItemData(1, "Shield", "A Shield for weak individuals", 5)*//*,
                new ItemData(2, "Potion", "Potion, heal yourself ffs", 10),
                new ItemData(3, "Book", "Book, with a lot of pictures", 10)*//*
            );

            // Create rooms with types, monsters, items, IDs, and adjacent rooms
            RoomData room1 = new RoomData("BossRoom", "Skeleton", Collections.emptyList(), 1,
                    Map.of("EAST", 2));
            RoomData room2 = new RoomData("DungeonRoom", "Skeleton", commonItems, 2,
                    Map.of("NORTH", 5, "WEST", 1, "SOUTH", 3, "EAST", 4));
            RoomData room3 = new RoomData("DungeonRoom", "Zombie", commonItems, 3,
                    Map.of("NORTH", 2));
            RoomData room4 = new RoomData("DungeonRoom", "Zombie", commonItems, 4,
                    Map.of("WEST", 2));
            RoomData room5 = new RoomData("DungeonRoom", "Zombie", commonItems, 5,
                    Map.of("WEST", 6, "SOUTH", 2, "EAST", 8));
            RoomData room6 = new RoomData("DungeonRoom", "Zombie", commonItems, 6,
                    Map.of("NORTH", 7, "EAST", 5));
            RoomData room7 = new RoomData("DungeonRoom", "Zombie", commonItems, 7,
                    Map.of("SOUTH", 6));
            RoomData room8 = new RoomData("TreasureRoom", "Zombie", commonItems, 8,
                    Map.of("NORTH", 9, "WEST", 5));
            RoomData room9 = new RoomData("DungeonRoom", "Zombie", commonItems, 9,
                    Map.of("SOUTH", 8, "EAST", 10));
            RoomData room10 = new RoomData("DungeonRoom", "Zombie", commonItems, 10,
                    Map.of("WEST", 9));

            // Add rooms to the list
            rooms.add(room1);
            rooms.add(room2);
            rooms.add(room3);
            rooms.add(room4);
            rooms.add(room5);
            rooms.add(room6);
            rooms.add(room7);
            rooms.add(room8);
            rooms.add(room9);
            rooms.add(room10);

            dungeonData = new DungeonData(rooms);


            // Initialize DungeonMapView
            DungeonMapView dungeonMapView = new DungeonMapView(terminal, playerData, dungeonData);

            // Display the dungeon map
            boolean run = true;
            while (run) {
                dungeonMapView.display();
                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/


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
