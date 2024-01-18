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
        this.characters = new ArrayList<>();
        this.adjacentRooms = new HashMap<>();
        adjacentRooms.put(RoomCardinalDirection.North, null);
        adjacentRooms.put(RoomCardinalDirection.South, null);
        adjacentRooms.put(RoomCardinalDirection.East, null);
        adjacentRooms.put(RoomCardinalDirection.West, null);
    }

    /**
     * Adds an adjacent room in a specified direction.
     *
     * @param direction The cardinal direction where the adjacent room is located.
     * @param room The room to be added as adjacent.
     */
    public void addAdjacentRoom(RoomCardinalDirection direction, Room room) {
        adjacentRooms.put(direction, room);
    }

    /**
     * Retrieves the adjacent room in the specified direction.
     *
     * @param direction The cardinal direction of the adjacent room to retrieve.
     * @return The adjacent room in the specified direction, or null if none exists.
     */
    public Room getAdjacentRoom(RoomCardinalDirection direction) {
        return adjacentRooms.get(direction);
    }

    /**
     * Returns a map of all adjacent rooms.
     *
     * @return A map containing the adjacent rooms keyed by their cardinal directions.
     */
    public Map<RoomCardinalDirection, Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    /**
     * Retrieves the ID of this room.
     *
     * @return The ID of the room.
     */
    public int getId() {
        return id;
    }

    /**
     * Abstract method to generate the room's characteristics.
     * This method should be implemented by subclasses.
     */
    abstract public void generateRoom();

    /**
     * Checks if a specified room is adjacent to this room.
     *
     * @param room The room to check adjacency for.
     * @return true if the specified room is adjacent; false otherwise.
     */
    public boolean isRoomAdjacent(Room room) {
        return adjacentRooms.containsValue(room);
    }

    /**
     * Retrieves the item in this room.
     *
     * @return The item present in the room, or null if no item is present.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Removes the item from this room.
     */
    public void removeItem() {this.item = null;}

    /**
     * Retrieves the list of characters present in this room.
     *
     * @return The list of characters in the room.
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Retrieves the total number of Room instances created.
     *
     * @return The total number of Room instances.
     */
    public static int getInstanceCounter() {
        return instanceCounter;
    }
    //
}
