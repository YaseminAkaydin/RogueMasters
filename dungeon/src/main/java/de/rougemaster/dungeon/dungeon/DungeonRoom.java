package de.rougemaster.dungeon.dungeon;

import javax.lang.model.type.NullType;
import java.util.Random;

//TODO: generateRoom?
public class DungeonRoom extends Room {
    private float enemySpawnChanceInPercent = 0.3f;
    private int enemyMaximumAmount = 1;
    private float itemSpawnChanceInPercent = 0.2f;
    private int itemMaximumAmount = 1;


    public DungeonRoom() {
        super();
    }

    @Override
    public void generateRoom() {
        Random random = new Random();

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
