package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.item.Item;

import javax.lang.model.type.NullType;
import java.util.Random;

//TODO: generateRoom?
public class DungeonRoom extends Room {
    private float enemySpawnChanceInPercent = 0.3f;
    private int enemyMaximumAmount = 1;
    private float itemSpawnChanceInPercent = 0.2f;
    private int itemMaximumAmount = 2;


    public DungeonRoom() {
        super();
    }

    @Override
    public void generateRoom() {
        Random random = new Random();

        // ENEMIES
        for (int i = 0; i < enemyMaximumAmount; i++) {
            boolean createItem = random.nextInt(100) < enemySpawnChanceInPercent;
            if (createItem) {
                //create enemy character
                NullType enemy = null;
                //enemies.add(enemy);
            }
        }

        // ITEMS
        for (int i = 0; i < itemMaximumAmount; i++) {
            boolean createItem = random.nextInt(100) < itemSpawnChanceInPercent;
            if (createItem) {
                //TODO: create item here?
                break;
            }
        }
    }
}
