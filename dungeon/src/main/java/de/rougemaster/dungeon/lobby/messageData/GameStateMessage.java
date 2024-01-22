package de.rougemaster.dungeon.lobby.messageData;


import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.game.GameState;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameStateMessage {

    private final List<PlayerMessage> playerList;
    private final List<EnemyMessage> enemyList;
    private final List<RoomMessage> roomList;
    private final Map<Integer, Integer> fightMap;

    //private final Map<Integer, RoomMessage> locationMap;

    public GameStateMessage(GameState gameState) {
        this.playerList = gameState.getPlayerList().stream().map(PlayerMessage::new).toList();
        this.enemyList = gameState.getEnemyList().stream().map(EnemyMessage::new).toList();
        this.roomList = gameState.getRoomList();
        for(RoomMessage rm: roomList){
            Optional<EnemyCharacter> opEnemy = gameState.getEnemyList().stream().filter(enemyCharacter -> enemyCharacter.getCurrentRoom().getId() == rm.getId()).findFirst();
            opEnemy.ifPresent(rm::setEnemy);
            for(PlayableCharacter p : gameState.getPlayerList()) {
                if(p.getCurrentRoom().getId() == rm.getId()){
                    rm.addPlayer(p);
                }
            }
        }
        this.fightMap = gameState.getFightMap();
    }
}
