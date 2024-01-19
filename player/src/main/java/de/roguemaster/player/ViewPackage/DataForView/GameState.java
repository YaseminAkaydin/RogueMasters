package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void initGameState() {

        this.playerRoomMap = new HashMap<>();
        initPlayerRoomMap();

        this.roomData = findRoomById(getCurrentRoomId());
        this.playerData = getLocalPlayer();
        this.inventoryItems = playerData.getInventory();
    }

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
     * Creates a HashMap with RoomData and PlayerData.
     */
    public void initPlayerRoomMap() {
        playerRoomMap = new HashMap<>();
        for (RoomData room : roomList) {
            List<PlayerData> playersInRoom = new ArrayList<>(room.getPlayers());
            playerRoomMap.put(room, playersInRoom);
        }
    }


    // Getter & Setter
    public int getCurrentRoomId() {
        for (Map.Entry<RoomData, List<PlayerData>> entry : playerRoomMap.entrySet()) {
            RoomData room = entry.getKey();
            List<PlayerData> players = entry.getValue();

            for (PlayerData player : players) {
                if (player.getId() == localPlayerID) { // Assuming PlayerData has a getUserId() method that returns the player's userID
                    //System.out.println("Current Room ID: " + room.getId());
                    return room.getId(); // Assuming RoomData has a getRoomId() method
                }
            }
        }
        return -1; // Return -1 or throw an exception if the player with the given userID is not found in any room
    }

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

    public HashMap<RoomData, List<PlayerData>> getPlayerRoomMap() {
        return playerRoomMap;
    }

    public int getLocalPlayerID() {
        return localPlayerID;
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