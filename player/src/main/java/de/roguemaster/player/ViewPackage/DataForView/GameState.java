package de.roguemaster.player.ViewPackage.DataForView;

import de.roguemaster.player.ViewPackage.DataForView.MonsterData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;

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

    // DungeonData
    private List<RoomData> roomList;
    private HashMap<RoomData, List<PlayerData>> playerRoomMap; // which player is in which room, in one room can be multiple players, but one player can only be in one room

    private int localPlayerID = -1;

    // TODO: Singelton maybe und dann per setter einfach aktualisieren?

    public GameState(List<PlayerData> playerList, List<MonsterData> enemyList, List<RoomData> roomList) {

    }
    public void initGameState(){

        this.playerRoomMap = new HashMap<>();
        initPlayerRoomMap();

        this.roomData = roomList.get(getCurrentRoomId()-1);
        this.playerData = getLocalPlayer();
        this.inventoryItems = playerData.getInventory();
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

    // Get CurrentRoomId of LocalPlayer
    public int getCurrentRoomId() {
        for (Map.Entry<RoomData, List<PlayerData>> entry : playerRoomMap.entrySet()) {
            RoomData room = entry.getKey();
            List<PlayerData> players = entry.getValue();

            for (PlayerData player : players) {
                if (player.getId() == localPlayerID) { // Assuming PlayerData has a getUserId() method that returns the player's userID
                    return room.getId(); // Assuming RoomData has a getRoomId() method
                }
            }
        }
        return -1; // Return -1 or throw an exception if the player with the given userID is not found in any room
    }

    // Methode die durch die playerList geht und den localenSpieler anhand seiner ID zurückgibt
    public PlayerData getLocalPlayer() {
        for (PlayerData player : playerList) {
            if (player.getId() == localPlayerID) {
                return player;
            }
        }
        return null;
    }

    // Methode die die RoomDaten Liste zurückgibt
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

    @Override
    public String toString() {
        return "GameState{" +
                "playerList=" + playerList +
                ", enemyList=" + enemyList +
                ", roomData=" + roomData +
                ", playerData=" + playerData +
                ", inventoryItems=" + inventoryItems +
                ", roomList=" + roomList +
                ", playerRoomMap=" + playerRoomMap +
                ", localPlayerID=" + localPlayerID +
                '}';
    }
}
/**
 * {
 *  "item" : {
 *    "id": 0,
 *    "typ": "Sword/Shield/Potion/Book",
 *    "name": "Super Duper Level 5 Schwert",
 *    "description": "A sword/shield/potion/book"
 *    "itemAttribute": int (sword = damage, shield=defense, potion=effect, book=extraPoints)
 *
 *   "playerList": [ 4 player max.
 *     {
 *       "level": 0,
 *       "experience": 0,
 *       "maxExperience": 0,
 *       "inventory": [], eine Liste an Items
 *       "maxHp": 0,
 *       "hp": 10,
 *       "attack": 10,
 *       "defense": 0,
 *       "id": 0
 *     }
 *   ],
 *  "enemy": [
 *     {
 *
 *       "name": "Skelenton/Zombie/Boss", --- um typ abzuleiten
 *       "dangerLevel": 0, --- ist ok zum anzeigen
 *       "maxHp": 7, --- ist gut zum anzeigen
 *       "hp": 7, --- ist gut zum anzeigen
 *       "attack": 5, --- brauch ich nicht
 *       "defense": 7, --- brauch ich nicht
 *       "id": 0 --- id immer sinnvoll
 *     }
 *   ],
 *
 *   "roomList": [ n räume
 *     {
 *       "id": 1,
 *       "item": EIN item pro Raum
 *       "players": [], max 4 player pro raum
 *       "enemies": [], immer nur ein mod pro raum passt
 *       "adjacentRooms": {
 *         "North": -1,
 *         "East": -1,
 *         "South": 2,
 *         "West": -1
 *       }
 *     }
 */

/**
 * {
 *   "data": {
 *     "playerList": [
 *       {
 *         "level": 0,
 *         "experience": 0,
 *         "inventory": [],
 *         "maxHp": 0,
 *         "hp": 10,
 *         "attack": 10,
 *         "defense": 0,
 *         "id": 0
 *       }
 *     ],
 *     "enemyList": [],
 *     "roomList": [
 *       {
 *         "id": 11,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": 12,
 *           "South": -1,
 *           "West": -1
 *         }
 *       },
 *       {
 *         "id": 12,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": 13,
 *           "South": -1,
 *           "West": 11
 *         }
 *       },
 *       {
 *         "id": 13,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": 14,
 *           "South": -1,
 *           "West": 12
 *         }
 *       },
 *       {
 *         "id": 14,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": 15,
 *           "East": 16,
 *           "South": -1,
 *           "West": 13
 *         }
 *       },
 *       {
 *         "id": 15,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": -1,
 *           "South": 14,
 *           "West": -1
 *         }
 *       },
 *       {
 *         "id": 16,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": -1,
 *           "South": 17,
 *           "West": 14
 *         }
 *       },
 *       {
 *         "id": 17,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": 16,
 *           "East": -1,
 *           "South": 18,
 *           "West": -1
 *         }
 *       },
 *       {
 *         "id": 18,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": 17,
 *           "East": 19,
 *           "South": -1,
 *           "West": -1
 *         }
 *       },
 *       {
 *         "id": 19,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": 20,
 *           "East": -1,
 *           "South": -1,
 *           "West": 18
 *         }
 *       },
 *       {
 *         "id": 20,
 *         "item": {
 *           "extraPoints": 15,
 *           "id": 1,
 *           "name": "Die Bibel",
 *           "description": "Religious stuff."
 *         },
 *         "players": [],
 *         "adjacentRooms": {
 *           "North": -1,
 *           "East": -1,
 *           "South": 19,
 *           "West": -1
 *         }
 *       }
 *     ]
 *   }
 * }
 * */
