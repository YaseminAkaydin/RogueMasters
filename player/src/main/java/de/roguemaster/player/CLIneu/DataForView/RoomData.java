package de.roguemaster.player.CLIneu.DataForView;

import java.util.ArrayList;
import java.util.List;

/**
 * Erste Dummy Version der Räume.
 * TODO: An echte Rooms anpassen
 */
public class RoomData {
    private String roomType; // normal, boss, treasure
    private String monster; // Skeleton, Zombie, Boss
    private List<ItemData> items; // Sword, Shield, Potion, Book
    private List<String> doors; // north, south, east, west
    private int roomID;


    public RoomData(String roomType, String monster, List<ItemData> items, List<String> doors, int roomID) {
        this.roomType = roomType;
        this.monster = monster;
        this.items = new ArrayList<>(items); // Max 2 Items, haben wir so entschieden
        this.doors = doors;
        this.roomID = roomID;

    }

    public String getRoomType() {
        return roomType;
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

    public List<String> getDoors() {
        return doors;
    }

    public void setDoors(List<String> doors) {
        this.doors = doors;
    }


    public int getRoomID() {
        return roomID;
    }

}
