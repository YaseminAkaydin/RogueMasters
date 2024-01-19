package de.rougemaster.dungeon.item;

public class Bomb extends Consumable{

    private int effect;

    public Bomb(int id, String name, String description, int effect) {
        super(id, name, description);
        this.effect= effect;
    }

    @Override
    public int use() {
        return effect;
    }
    public int getEffect() {
        return effect;
    }
}
