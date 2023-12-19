package de.roguemaster.player.CommandLineInterface;

import java.util.List;
import java.util.Random;

// evtl in ein DungeonPackage verschieben dann zwei klassen machen
class Room {
    int x, y, width, height;
    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}


/**
 * Pseudo Dungeon, double check den shit later
 */
public class Dungeon {
    private final char[][] layout;
    private final int width;
    private final int height;
    private final Random random;
    private final int MIN_ROOM_SIZE = 4;
    private final int MAX_ROOM_SIZE = 10;
    private final int ROOM_PADDING = 5; // Space between rooms
    private final List<Room> rooms;

    public Dungeon(char[][] layout, int width, int height, Random random, List<Room> rooms) {
        this.layout = layout;
        this.width = width;
        this.height = height;
        this.random = random;
        this.rooms = rooms;
    }

    private static Dungeon dungeon = null;

    public Dungeon createDungeon(char[][] layout, int width, int height, Random random, List<Room> rooms){
        if(dungeon == null){
            dungeon = new Dungeon(layout, width, height, random, rooms);
        }
        return dungeon;
    }

    public char[][] getLayout() {
        return layout;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Random getRandom() {
        return random;
    }

    public int getMIN_ROOM_SIZE() {
        return MIN_ROOM_SIZE;
    }

    public int getMAX_ROOM_SIZE() {
        return MAX_ROOM_SIZE;
    }

    public int getROOM_PADDING() {
        return ROOM_PADDING;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public static Dungeon getDungeon() {
        return dungeon;
    }

    public static void setDungeon(Dungeon dungeon) {
        Dungeon.dungeon = dungeon;
    }
}
