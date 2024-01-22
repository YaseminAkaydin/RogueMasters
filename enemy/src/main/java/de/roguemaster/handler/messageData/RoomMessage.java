package de.roguemaster.handler.messageData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomMessage {
    private int id;
    private ItemMessage item;
    private EnemyMessage enemy;
    private List<PlayerMessage> players;
    private final Map<RoomCardinalDirection, Integer> adjacentRooms;
    private String roomType;

    public RoomMessage(int id, EnemyMessage enemies, List<PlayerMessage> players, int roomNorth, int roomSouth, int roomWest, int roomEast, String roomType) {
        this.id = id;
        this.enemy = enemies;
        this.players = players;
        this.adjacentRooms = new HashMap<>();
        this.roomType = roomType;
        adjacentRooms.put(RoomCardinalDirection.North, roomNorth);
        adjacentRooms.put(RoomCardinalDirection.South, roomSouth);
        adjacentRooms.put(RoomCardinalDirection.East, roomEast);
        adjacentRooms.put(RoomCardinalDirection.West, roomWest);
    }
    public int getId() {
        return id;
    }
}
