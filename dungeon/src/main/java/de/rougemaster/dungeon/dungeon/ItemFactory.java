package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.item.*;


import java.util.Random;

/**
 * A factory class for creating Item objects.
 */
public class ItemFactory {

    /**
     * Creates a new Item object based on type and level.
     *
     * @param level The level of the item. 1-5, levels for books and bombs are scaled automatically.
     * @return a new Item object of the specified type and level.
     */
    public static Item createRandomItem(int level) {
        Random random = new Random();
        int num = random.nextInt(5);

        //Scaling for bombs and books
        int adjustedLevel = -1;
        if (level == 5) {
            adjustedLevel = 3;
        } else if (level == 3 || level == 4) {
            adjustedLevel = 2;
        } else {
            adjustedLevel = 1;
        }

        switch (num) {
            case 0:
                return createArmor(level);
            case 1:
                return createWeapon(level);
            case 2:
                return createBook(adjustedLevel);
            case 3:
                return createPotion(level);
            case 4:
                return createBomb(adjustedLevel);
            default:
                throw new IllegalArgumentException("Item could not be generated.");
        }
    }

    private static Armor createArmor(int level) {
        // Sample implementation, you need to define the id, name, and description for each level
        int defense = switch (level) {
            case 1 -> 3;
            case 2 -> 5;
            case 3 -> 10;
            case 4 -> 20;
            case 5 -> 30;
            default -> throw new IllegalArgumentException("Invalid level for armor");
        };
        return new Armor(level, "Armor " + level, "Armor description " + level, defense);
    }

    private static Weapon createWeapon(int level) {
        int attack = switch (level) {
            case 1 -> 3;
            case 2 -> 5;
            case 3 -> 10;
            case 4 -> 20;
            case 5 -> 30;
            default -> throw new IllegalArgumentException("Invalid level for weapon");
        };
        return new Weapon(level, "Weapon " + level, "Weapon description " + level, attack);
    }

    private static Book createBook(int level) {
        int exp = switch (level) {
            case 1 -> 5;
            case 2 -> 10;
            case 3 -> 20;
            default -> throw new IllegalArgumentException("Invalid level for book");
        };
        return new Book(level, "Book " + level, "Book description " + level, exp);
    }

    private static Potion createPotion(int level) {
        int health = switch (level) {
            case 1 -> 5;
            case 2 -> 15;
            case 3 -> 30;
            case 4 -> 45;
            case 5 -> 60;
            default -> throw new IllegalArgumentException("Invalid level for potion");
        };
        return new Potion(level, "Potion " + level, "Potion description " + level, health);
    }

    private static Bomb createBomb(int level) {
        int damage = switch (level) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> 30;
            default -> throw new IllegalArgumentException("Invalid level for bomb");
        };
        // Assuming you have a Bomb class similar to the other items
        return new Bomb(level, "Bomb " + level, "Bomb description " + level, damage);
    }
}
