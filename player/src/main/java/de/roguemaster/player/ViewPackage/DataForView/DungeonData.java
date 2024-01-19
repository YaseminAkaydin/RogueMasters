package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DungeonData. Gets Rooms via constructor and creates a HashMap with RoomData and PlayerData.
 * Used in ViewBuilder to create the View.
 * Updating the GameState is done via the Setter of the Data Classes
 */
public class DungeonData {

    private List<RoomData> rooms;
    private HashMap<RoomData, List<PlayerData>> playerRoomMap; // which player is in which room, in one room can be multiple players, but one player can only be in one room

    /**
     * Constructor
     * @param rooms
     */
    public DungeonData(List<RoomData> rooms) {
        this.rooms = rooms;
        this.playerRoomMap = new HashMap<>();
        initPlayerRoomMap();
    }

    /**
     * Creates a HashMap with RoomData and PlayerData.
     */
    private void initPlayerRoomMap() {
        playerRoomMap = new HashMap<>();
        for (RoomData room : rooms) {
            List<PlayerData> playersInRoom = new ArrayList<>(room.getPlayers());
            playerRoomMap.put(room, playersInRoom);
        }
    }

}
