package de.rougemaster.dungeon.item;


public abstract class Consumable extends Item{
    public Consumable(int id, String name, String description) {
        super(id, name, description);
    }

    public abstract int use();
}
