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
}
