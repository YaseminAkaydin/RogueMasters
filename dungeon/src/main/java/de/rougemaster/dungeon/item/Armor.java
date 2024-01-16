package de.rougemaster.dungeon.item;

public class Armor extends Eqipable{
    private int defense;

    public Armor(int id, String name, String description, int defense) {
        super(id, name, description);
        this.defense=defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
