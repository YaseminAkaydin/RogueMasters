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

    public List<PlayableCharacter> getPlayerList() {
        return playerList;
    }

    public List<EnemyCharacter> getEnemyList() {
        return enemyList;
    }

    public List<RoomMessage> getRoomList() {
        return roomList;
    }
}
