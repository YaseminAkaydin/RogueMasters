package de.rougemaster.dungeon.item;

public class Weapon extends Eqipable{
    private int damage;
    public Weapon(int id, String name, String description, int damage) {
        super(id, name, description);
        this.damage=damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
