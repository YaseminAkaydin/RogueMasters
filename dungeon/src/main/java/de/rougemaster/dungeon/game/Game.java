package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<PlayableCharacter> playerList;
    private final List<EnemyCharacter> enemyList;

    private final Dungeon dungeon;

    private final TurnManager turnManager;

    /**
     * Creates a new Game
     * @param dungeonRoomCount the number of rooms in the dungeon
     * @param dungeonDifficultyLevel the difficulty level of the dungeon
     */
    public Game(int dungeonRoomCount, int dungeonDifficultyLevel) {
        this.playerList = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        this.dungeon = new Dungeon(dungeonRoomCount,dungeonDifficultyLevel);

        this.turnManager = new TurnManager(playerList, enemyList);
    }

    /**
     * Adds a player to the game
     * @param player the player that is to be added
     */
    public void addPlayer(PlayableCharacter player) {
        playerList.add(player);
    }

    /**
     * Adds an enemy to the game
     * @param enemy the enemy that is to be added
     */
    public void addEnemy(EnemyCharacter enemy) {
        enemyList.add(enemy);
    }

    /**
     * Starts the game
     */
    private void startGame() {
        //TODO: Start Game in TurnManager
    }

    /**
     * Ends the game
     */
    private void endGame() {
        //TODO: Trigger everything that needs to be triggered when the game ends.
    }

    /**
     * Sets the turn of a character in TurnManager
     * @param gameCommand the command that is to be executed
     * @param character the character that is to be set
     */
    private void setCharacterTurn(GameCommand gameCommand, Character character) {
        //TODO: Set Character Turn in TurnManager
    }
}
