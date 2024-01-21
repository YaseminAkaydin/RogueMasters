package de.roguemaster.player.ViewPackage.DataForView;

/**
 * ItemData. Used in ViewBuilder to create the View.
 */
public class ItemData {
    private int id;
    private String name;
    private String typ;
    private String description;
    private int itemAttribute;
    public ItemData(int id, String name, String description, int itemAttribute, String typ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemAttribute = itemAttribute;
        this.typ = typ;
    }

    // Getter & Setter
    public String getAttributeName(){
        return this.typ;
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

    public String getDescription() {
        return description;
    }

    public int getAttributes(){
        return this.itemAttribute;
    }
    public String getAttributeType(){
        // Weapon/Bomb = damagae, Armor = defense, Consumable = heal, Book = experience
        if (typ.equals("Weapon") || typ.equals("Bomb")){
            return "Damage";
        } else if (typ.equals("Armor")){
            return "Defense";
        } else if (typ.equals("Consumable")){
            return "Heal";
        } else if (typ.equals("Book")){
            return "Experience";
        } else {
            return "Unkonwn";
        }
    }

}
