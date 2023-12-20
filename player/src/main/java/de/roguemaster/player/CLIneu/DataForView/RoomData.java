package de.roguemaster.player.CLIneu.DataForView;

import java.util.List;

/**
 * Erste Dummy Version der Räume.
 */
public class RoomData {
    private String roomType; // normal, boss, treasure
    private String monster; // Skeleton, Zombie, Boss
    private List<String> items; // Sword, Shield, Potion, Book
    private List<String> doors; // north, south, east, west
    private int roomID;


    public RoomData(String roomType, String monster, List<String> items, List<String> doors, int roomID) {
        this.roomType = roomType;
        this.monster = monster;
        this.items = items;
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

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
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
