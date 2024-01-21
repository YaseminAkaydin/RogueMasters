package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerData. Used in ViewBuilder to create the View.
 */
public class PlayerData {
    private int level;
    private int experience;
    private List<ItemData> inventory;
    private int maxHP;
    private int hp;
    private int id;
    private ItemData armorSlot;
    private ItemData weaponSlot;
    private int currentRoomId;
    private int attack;
    private int defense;

    public PlayerData(int level, int hp, int experience,
                      List<ItemData> inventory, int maxHp,
                      int id, ItemData armorSlot, ItemData weaponSlot,
                      int currentRoomId, int attack, int defense) {
        this.level = level;
        this.hp = hp;
        this.experience = experience;
        this.inventory = new ArrayList<>(inventory);
        this.maxHP = maxHp;
        this.id = id;
        this.armorSlot = armorSlot;
        this.weaponSlot = weaponSlot;
        this.currentRoomId = currentRoomId;
        this.attack = attack;
        this.defense = defense;
    }

    public int getLevel() {
        return level;
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
        return maxHP;
    }

    public int getId() {
        return id;
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public ItemData getArmorSlot() {
        return armorSlot;
    }

    public ItemData getWeaponSlot() {
        return weaponSlot;
    }
}

