package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacterFactory;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.BossRoom;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.ItemManager;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.fight.Fight;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.lobby.LobbyCharType;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final List<PlayableCharacter> playerList;
    private final List<EnemyCharacter> enemyList;
    private final Dungeon dungeon;
    private final ItemManager itemManager;

    private final TurnManager turnManager;
    private LobbyThread turnManagerThread;

    /**
     * Creates a new Game
     * @param dungeonRoomCount the number of rooms in the dungeon
     * @param dungeonDifficultyLevel the difficulty level of the dungeon
     */
    public Game(int dungeonRoomCount, int dungeonDifficultyLevel) {
        this.playerList = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        this.dungeon = new Dungeon(dungeonRoomCount,dungeonDifficultyLevel);
        this.itemManager = new ItemManager(dungeon);
        this.itemManager.placeItem();
        this.turnManager = new TurnManager();
        this.turnManagerThread = null;
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
     * @param enemyType the enemytype that is to be added
     */
    public EnemyCharacter addEnemy(LobbyCharType enemyType) {
        EnemyCharacterFactory enemyFactory = new EnemyCharacterFactory();
        EnemyCharacter enemy = enemyFactory.createEnemy(convertLobbyCharTypToEnemyCharType(enemyType));

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
        return enemy;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * Starts the game
     */
    private void startGame() {
        turnManagerThread.run();
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


    //TODO: Testttttt
    /**
     * Removes a player from the game
     * @param character the player that is to be removed
     */
    public void removeCharacter(Character character) {
        //Check if char is in playerList or enemyList by class type

        if(turnManager.getFightManager().getAllCharactersInFights().contains(character)) {
            //Fight beenden
            //character aus der Commanmap vom turnmanager holen
            Fight fight = turnManager.getFightManager().getFight(character);
            turnManager.getFightManager().endFight(fight);
            turnManager.getCommandMap().remove(character);
        }else {
            turnManager.getCommandMap().remove(character);
        }

        if(character instanceof PlayableCharacter){
            playerList.remove(character);
        } else {
            enemyList.remove(character);

        }
    }

    public void createTurnManagerThread (LobbyThread turnManagerThread) {
        this.turnManagerThread = turnManagerThread;
    }



    /**
     * Translates a LobbyMessage to a GameCommand
     * @param lobbyCharType lobbyCharType the CharTypeToBeTranslated
     * @return the translated GameCommand
     */
    private EnemyCharacterFactory.EnemyTyp convertLobbyCharTypToEnemyCharType(LobbyCharType lobbyCharType) {
        return switch (lobbyCharType) {
            case Zombie -> EnemyCharacterFactory.EnemyTyp.Zombie;
            case Skeleton -> EnemyCharacterFactory.EnemyTyp.Skeleton;
            case Devil -> EnemyCharacterFactory.EnemyTyp.Devil;
            default -> throw new IllegalArgumentException("PlayableCharacter is not an EnemyCharacter");
        };
    }
}
