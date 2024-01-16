package de.roguemaster.player.ViewPackage.DataForView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * DungeonData, only contains List of Rooms at first
 */
public class DungeonData {
    private final List<RoomData> rooms;
    private HashMap<RoomData, List<PlayerData>> playerRoomMap; // which player is in which room, in one room can be multiple players, but one player can only be in one room

    public DungeonData(List<RoomData> rooms) {
        this.rooms = rooms;
        this.playerRoomMap = new HashMap<>();
        initPlayerRoomMap();
    }

    private void initPlayerRoomMap() {
        playerRoomMap = new HashMap<>();
        for (RoomData room : rooms) {
            List<PlayerData> playersInRoom = new ArrayList<>();
            for (PlayerData player : room.getCharacters()) {
                playersInRoom.add(player);
            }
            playerRoomMap.put(room, playersInRoom);
        }
    }

    // Getter und Setter
    public List<RoomData> getRooms() {
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





}
