package de.rougemaster.dungeon.item;

public abstract class Item {
    private int id;
    private String name;
    private String description;

    // Konstruktor für die Klasse Item
    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
