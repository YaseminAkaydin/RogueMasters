package de.roguemaster.player.ViewPackage.DataForView;

public class ItemData {
    private int id;
    private String name;
    private String description;
    private int damage = 0; // If item is Sword/Shield/Potion/Book we overwrite the 0
    private int defense = 0;
    private int effect = 0;
    private int extraPoints = 0;

    public ItemData(int id, String name, String description, int itemAttribute) {
        this.id = id;
        this.name = name;
        this.description = description;
        if (description.contains("Sword")) this.damage = itemAttribute;
        if (description.contains("Shield")) this.defense = itemAttribute;
        if (description.contains("Potion")) this.effect = itemAttribute;
        if (description.contains("Book")) this.extraPoints = itemAttribute;
    }

    // Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttributes(){
        if (description.contains("Sword")) return this.damage;
        if (description.contains("Shield")) return this.defense;
        if (description.contains("Potion")) return this.effect;
        if (description.contains("Book")) return this.extraPoints;
        return 999;
    }

}
