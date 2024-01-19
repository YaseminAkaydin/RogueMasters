package de.rougemaster.dungeon.dungeon;

import de.rougemaster.dungeon.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager {
    private Dungeon dungeon;

    public ItemManager(Dungeon dungeon){
        this.dungeon=dungeon;
    }

    /** Returns a list of all treasure rooms in the dungeon.
     *
     * @return list of all Treasure rooms.
     */
    public List<TreasureRoom> getAllTreasureRooms(){
        List<TreasureRoom> treasureRooms = new ArrayList<>();
        for (Room room: dungeon.getRoomList()) {
            if (room instanceof TreasureRoom){
                treasureRooms.add((TreasureRoom) room);
            }
        }
        return treasureRooms;
    }

    /**
     * Place Items in Treasure Rooms
     */
    public void placeItem(){
        Random random= new Random();
        int num= random.nextInt(6);
        List<TreasureRoom> treasureRooms= getAllTreasureRooms();
        for (Room room: treasureRooms) {
            room.item = ItemFactory.createRandomItem(num);
        }
    }
}
