package de.rougemaster.dungeon.character.playerCharacter;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.item.Armor;
import de.rougemaster.dungeon.item.Book;
import de.rougemaster.dungeon.item.Item;
import de.rougemaster.dungeon.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacter extends Character {
    private static int idCounter = 0;
    private int level;
    private int experience;

    private final List<Item> inventory;

    private Armor armorSlot;
    private Weapon weaponSlot;



    public PlayableCharacter () {
        //TODO: Set Stats of Character
        id= idCounter++;
        inventory = List.of(new Book(0, "Book", "A book that gives you 10 EXP", 10), new Weapon(1, "Sword", "A sword that gives you 10 Attack", 10), new Armor(2, "Shield", "A shield that gives you 10 Defense", 10));
        armorSlot = null;
        weaponSlot = null;
        hp= 10;
        attack=10;
    }

    /**
     * Attacks an enemy Character in a fight
     * @param character enemy character
     */
    public void attackUsingEquipment(Character character) {
        int attackDamage = weaponSlot == null ? this.attack : this.attack + weaponSlot.getDamage();
        int netDamage = attackDamage - character.getDefense();
        if (netDamage > 0) {
            character.setHp(character.getHp() - netDamage);
        }
    }

    /**
     * Increases the defense of a character for a round in a fight
     */
    public void defend() {
        this.defense *= 2;
    }

    /**
     * Lowers the defense of a character for a round in a fight.
     * WARNING: Only called by FightManager, which ensures defending is handled correctly.
     */
    public void stopDefending(){
        this.defense /= 2;
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
        if(exp < 0) {
            return;
        }
        if(level >= 15){
            return;
        }

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
    public void takeItemInRoom(Item item){
        if(currentRoom.getItem() != null){
            currentRoom.removeItem();
            inventory.add(item);
        }else{
            //TODO: throw exception
        }
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

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getMaxExperience(){
        return (int)Math.pow(level, 1.5);
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
