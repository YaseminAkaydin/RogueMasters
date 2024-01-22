package de.roguemaster.handler.messageData;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameStateMessage {

    private final List<PlayerMessage> playerList;
    private final List<EnemyMessage> enemyList;
    private final List<RoomMessage> roomList;
    private final Map<Integer, Integer> fightMap;

    //private final Map<Integer, RoomMessage> locationMap;

    public GameStateMessage() {
        this.enemyList = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.roomList = new ArrayList<>();
        this.fightMap = new HashMap<>();
    }

    public List<EnemyMessage> getEnemyList() {
        return enemyList;
    }

    public List<PlayerMessage> getPlayerList() {
        return playerList;
    }

    public List<RoomMessage> getRoomList() {
        return roomList;
    }

    public Map<Integer, Integer> getFightMap() {
        return fightMap;
    }
}
