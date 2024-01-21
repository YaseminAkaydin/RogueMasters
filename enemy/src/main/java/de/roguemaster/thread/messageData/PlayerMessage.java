package de.roguemaster.thread.messageData;

import java.util.List;

public class PlayerMessage {
    int level;
    int experience;
    int maxExperience;
    String name;
    List<ItemMessage> inventory;
    int maxHP;
    int hp;
    int attack;
    int defense;
    int id;
    ItemMessage armorSlot;
    ItemMessage weaponSlot;

    int currentRoomId;

    private PlayerMessage(int level, int experience, int maxExperience,
                          String name, List<ItemMessage> inventory,
                          int maxHP, int hp, int attack, int defense, int id,
                          ItemMessage armorSlot, ItemMessage weaponSlot, int currentRoomId) {
        this.level = level;
        this.experience = experience;
        this.maxExperience = maxExperience;
        this.name = name;
        this.inventory = inventory;
        this.maxHP = maxHP;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.id = id;
        this.armorSlot = armorSlot;
        this.weaponSlot = weaponSlot;
        this.currentRoomId = currentRoomId;
    }



}
