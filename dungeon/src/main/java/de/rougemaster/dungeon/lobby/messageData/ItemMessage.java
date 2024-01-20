package de.rougemaster.dungeon.lobby.messageData;

import de.rougemaster.dungeon.item.*;

public class ItemMessage {
    int id;
    String typ;
    String name;
    String description;
    String itemAttribute;

    public ItemMessage (Item item) {
        this(item.getId(),
                getTypFromItem(item),
                item.getName(),
                item.getDescription(),
                getItemAttributeFromWeapon(item));
    }

    private ItemMessage(int id, String typ, String name, String description, String itemAttribute) {
        this.id = id;
        this.typ = typ;
        this.name = name;
        this.description = description;
        this.itemAttribute = itemAttribute;
    }

    private static String getTypFromItem(Item item) {
        if(item instanceof Armor){
            return "Armor";
        }
        if(item instanceof Book){
            return "Book";
        }
        if(item instanceof Potion){
            return "Potion";
        }
        if(item instanceof Weapon){
            return "Weapon";
        }
        if(item instanceof Bomb){
            return "Bomb";
        }
        throw new IllegalArgumentException("Not known Typ of Item");
    }
    private static String getItemAttributeFromWeapon(Item item) {
        if(item instanceof Armor){
            return "Defense: " + ((Armor) item).getDefense();
        }
        if(item instanceof Book){
            return "Experience: " + ((Book) item).getExtraPoints();
        }
        if(item instanceof Potion){
            return "Healing: " + ((Potion) item).getEffect();
        }
        if(item instanceof Weapon){
            return "Attack: " + ((Weapon) item).getDamage();
        }
        if(item instanceof Bomb){
            return "Damage: " + ((Bomb) item).getEffect();
        }
        throw new IllegalArgumentException("Not known Typ of Item");
    }

}
