package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.List;

/**
 * Erste Dummy Version der PlayerDaten
 */
public class PlayerData {
    private int level;
    private int experience;
    private List<ItemData> inventory;
    private int maxHp;
    private int hp;
    private int id;

    public PlayerData(int level, int hp, int experience, List<ItemData> inventory, int maxHp, int id) {
        this.level = level;
        this.hp = hp;
        this.experience = experience;
        this.inventory = new ArrayList<>(inventory);
        this.maxHp = maxHp;
        this.id = id;
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

    public int getExperience() {
        return experience;
    }

    public List<ItemData> getInventory() {
        return inventory;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getId() {
        return id;
    }
}

