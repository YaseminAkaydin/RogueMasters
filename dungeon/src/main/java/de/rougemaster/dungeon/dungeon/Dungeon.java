package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Dungeon {
    /*
     * TODO: ROOMS should generate on a grid that dont overlap
     *  EXAMPLE:
     *  Room 1: 0,0 -> East leads to Room 2
     *  Room 2: 1,0 -> South leads to Room 3
     *  Room 3: 1,1 -> West leads to Room 4
     *  Room 4: 0,1 -> North leads to Room 5
     *  Room 5: 0,0
     *  Shouldn't be possible
     */
    private final int dungeonDifficulty;
    private List<Room> roomList;
    Map<Point,Room> grid;
    private String map;
    private Random random = new Random();
    private RoomFactory roomFactory;

    public Dungeon(int rooms, int dungeonDifficulty) {
        this.dungeonDifficulty = dungeonDifficulty;
        this.roomList = new ArrayList<>();
        this.roomFactory = new RoomFactory();
        this.grid = new HashMap<>();

        generateAllRooms(rooms);
    }

    private void generateAllRooms(int depth) {
        // Create the BossRoom first using RoomFactory
        Room bossRoom = roomFactory.createRoom(RoomFactory.RoomType.BOSS);
        roomList.add(bossRoom);
        grid.put(new Point(0, 0), bossRoom);



        // Current room starts from the BossRoom
        Room currentRoom = bossRoom;
        Point currentPoint = new Point(0, 0);

        for (int i = 1; i < depth; i++) {
            RoomCardinalDirection direction = getRandomDirection();
            Point newPoint = getNextPoint(currentPoint, direction);

            if (grid.containsKey(newPoint)) {
                i--;
                continue;
            }

            Room newRoom = (i % 5 == 0) ? roomFactory.createRoom(RoomFactory.RoomType.TREASURE) : roomFactory.createRoom(RoomFactory.RoomType.DUNGEON);
            roomList.add(newRoom);
            grid.put(newPoint, newRoom);

            connectRooms(currentRoom, newRoom, direction);

            currentRoom = newRoom;
            currentPoint = newPoint;
        }
    }

    private Point getNextPoint(Point currentPoint, RoomCardinalDirection direction) {
        return switch (direction) {
            case North -> new Point(currentPoint.x, currentPoint.y - 1);
            case South -> new Point(currentPoint.x, currentPoint.y + 1);
            case East -> new Point(currentPoint.x + 1, currentPoint.y);
            case West -> new Point(currentPoint.x - 1, currentPoint.y);
        };
    }

    public static void main(String[] args) {

        // Create a Dungeon with a specified number of rooms and difficulty
        int numberOfRooms = 10; // Example number of rooms
        int dungeonDifficulty = 5; // Example difficulty
        Dungeon dungeon = new Dungeon(numberOfRooms, dungeonDifficulty);

        // Print the dungeon layout
        dungeon.printDungeonLayout();

    }
    public static void connectRooms(Room room1, Room room2, RoomCardinalDirection direction) {
        RoomCardinalDirection oppositeDirection = getOppositeDirection(direction);
        room1.addAdjacentRoom(direction, room2);
        room2.addAdjacentRoom(oppositeDirection, room1);
    }

    private RoomCardinalDirection getRandomAvailableDirection(Room room) {
        List<RoomCardinalDirection> availableDirections = room.getAdjacentRooms().entrySet().stream().filter(entry -> entry.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toList());

        if (availableDirections.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(availableDirections.size());
        return availableDirections.get(randomIndex);
    }

    private static RoomCardinalDirection getOppositeDirection(RoomCardinalDirection direction) {
        if (direction == null){
            System.out.println("WTF"); //TODO: fixen, direction darf nicht null geben das führt zum error beim dungeon createn
        }

        return switch (direction) {
            case North -> RoomCardinalDirection.South;
            case South -> RoomCardinalDirection.North;
            case East -> RoomCardinalDirection.West;
            case West -> RoomCardinalDirection.East;
            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }

    private Room getRandomExistingRoom() {
        if (roomList.isEmpty()) {
            throw new IllegalStateException("Room list is empty.");
        }
        int randomIndex = random.nextInt(roomList.size());
        return roomList.get(randomIndex);
    }

    private RoomCardinalDirection getRandomDirection() {
        int randomIndex = random.nextInt(RoomCardinalDirection.values().length);
        return RoomCardinalDirection.values()[randomIndex];
    }

    public void printDungeonLayout() {
        System.out.println("Dungeon Layout:");
        for (Room room : roomList) {
            String roomType = room instanceof BossRoom ? "BossRoom" : "Room";
            System.out.println(roomType + " ID: " + room.getId());

            room.getAdjacentRooms().forEach((direction, adjacentRoom) -> {
                if (adjacentRoom != null) {
                    System.out.println("  - " + direction + " leads to Room ID: " + adjacentRoom.getId());
                } else {
                    System.out.println("  - " + direction + " leads to nowhere (no connection)");
                }
            });
        }
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public int getDungeonDifficulty() {
        return dungeonDifficulty;
    }
}
