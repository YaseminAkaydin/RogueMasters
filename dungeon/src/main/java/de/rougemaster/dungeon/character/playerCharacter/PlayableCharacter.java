package de.rougemaster.dungeon.character.playerCharacter;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.item.Armor;
import de.rougemaster.dungeon.item.Item;
import de.rougemaster.dungeon.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacter extends Character {
    private int level;
    private int experience;

    private final List<Item> inventory;

    private Armor armorSlot;
    private Weapon weaponSlot;


    public PlayableCharacter () {
        //TODO: Set Stats of Character
        inventory = new ArrayList<>();
        armorSlot = null;
        weaponSlot = null;
    }

    /**
     * Attacks an enemy Character in a fight
     * @param character enemy character
     */
    public void attackUsingEquipment(Character character) {
        //TODO: Check if character is in the same Room as PlayerCharacter
        //TODO: Check if character is in battle with PlayerCharacter

        /*TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */
        int attackDamage = this.attack + weaponSlot.getWeaponDamage();
        character.setHp(character.getHp()-(attackDamage - character.getDefense()));
    }

    /**
     * Increases the defense of a character for a round in a fight
     */
    public void defend() {
        //TODO: Check if character is in the same Room as PlayerCharacter

        /*
        * TODO: The Following implementation is a example and shouldn't be used for the final product.
        *       There should be a FightManager to handle this. */
        this.defense *= 2;
    }


    /**
     * Activates the SpecialAbility of the Character.
     * TODO: This can be implemented at a later time.
     */
    @Deprecated
    public void useSpecialAbility(){

    }

    /**
     * Uses a given Item on the PlayerCharacter
     * @param item the Item that is used.
     * @throws InventoryItemMissingException if inventory doesn't contain the given item.
     */
    public void useItem(Item item) throws InventoryItemMissingException{
        if(!inventory.contains(item)){
            throw new InventoryItemMissingException();
        }
        //TODO: useItem can first be implemented after Items are implemented.
    }

    /**
     * Gives the PlayerCharacter experience. If the Player reached the needed EXP for a level up Player gets leveled up.
     * If max level is reached this method does nothing.
     * @param exp the amount of Experience gained. exp can only be positive
     */
    public void gainExperience (int exp) {
        //TODO: The Following implementation is a example and shouldn't be used for the final product.

        if(exp < 0) {
            return;
        }
        //TODO: Check if level cap is reached

        experience += exp;

        //Check if level up is possible and calculate level up
        while(experience >= (int)Math.pow(level, 1.5)) {
            experience -= (int)Math.pow(level, 1.5);
            level++;
        }

        //TODO: Increase Stats
    }

    /**
     * Checks if the Room has an Item.
     * This Methode might not be needed in PlayerCharacter and might be moved to turnManager.
     */
    public void inspectRoom(){
        //TODO: Can only be implemented after Room is implemented
    }

    /**
     * Takes the Item from the room and puts it in the inventory.
     */
    public void takeItemInRoom(){
        //TODO: Check if Room has a Item
        //TODO: Remove Item from Room
        //TODO: Add Item to Inventory
    }

    /**
     * Equips a given Item.
     * @param item the given Item. It has to be in the PlayerCharacter inventory.
     * @throws InventoryItemMissingException if inventory doesn't contain the given item.
     */
    public void equipItem(Item item) throws InventoryItemMissingException{
        if(!inventory.contains(item)) {
            throw new InventoryItemMissingException();
        }
        //TODO: Check if item is equipable.

        if(item instanceof Weapon){
            weaponSlot = (Weapon) item;
        }
        else if(item instanceof Armor){
            armorSlot = (Armor) item;
        }
        else{
            throw new IllegalArgumentException("Item is equipable but not a weapon or a piece of armor");
        }
    }
}
