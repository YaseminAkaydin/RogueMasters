package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.BossRoom;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


        this.turnManager = new TurnManager();
    }

    /**
     * Adds a player to the game
     */
    public PlayableCharacter addPlayer() {
        PlayableCharacter player = new PlayableCharacter();

        List<Room> allRooms = dungeon.getRoomList();
        List<Room> nonBossRooms = allRooms.stream()
                .filter(room -> !(room instanceof BossRoom))
                .toList();


        //kein Random room OHNE GEGENER DRIN
        if (!nonBossRooms.isEmpty()) {
            Random random = new Random();
            boolean done= true;
            while (done){
                Room randomRoom = nonBossRooms.get(random.nextInt(nonBossRooms.size()));
                if(randomRoom.getCharacters().isEmpty()){
                    player.teleport(randomRoom);
                    done=false;
                }
            }
        }

        playerList.add(player);
        return player;
    }

    /**
     * Adds an enemy to the game, puts him into a room, except boss -> bossroom
     * @param enemy the enemy that is to be added
     */
    public void addEnemy(EnemyCharacter enemy) {

        List<Room> allRooms = dungeon.getRoomList();
        List<Room> nonBossRooms = allRooms.stream()
                .filter(room -> !(room instanceof BossRoom))
                .toList();
        BossRoom bossRoom= (BossRoom) allRooms.stream().filter(room -> room instanceof BossRoom).toList().get(0);

        if(enemy instanceof Devil){
            enemy.teleport(bossRoom);
        } else {
            if (!nonBossRooms.isEmpty()) {
                Random random = new Random();
                boolean done= true;
                while (done){
                    Room randomRoom = nonBossRooms.get(random.nextInt(nonBossRooms.size()));
                    if(randomRoom.getCharacters().isEmpty()){
                        enemy.teleport(randomRoom);
                        done=false;
                    }
                }
            }
        }
        enemyList.add(enemy);
    }

    public TurnManager getTurnManager() {
        return turnManager;
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
    public void setCharacterTurn(GameCommand gameCommand, Character character) {
        turnManager.setCharacterTurn(character, gameCommand);
    }

    /**
     * Returns the current GameState
     * @return the current GameState
     */
    public GameState getGameState(){
        return new GameState(playerList, enemyList, dungeon.getRoomList().stream().map(RoomMessage::new).toList());
    }
    public Dungeon getDungeon() {
        return dungeon;
    }


    /**
     * Removes a player from the game
     * @param character the player that is to be removed
     */
    public void removeCharacter(Character character) {
        //Check if char is in playerList or enemyList by class type
        if(character instanceof PlayableCharacter){
            playerList.remove(character);
        }
        else if(character instanceof EnemyCharacter){
            enemyList.remove(character);
        }

        //TODO: Check if fightManager has a fight with the character stop the fight
        //TODO: Check if turnManager has a turn with the character delete character from turnManager
    }
}
