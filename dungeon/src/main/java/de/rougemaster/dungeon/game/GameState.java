package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

import java.util.List;

public class GameState {

    private final List<PlayableCharacter> playerList;
    private final List<EnemyCharacter> enemyList;
    private final List<RoomMessage> roomList;

    public GameState(List<PlayableCharacter> playerList, List<EnemyCharacter> enemyList, List<RoomMessage> roomList) {
        this.playerList = playerList;
        this.enemyList = enemyList;
        this.roomList = roomList;
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
}
