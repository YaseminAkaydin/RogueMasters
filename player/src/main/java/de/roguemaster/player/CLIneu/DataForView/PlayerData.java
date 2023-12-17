package de.roguemaster.player.CLIneu.DataForView;

import java.util.List;

/**
 * This class is used to store the data needed for the view.
 * TODO: Daten anpassen an echte Daten auf server (Cemos Klassen)
 */
public class PlayerData {
    private int level;
    private int hp;
    private int exp;
    private List<String> inventoryItems;


    public PlayerData(int level, int hp, int exp, List<String> inventoryItems) {
        this.level = level;
        this.hp = hp;
        this.exp = exp;
        this.inventoryItems = inventoryItems;
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

    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    public void setInventoryItems(List<String> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}

