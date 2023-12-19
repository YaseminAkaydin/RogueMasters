package de.rougemaster.dungeon.dungeon;

public class RoomFactory {

    public enum RoomType {
        DUNGEON, BOSS, TREASURE
    }

    public Room createRoom(RoomType type) {
        return switch (type) {
            case DUNGEON -> new DungeonRoom();
            case BOSS -> new BossRoom();
            case TREASURE -> new TreasureRoom();
            default -> throw new IllegalArgumentException("Unknown Room Type");
        };
    }


}
