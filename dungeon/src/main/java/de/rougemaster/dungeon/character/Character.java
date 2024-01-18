package de.rougemaster.dungeon.character;

import de.rougemaster.dungeon.dungeon.Room;

import java.util.Random;

public abstract class Character {

    protected int maxHp;
    protected int hp;
    protected String name;
    protected int attack;
    protected int defense;
    protected int id;

    protected Room currentRoom;

    /**
     * move u to the given adjacent Room
     * @param room the room you want to move to.
     */
    public void move(Room room) {
        if(currentRoom.isRoomAdjacent(room)){
            currentRoom = room;
        }
    }

    /**
     * move u to any given Room
     * @param room the room you want to move to.
     */
    public void teleport(Room room){
        currentRoom = room;
    }

    /**
     * Makes you flee into a given to adjacent Room.
     * Only usable in a fight.
     * @param room the room you want to move to.
     * @return if fleeing was successful.
     */
    public boolean flee(Room room){
        if(new Random().nextBoolean()){
            move(room);
            return true;
        }
        return false;
    }

    /**
     * Makes the given Character do nothing.
     * This Method is maybe not needed and should be implemented in turnManager.
     */
    public void doNothing(){
        //TODO: Counter einbauen, und schauen, wie oft hintereinander doNothing ausgeführt wurde
        return;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
