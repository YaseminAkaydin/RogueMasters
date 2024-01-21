package de.roguemaster.player.ViewPackage.DataForView;

/**
 * MonsterData. Used in ViewBuilder to create the View.
 */
public class MonsterData {
    private String name;
    private int dangerLevel;
    private int maxHp;
    private int hp;
    private int id;
    private int attack;
    private int defense;
    private int currentRoomId;
    // private int attack; // brauchen wir nicht deswegen erstmal weggelassen
    // private int defense;

    public MonsterData(String name, int dangerLevel, int maxHp, int hp, int id, int attack, int defense, int currentRoomId) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.maxHp = maxHp;
        this.hp = hp;
        this.id = id;
        this.attack = attack;
        this.defense = defense;
        this.currentRoomId = currentRoomId;
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }


    public int getMaxHp() {
        return maxHp;
    }


    public int getHp() {
        return hp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttack() {
        return attack;
    }
    public int getDefense() {
        return defense;
    }
}
