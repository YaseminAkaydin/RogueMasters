package de.rougemaster.dungeon.lobby.messageData;


import de.rougemaster.dungeon.game.GameState;

import java.util.List;

public class GameStateMessage {

    private final List<PlayerMessage> playerList;
    private final List<EnemyMessage> enemyList;
    private final List<RoomMessage> roomList;

    public GameStateMessage(GameState gameState) {
        this.playerList = gameState.getPlayerList().stream().map(PlayerMessage::new).toList();
        this.enemyList = gameState.getEnemyList().stream().map(EnemyMessage::new).toList();
        this.roomList = gameState.getRoomList();
    }
}
