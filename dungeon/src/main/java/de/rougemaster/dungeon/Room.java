package de.rougemaster.dungeon;

import javax.lang.model.type.NullType;
import java.util.*;
//TODO: Import Items und EnemyCharacter (durch NullType ersetzen), prüfen ob Typen von Klassenvariablen korrekt sind, Kommentare

abstract public class Room {

    static int instanceCounter = 0;
    private int id;
    protected List<NullType> items;
    protected List<NullType> enemies;
    private Map<String, Room> adjacentRooms;


    public Room() {
        instanceCounter++;
        this.id = instanceCounter;
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.adjacentRooms = new HashMap<>();
        adjacentRooms.put("NORTH", null);
        adjacentRooms.put("EAST", null);
        adjacentRooms.put("SOUTH", null);
        adjacentRooms.put("WEST", null);

    }

    public void addAdjacentRoom(String direction, Room room) {
        adjacentRooms.put(direction, room);
    }

    public Room getAdjacentRoom(String direction) {
        return adjacentRooms.get(direction);
    }

    public Map<String, Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    public int getId() {
        return id;
    }

    abstract public void generateRoom();


}
