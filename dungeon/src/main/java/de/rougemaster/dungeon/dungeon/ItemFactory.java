package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.item.Book;
import de.rougemaster.dungeon.item.Item;

import java.util.function.BiPredicate;

/**
 * A factory class for creating Item objects.
 */
public class ItemFactory {

    /**
     * Creates a new Item object.
     * This method can be extended to include more parameters
     * or logic to create different types of items.
     *
     * @return a new Item object
     */
    public static Item createItem() {
        int i = 0;
        Book book = new Book(i, "Book", "Religious stuff.", 15);
        i++;
        return book;

    }
}
