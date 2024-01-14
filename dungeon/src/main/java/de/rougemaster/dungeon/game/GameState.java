package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;

import java.util.List;

public class GameState {

    private final List<PlayableCharacter> playerList;
    private final List<EnemyCharacter> enemyList;
    private final Dungeon dungeon;

    public GameState(List<PlayableCharacter> playerList, List<EnemyCharacter> enemyList, Dungeon dungeon) {
        this.playerList = playerList;
        this.enemyList = enemyList;
        this.dungeon = dungeon;
    }

}
