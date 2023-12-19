package de.roguemaster.player.CLIneu.DataForView;

import java.util.List;

public class RoomData {
    private String roomType;
    private String monster;
    private List<String> items;
    private List<String> doors;
    private String item;
    private int roomID;

    // Constructor, getters, and setters


    public RoomData(String roomType, String monster, List<String> items, List<String> doors, String item, int roomID) {
        this.roomType = roomType;
        this.monster = monster;
        this.items = items;
        this.doors = doors;
        this.item = item;
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

    public String getItem() {
        return item;
    }

    public int getRoomID() {
        return roomID;
    }

}
