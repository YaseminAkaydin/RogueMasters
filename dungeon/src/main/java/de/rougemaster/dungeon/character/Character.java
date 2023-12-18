package de.rougemaster.dungeon.character;

import de.rougemaster.dungeon.dungeon.Room;

public abstract class Character {

    protected int maxHp;
    protected int hp;
    protected String name;
    protected int attack;
    protected int defense;
    protected int id;

    protected Room currentRoom;

    /**
     * move u to the given Room
     * @param room the room you want to move to.
     */
    public void move(Room room) {
        currentRoom = room;
    }

    /**
     * Makes you flee into a given to adjacent Room.
     * Only usable in a fight.
     * @param room the room you want to move to.
     * @return if fleeing was successful.
     */
    public boolean flee(Room room){
        //TODO: Check if PlayerCharacter is in a fight.
        //TODO: Check if Room is reachable.
        //TOOO: Stop battle.
        move(room);

        return false;
    }

    /**
     * Makes the given Character do nothing.
     * This Method is maybe not needed and should be implemented in turnManager.
     */
    public void doNothing(){
        //TODO: implement after turnManager/fightManager is done.
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getHp() {
        return hp;
    }

    public int getDefense() {
        return defense;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
