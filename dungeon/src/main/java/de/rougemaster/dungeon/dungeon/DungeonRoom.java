package de.rougemaster.dungeon.dungeon;

import javax.lang.model.type.NullType;
import java.util.Random;


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
    }
    @Override
    public String getRoomType() {
        return "DungeonRoom";
    }

}
