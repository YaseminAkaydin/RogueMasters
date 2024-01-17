package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Erste Dummy Version der Räume.
 */
public class RoomData {
    private String roomType; // DungeonRoom, BossRoom, TreasureRoom
    private List<PlayerData> characters; // Spieler
    private MonsterData monster; // Skeleton, Zombie, Boss
    private ItemData items; // Sword, Shield, Potion, Book
    private final int roomID;
    private final Map<String, Integer> adjacentRooms;


    public RoomData(String roomType, MonsterData monster,
                    ItemData items,int roomID, Map<String, Integer> adjacentRooms, List<PlayerData> characters) {
        this.roomType = roomType;
        this.monster = monster;
        this.characters = characters;
        this.items = items; // Max 2 Items, haben wir so entschieden
        this.roomID = roomID;
        this.adjacentRooms = new HashMap<>(adjacentRooms);
    }
    public RoomData(String roomType, MonsterData monster,
                    ItemData items,int roomID, Map<String, Integer> adjacentRooms) {
        this.roomType = roomType;
        this.monster = monster;
        this.characters = new ArrayList<>();
        this.items = items; // Max 2 Items, haben wir so entschieden
        this.roomID = roomID;
        this.adjacentRooms = new HashMap<>(adjacentRooms);
    }


    public String getRoomType() {
        return roomType;
    }

    public void addAdjacentRoom(String direction, int roomId) {
        adjacentRooms.put(direction, roomId);
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<PlayerData> getCharacters() {
        return characters;
    }

    public MonsterData getMonster() {
        return monster;
    }

    public void setMonster(MonsterData monster) {
        this.monster = monster;
    }

    public ItemData getItems() {
        return items;
    }

    public void setItems(ItemData items) {
        this.items = items;
    }

    public int getId() {
        return roomID;
    }

    public Map<String, Integer> getAdjacentRooms() {
        return adjacentRooms;
    }

}
