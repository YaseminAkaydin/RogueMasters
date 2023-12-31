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
        int min= 100;
        int max= 800;
        Random random= new Random();
        return random.nextInt(max-min +1)+min;
    }
}
