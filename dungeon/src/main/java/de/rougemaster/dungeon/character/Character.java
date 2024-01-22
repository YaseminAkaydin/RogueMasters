package de.rougemaster.dungeon.character;

import de.rougemaster.dungeon.dungeon.Room;

import java.util.Objects;
import java.util.Random;

public abstract class Character {
    protected static int idCounter = 0;
    protected int maxHp;
    protected int hp;
    protected String name;
    protected int attack;
    protected int defense;
    protected int id;

    protected Room currentRoom;


    /**
     * Returns the level of the Character
     * @return the amount EXP that the character drops when killed
     */
    public abstract int getEXP();

    /**
     * Returns the level of the Character
     * @return the level of the Character
     */
    public abstract int getLevel();

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
     */
    public void flee(Room room){
        move(room);
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


    public void die(){
        hp = 0;
        currentRoom = null;
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

    @Override
    //hashcode und equals für die LobbyMap
    public int hashCode() {
        return Objects.hash(id);
    }


}
