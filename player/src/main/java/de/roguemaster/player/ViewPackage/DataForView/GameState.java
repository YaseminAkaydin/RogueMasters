package de.roguemaster.player.ViewPackage.DataForView;

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
}
