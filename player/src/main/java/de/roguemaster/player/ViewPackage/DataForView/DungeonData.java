package de.roguemaster.player.ViewPackage.DataForView;

import java.util.List;

/**
 * DungeonData, only contains List of Rooms at first
 */
public class DungeonData {
    private final List<RoomData> rooms;
    public DungeonData(List<RoomData> rooms) {
        this.rooms = rooms;
    }

    public List<RoomData> getRooms() {
        return rooms;
    }

}
