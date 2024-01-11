package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.List;

/**
 * Erste Dummy Version der PlayerDaten
 */
public class PlayerData {
    private int level;
    private int hp;
    private int exp;
    private List<ItemData> inventoryItems;
    private int currentRoomID;


    public PlayerData(int level, int hp, int exp, List<ItemData> inventoryItems, int currentRoomID) {
        this.level = level;
        this.hp = hp;
        this.exp = exp;
        this.inventoryItems = new ArrayList<>(inventoryItems);
        this.currentRoomID = currentRoomID;
    }

    public int getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(int currentRoomID) {
        this.currentRoomID = currentRoomID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public List<ItemData> getInventoryItems() {
        return inventoryItems;
    }
    public void setInventoryItems(List<ItemData> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}

