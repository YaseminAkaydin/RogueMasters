package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.item.Item;
import lombok.Getter;

import javax.lang.model.type.NullType;
import java.util.*;
//TODO: Import Items und EnemyCharacter (durch NullType ersetzen), prüfen ob Typen von Klassenvariablen korrekt sind, Kommentare

abstract public class Room {

    static int instanceCounter = 0;
    private int id;
    protected List<Item> items;
    protected List<Character> enemies;
    private final Map<RoomCardinalDirection, Room> adjacentRooms;

    public Room() {
        instanceCounter++;
        this.id = instanceCounter;
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
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

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public RoomMessage getRoomMessage() {
        return new RoomMessage(
                this.id,
                this.items,
                this.enemies,
                this.adjacentRooms.get(RoomCardinalDirection.North) == null ? -1 : this.adjacentRooms.get(RoomCardinalDirection.North).id,
                this.adjacentRooms.get(RoomCardinalDirection.South) == null ? -1 : this.adjacentRooms.get(RoomCardinalDirection.South).id,
                this.adjacentRooms.get(RoomCardinalDirection.West) == null ? -1 : this.adjacentRooms.get(RoomCardinalDirection.West).id,
                this.adjacentRooms.get(RoomCardinalDirection.East) == null ? -1 : this.adjacentRooms.get(RoomCardinalDirection.East).id
        );
    }
}
