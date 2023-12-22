package de.roguemaster.player.CLIneu.DataForView;

import java.util.ArrayList;
import java.util.List;

/**
 * Erste Dummy Version der PlayerDaten
 * TODO: Daten anpassen an echte Daten auf server (Cemos Klassen)
 */
public class PlayerData {
    private int level;
    private int hp;
    private int exp;
    private List<ItemData> inventoryItems;


    public PlayerData(int level, int hp, int exp, List<ItemData> inventoryItems) {
        this.level = level;
        this.hp = hp;
        this.exp = exp;
        this.inventoryItems = new ArrayList<>(inventoryItems);
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

