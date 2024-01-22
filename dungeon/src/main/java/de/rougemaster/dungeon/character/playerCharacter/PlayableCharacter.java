package de.rougemaster.dungeon.character.playerCharacter;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.dungeon.ItemFactory;
import de.rougemaster.dungeon.item.*;

import java.util.ArrayList;
import java.util.List;

public class PlayableCharacter extends Character {
    private int level;
    private int experience;

    private final List<Item> inventory;

    private Armor armorSlot;
    private Weapon weaponSlot;


    public PlayableCharacter() {
        //TODO: Set Stats of Character
        id = ++idCounter;
        inventory = new ArrayList<>();

        equipArmor(ItemFactory.createArmor(1));
        equipWeapon(ItemFactory.createWeapon(1));

        inventory.add(armorSlot);
        inventory.add(weaponSlot);

        hp = 5;
        attack = 2;
        defense = 3;
        maxHp = 5;
        level = 1;
    }

    /**
     * Attacks an enemy Character in a fight
     * @param character enemy character
     */
    public void attackUsingEquipment(Character character) {
        int netDamage = attack - character.getDefense();
        if (netDamage > 0) {
            character.setHp(character.getHp() - Math.max(0, netDamage));
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
     * Uses a given Item on the PlayerCharacter (bombs handled in Fight)
     * @param item the Item that is used.
     * @throws InventoryItemMissingException if inventory doesn't contain the given item.
     */
    public void useItem(Item item) throws InventoryItemMissingException{
        if(!inventory.contains(item)){
            throw new InventoryItemMissingException();
        }

        if(item instanceof Eqipable){
            equipItem(item);
        }
        if (item instanceof Book){
            Book book = (Book) item;
            gainExperience(book.getExtraPoints());
            inventory.remove(book);
        }
        if (item instanceof Potion){
            hp+= ((Potion) item).use();
            if(hp>maxHp){
                hp = maxHp;
            }
            inventory.remove(item);
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
        boolean levelup = false;

        //Check if level up is possible and calculate level up
        while(experience >= (int)Math.pow(level, 1.5)) {
            experience -= (int)Math.pow(level, 1.5);
            level++;
            levelup = true;
        }


        if(level <= 0) {
            level = 1;
        }
        if(level > 15) {
            level = 15;
        }

        if(level < 5){
            this.attack= 2;
            this.defense=2;
            this.maxHp=3;

            for(int i = level; i>1; i--){
                attack += 2;
                defense += 2;
                maxHp +=  3;
            }
        }

        if(level < 10 && level >= 5 ){
            this.attack= 10;
            this.defense=10;
            this.maxHp=15;
            for(int i = level; i>1; i--){
                attack += 3;
                defense += 3;
                maxHp +=  5;
            }
        }

        if(level >= 10){
            this.attack= 25;
            this.defense=25;
            this.maxHp=40;
            for(int i = level; i>1; i--){
                attack += 4;
                defense += 4;
                maxHp +=  8;
            }
        }
        if(levelup){
            this.hp=maxHp;
        }

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
        } else {
            //TODO: throw exception
        }
    }

    public void deleteItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
        } else {
            //TODO:throw exception
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

        if (item instanceof Weapon ) {
            equipWeapon((Weapon) item);
        }

        if (item instanceof Armor) {
            equipArmor((Armor) item);
        }
    }

    private void equipWeapon(Weapon weapon) {
        if(weaponSlot != null){
            attack -= weaponSlot.getDamage();
        }
        attack += weapon.getDamage();
        weaponSlot = weapon;
    }

    private void equipArmor(Armor armor) {
        if(armorSlot != null){
            defense -= armorSlot.getDefense();
        }
        defense += armor.getDefense();
        armorSlot = armor;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getMaxExperience() {
        return (int) Math.pow(level, 1.5);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void gainItem(Item item){
        inventory.add(item);
    }

    public Item getArmorSlot() {
        return armorSlot;
    }

    public Item getWeaponSlot() {
        return weaponSlot;
    }

    @Override
    public int getEXP() {
        return 0;
    }
}
