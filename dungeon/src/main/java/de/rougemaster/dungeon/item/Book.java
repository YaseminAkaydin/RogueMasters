package de.rougemaster.dungeon.item;

import java.util.Random;

public class Book extends Consumable{
    private int extraPoints;
    public Book(int id, String name, String description, int extraPoints) {
        super(id, name, description);
        this.extraPoints=extraPoints;
    }

    // Gedanke: Player erhält beim benutzen eines Buchs neue XP
    @Override
    public int use() {
        int min= 100;
        int max= 500;
        Random random= new Random();
        return random.nextInt(max-min +1)+min;
    }
}
