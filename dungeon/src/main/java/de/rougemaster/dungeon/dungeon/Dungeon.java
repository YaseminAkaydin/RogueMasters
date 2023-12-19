package de.rougemaster.dungeon.dungeon;

import java.util.*;
import java.util.stream.Collectors;

public class Dungeon {

    private final int dungeonDifficulty;
    private List<Room> roomList;
    private String map;
    private Random random = new Random();
    private RoomFactory roomFactory;

    public Dungeon(int rooms, int dungeonDifficulty) {
        this.dungeonDifficulty = dungeonDifficulty;
        this.roomList = new ArrayList<>();
        this.roomFactory = new RoomFactory();

        generateAllRooms(rooms);
    }

    private void generateAllRooms(int depth) {
        // Create the BossRoom first using RoomFactory
        Room bossRoom = roomFactory.createRoom(RoomFactory.RoomType.BOSS);
        roomList.add(bossRoom);

        // Connect BossRoom to one other room
        Room nextRoom = roomFactory.createRoom(RoomFactory.RoomType.DUNGEON);
        connectRooms(bossRoom, nextRoom, getRandomDirection());
        roomList.add(nextRoom);

        // Current room starts from the nextRoom
        Room currentRoom = nextRoom;

        for (int i = 2; i < depth; i++) {
            Room newRoom = (i % 5 == 0) ? roomFactory.createRoom(RoomFactory.RoomType.TREASURE) : roomFactory.createRoom(RoomFactory.RoomType.DUNGEON);
            roomList.add(newRoom);

            String direction = getRandomAvailableDirection(currentRoom);
            connectRooms(currentRoom, newRoom, direction);

            currentRoom = (random.nextBoolean()) ? newRoom : getRandomExistingRoom();

            // Ensure no additional connections are made to the BossRoom
            if (currentRoom == bossRoom) {
                currentRoom = nextRoom; // Move back to nextRoom if we revisit BossRoom
            }
        }
    }

    public static void connectRooms(Room room1, Room room2, String direction) {
        String oppositeDirection = getOppositeDirection(direction);
        room1.addAdjacentRoom(direction, room2);
        room2.addAdjacentRoom(oppositeDirection, room1);
    }

    private String getRandomAvailableDirection(Room room) {
        List<String> availableDirections = room.getAdjacentRooms().entrySet().stream().filter(entry -> entry.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toList());

        if (availableDirections.isEmpty()) {
            return null;
        }
        int randomIndex = random.nextInt(availableDirections.size());
        return availableDirections.get(randomIndex);
    }

    private static String getOppositeDirection(String direction) {
        return switch (direction) {
            case "NORTH" -> "SOUTH";
            case "SOUTH" -> "NORTH";
            case "EAST" -> "WEST";
            case "WEST" -> "EAST";
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

    private String getRandomDirection() {
        List<String> directions = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");
        int randomIndex = random.nextInt(directions.size());
        return directions.get(randomIndex);
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

    public int getDungeonDifficulty() {
        return dungeonDifficulty;
    }
}
