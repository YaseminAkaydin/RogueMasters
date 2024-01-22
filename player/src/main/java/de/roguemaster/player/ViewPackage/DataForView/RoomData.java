package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RoomData. Used in ViewBuilder to create the View.
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

    // Getter & Setter
    public String getRoomType() {
        return roomType;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    public MonsterData getEnemy() {
        return enemy;
    }

    public ItemData getItem() {
        return item;
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getAdjacentRooms() {
        return adjacentRooms;
    }





}
