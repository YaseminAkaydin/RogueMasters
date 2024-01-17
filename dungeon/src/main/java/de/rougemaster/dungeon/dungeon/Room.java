package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.item.Item;

import java.util.*;
//TODO: Import Items und EnemyCharacter (durch NullType ersetzen), prüfen ob Typen von Klassenvariablen korrekt sind, Kommentare

abstract public class Room {

    static int instanceCounter = 0;
    private int id;
    protected Item item;
    protected List<Character> characters;
    private final Map<RoomCardinalDirection, Room> adjacentRooms;

    public Room() {
        instanceCounter++;
        this.id = instanceCounter;
        this.item = ItemFactory.createItem();
        this.characters = new ArrayList<>();
        this.adjacentRooms = new HashMap<>();
        adjacentRooms.put(RoomCardinalDirection.North, null);
        adjacentRooms.put(RoomCardinalDirection.South, null);
        adjacentRooms.put(RoomCardinalDirection.East, null);
        adjacentRooms.put(RoomCardinalDirection.West, null);
    }

    public void addAdjacentRoom(RoomCardinalDirection direction, Room room) {
        adjacentRooms.put(direction, room);
    }

    public Room getAdjacentRoom(RoomCardinalDirection direction) {
        return adjacentRooms.get(direction);
    }

    public Map<RoomCardinalDirection, Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    public int getId() {
        return id;
    }

    abstract public void generateRoom();

    public boolean isRoomAdjacent(Room room) {
        return adjacentRooms.containsValue(room);
    }

    public Item getItem() {
        return item;
    }

    public void removeItem() {this.item = null;}

    public List<Character> getCharacters() {
        return characters;
    }
    public static int getInstanceCounter() {
        return instanceCounter;
    }
}
