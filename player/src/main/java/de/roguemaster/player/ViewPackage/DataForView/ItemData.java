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
        if (name.startsWith("Weapon")) this.damage = itemAttribute;
        if (name.startsWith("Armor")) this.defense = itemAttribute;
        if (name.startsWith("Potion")) this.effect = itemAttribute;
        if (name.startsWith("Book")) this.extraPoints = itemAttribute;
    }

    // Getter & Setter

    public String getAttributeName(){
        if (name.startsWith("Weapon")) return "Damage";
        if (name.startsWith("Armor")) return "Defense";
        if (name.startsWith("Potion")) return "Effect";
        if (name.startsWith("Book")) return "Experience";
        return "Server sends wrong Name";
    }

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
        if (name.contains("Sword")) return this.damage;
        if (name.contains("Shield")) return this.defense;
        if (name.contains("Potion")) return this.effect;
        if (name.contains("Book")) return this.extraPoints;
        return 999;
    }

}
