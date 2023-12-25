package de.roguemaster.player.CLIneu.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Erste Dummy Version der Räume.
 * TODO: An echte Rooms anpassen
 * TODO: Beispiel Dungeon von SS Code generieren und dann diese Daten nehmen als BSP Liste
 */
public class RoomData {
    private String roomType; // DungeonRoom, BossRoom, TreasureRoom
    private String monster; // Skeleton, Zombie, Boss
    private List<ItemData> items; // Sword, Shield, Potion, Book
    private int roomID;
    private Map<String, Integer> adjacentRooms;


    public RoomData(String roomType, String monster,
                    List<ItemData> items,int roomID, Map<String, Integer> adjacentRooms) {
        this.roomType = roomType;
        this.monster = monster;
        this.items = new ArrayList<>(items); // Max 2 Items, haben wir so entschieden
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

    public String getMonster() {
        return monster;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }

    public int getId() {
        return roomID;
    }

    public Map<String, Integer> getAdjacentRooms() {
        return adjacentRooms;
    }

    @Override
    public String toString() {
        return "RoomData{" +
                "roomType='" + roomType + '\'' +
                ", monster='" + monster + '\'' +
                ", items=" + items +
                ", roomID=" + roomID +
                ", adjacentRooms=" + adjacentRooms +
                '}';
    }
}
