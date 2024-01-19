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
    private List<PlayerData> players; // Spieler
    private MonsterData enemy; // Skeleton, Zombie, Boss
    private ItemData item; // Sword, Shield, Potion, Book
    private final int id;
    private final Map<String, Integer> adjacentRooms;


    public RoomData(String roomType, MonsterData enemy,
                    ItemData item, int id, Map<String, Integer> adjacentRooms, List<PlayerData> players) {
        this.roomType = roomType;
        this.enemy = enemy;
        this.players = players;
        this.item = item; // Max 2 Items, haben wir so entschieden
        this.id = id;
        this.adjacentRooms = new HashMap<>(adjacentRooms);
    }
    public RoomData(String roomType, MonsterData enemy,
                    ItemData item, int id, Map<String, Integer> adjacentRooms) {
        this.roomType = roomType;
        this.enemy = enemy;
        this.players = new ArrayList<>();
        this.item = item; // Max 2 Items, haben wir so entschieden
        this.id = id;
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

    public List<PlayerData> getPlayers() {
        return players;
    }

    public MonsterData getEnemy() {
        return enemy;
    }

    public void setEnemy(MonsterData enemy) {
        this.enemy = enemy;
    }

    public ItemData getItem() {
        return item;
    }

    public void setItem(ItemData item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }




}
