package de.rougemaster.dungeon.item;

import java.util.Random;

public class Potion extends Consumable{
    private int effect;

    public Potion(int id, String name, String description, int effect) {
        super(id, name, description);
        this.effect= effect;
    }

    //Player erhält Lebenspunkte
    @Override
    public int use() {
        return effect;
    }

    public int getEffect() {
        return effect;
    }
}
