package de.roguemaster.handler.messageData;

public class ItemMessage {
    int id;
    String typ;
    String name;
    String description;
    int itemAttribute;


    private ItemMessage(int id, String typ, String name, String description, int itemAttribute) {
        this.id = id;
        this.typ = typ;
        this.name = name;
        this.description = description;
        this.itemAttribute = itemAttribute;
    }

}
