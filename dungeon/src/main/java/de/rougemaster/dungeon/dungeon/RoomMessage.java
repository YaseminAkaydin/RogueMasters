package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.item.Item;


import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomMessage {
    private int id;
    protected List<Item> items;
    protected List<Character> characters;
    private final Map<RoomCardinalDirection, Integer> adjacentRooms;


    public RoomMessage(int id, List<Item> items, List<Character> characters, int roomNorth, int roomSouth, int roomWest, int roomEast) {
        this.id = id;
        this.items = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.adjacentRooms = new HashMap<>();
        adjacentRooms.put(RoomCardinalDirection.North, roomNorth);
        adjacentRooms.put(RoomCardinalDirection.South, roomSouth);
        adjacentRooms.put(RoomCardinalDirection.East, roomEast);
        adjacentRooms.put(RoomCardinalDirection.West, roomWest);
    }
}
