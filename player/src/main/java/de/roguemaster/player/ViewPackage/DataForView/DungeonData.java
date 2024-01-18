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

    /**
     * FOR TEST!
     * For Client Sided Logic Handling for switching Rooms, not needed in live version, because we get
     * the gameState with updated rooms from the server
     * @param playerId
     * @param newRoom
     */
    public void updatePlayerRoom(int playerId, RoomData newRoom) {
        // Find and remove the player from their current room
        PlayerData playerToUpdate = null;
        RoomData currentRoom = null;

        for (Map.Entry<RoomData, List<PlayerData>> entry : playerRoomMap.entrySet()) {
            List<PlayerData> players = entry.getValue();
            for (PlayerData player : players) {
                if (player.getId() == playerId) {
                    playerToUpdate = player;
                    currentRoom = entry.getKey();
                    break;
                }
            }
            if (playerToUpdate != null) {
                players.remove(playerToUpdate);
                break;
            }
        }

        // Add the player to the new room
        if (playerToUpdate != null && newRoom != null) {
            playerRoomMap.computeIfAbsent(newRoom, k -> new ArrayList<>()).add(playerToUpdate);
        } else {
            // Handle the case where the player or the new room doesn't exist
            // You might want to throw an exception or log an error here
        }
    }

    // Getter und Setter
    public List<RoomData> getRoomList() {
        return rooms;
    }

    public HashMap<RoomData, List<PlayerData>> getPlayerRoomMap() {
        return playerRoomMap;
    }

    // Get CurrentRoomId of LocalPlayer
    public int getCurrentRoomId(int userId) {
        for (Map.Entry<RoomData, List<PlayerData>> entry : playerRoomMap.entrySet()) {
            RoomData room = entry.getKey();
            List<PlayerData> players = entry.getValue();

            for (PlayerData player : players) {
                if (player.getId() == userId) { // Assuming PlayerData has a getUserId() method that returns the player's userID
                    return room.getId(); // Assuming RoomData has a getRoomId() method
                }
            }
        }
        return -1; // Return -1 or throw an exception if the player with the given userID is not found in any room
    }

    // Get the LocalPlayer ?
    public PlayerData getLocalPlayer(int playerId) {
        for (List<PlayerData> players : playerRoomMap.values()) {
            for (PlayerData player : players) {
                if (player.getId() == playerId) { // Assuming PlayerData has a getUserId() method
                    return player;
                }
            }
        }
        return null; // Return null or throw an exception if no player with the given ID is found
    }

    public void setRooms(List<RoomData> rooms) {
        this.rooms = rooms;
        initPlayerRoomMap();
    }
}
