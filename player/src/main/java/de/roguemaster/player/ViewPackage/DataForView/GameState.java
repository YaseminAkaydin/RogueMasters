package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameState. Gets PlayerData, MonsterData and RoomData via constructor and creates a HashMap with RoomData and PlayerData.
 */
public class GameState {


    private List<PlayerData> playerList;
    private List<MonsterData> enemyList;

    // the room of the localPlayer
    private RoomData roomData;
    // the localPlayer
    private PlayerData playerData;
    // inventoryitems localPlayer
    private List<ItemData> inventoryItems;


    private List<RoomData> roomList;
    private HashMap<RoomData, List<PlayerData>> playerRoomMap; // which player is in which room, in one room can be multiple players, but one player can only be in one room
    private int localPlayerID = -1;

    public GameState(List<PlayerData> playerList, List<MonsterData> enemyList, List<RoomData> roomList) {

    }

    /**
     * Initializes the game state. This includes initializing the player-room map, setting the local
     * player data, finding the room data for the player's current room, and retrieving the inventory
     * items of the local player.
     */
    public void initGameState() {
        this.playerRoomMap = new HashMap<>();
        initPlayerRoomMap();
        this.playerData = getLocalPlayer();
        if (playerData == null) {
            System.out.println("Local player died");
            return;
        }
        this.roomData = findRoomById(playerData.getCurrentRoomId());
        this.inventoryItems = playerData.getInventory();
    }

    /**
     * Finds and returns the RoomData for a specified room ID. If the room is not found, a message is
     * printed, and null is returned. An exception can be thrown instead if the room ID is not found.
     *
     * @param roomId The ID of the room to find.
     * @return The RoomData for the specified room ID, or null if the room is not found.
     */
    private RoomData findRoomById(int roomId) {
        for (RoomData room : roomList) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        System.out.println("Room with id " + roomId + " not found.");
        return null; // oder werfen Sie eine Exception, falls die roomId nicht gefunden wurde
    }

    /**
     * Initializes the player-room map. The map is created to associate each room with the list of players
     * present in that room.
     */
    public void initPlayerRoomMap() {
        playerRoomMap = new HashMap<>();
        for (RoomData room : roomList) {
            List<PlayerData> playersInRoom = new ArrayList<>(room.getPlayers());
            playerRoomMap.put(room, playersInRoom);
        }
    }

    // Getter & Setter
    public PlayerData getLocalPlayer() {
        for (PlayerData player : playerList) {
            if (player.getId() == localPlayerID) {
                return player;
            }
        }
        return null;
    }

    public List<RoomData> getRoomList() {
        return roomList;
    }

    public void setLocalPlayerID(int localPlayerID) {
        this.localPlayerID = localPlayerID;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public List<ItemData> getInventoryItems() {
        return inventoryItems;
    }
}