package de.roguemaster.player.ViewPackage.DataForView;

public class MonsterData {
    private String name;
    private int dangerLevel;
    private int maxHp;
    private int hp;
    private int id;
    // private int attack; // brauchen wir nicht deswegen erstmal weggelassen
    // private int defense;

    public MonsterData(String name, int dangerLevel, int maxHp, int hp, int id) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.maxHp = maxHp;
        this.hp = hp;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
