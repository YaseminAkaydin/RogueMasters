package de.roguemaster.player;

import de.roguemaster.player.ViewPackage.DataForView.MonsterData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;

import java.util.List;

public class GameState {

    private final List<PlayerData> playerList;
    private final List<MonsterData> enemyList;
    private final List<RoomData> roomList;

    public GameState(List<PlayerData> playerList, List<MonsterData> enemyList, List<RoomData> roomList) {
        this.playerList = playerList;
        this.enemyList = enemyList;
        this.roomList = roomList;
    }
    // Methode die durch die playerList geht und den localenSpieler anhand seiner ID zurückgibt
    public PlayerData getLocalPlayer(int playerId) {
        for (PlayerData player : playerList) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null;
    }
    // Methode die die RoomDaten Liste zurückgibt
    public List<RoomData> getRoomList() {
        return roomList;
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
