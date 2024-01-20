package de.rougemaster.dungeon.lobby.messageData;

import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.item.Item;

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

    public PlayerMessage (PlayableCharacter playableCharacter){
        this(   playableCharacter.getLevel(),
                playableCharacter.getExperience(),
                playableCharacter.getMaxExperience(),
                playableCharacter.getName(),
                convertItemToItemMessage(playableCharacter.getInventory()),
                playableCharacter.getMaxHp(),
                playableCharacter.getHp(),
                playableCharacter.getAttack(),
                playableCharacter.getDefense(),
                playableCharacter.getId(),
                new ItemMessage(playableCharacter.getArmorSlot()),
                new ItemMessage(playableCharacter.getWeaponSlot()),
                playableCharacter.getCurrentRoom().getId());
    }

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

    private static List<ItemMessage> convertItemToItemMessage (List<Item> inventory){
        return inventory
                .stream()
                .map(ItemMessage::new)
                .toList();
    }


}
