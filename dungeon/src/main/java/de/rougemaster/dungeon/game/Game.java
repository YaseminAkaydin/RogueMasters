package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacterFactory;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.BossRoom;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.ItemManager;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.fight.Fight;
import de.rougemaster.dungeon.game.fight.FightManager;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.doNothingGameCommand;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.lobby.LobbyCharType;
import de.rougemaster.dungeon.lobby.LobbyThread;
import de.rougemaster.dungeon.lobby.messageData.RoomMessage;

import java.util.*;

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
        this.itemManager= new ItemManager(dungeon);
        itemManager.placeItem();
        this.turnManager = new TurnManager();
        this.turnManagerThread = null;
    }

    public List<PlayableCharacter> getPlayerList(){
        return playerList;
    }
    /**
     * Adds a player to the game
     */
    public PlayableCharacter addPlayer() {
        PlayableCharacter player = new PlayableCharacter();

        player.teleport(findFreeRoom());
        setCharacterTurn(new doNothingGameCommand(player), player);
        playerList.add(player);
        return player;
    }

    public Room findFreeRoom(){
        // TODO: Fall Devil !!

        List<Room> allRooms = dungeon.getRoomList();
        List<Room> notAvailableRooms= new ArrayList<>();
        notAvailableRooms.add(findBossRoom());

        for (PlayableCharacter playableCharacter: playerList) {
            notAvailableRooms.add(playableCharacter.getCurrentRoom());
        }
        for (EnemyCharacter enemyCharacter: enemyList){
            notAvailableRooms.add(enemyCharacter.getCurrentRoom());
        }
        Set<Room> mergedSet= new HashSet<>(allRooms);
        mergedSet.removeAll(notAvailableRooms);

        List<Room> roomList = new ArrayList<>(mergedSet);
        Room firstRoom = null;
        if (!roomList.isEmpty()) {
            Random random = new Random();
            firstRoom = roomList.get(random.nextInt(roomList.size())); // -1 an size vllt
        }
        return firstRoom;
    }

    /**
     * Adds an enemy to the game, puts him into a room, except boss -> bossroom
     * @param enemyType the enemytype that is to be added
     */
    public EnemyCharacter addEnemy(LobbyCharType enemyType) {;
        EnemyCharacterFactory enemyFactory = new EnemyCharacterFactory();

        int playerLevelSum = playerList.stream()
                .mapToInt(PlayableCharacter::getLevel)
                .sum();
        
        int playlerLevelAvg = playerLevelSum / playerList.size();
        EnemyCharacter enemy = enemyFactory.createEnemy(convertLobbyCharTypToEnemyCharType(enemyType), playlerLevelAvg);

        if(enemyType ==LobbyCharType.Devil){
            enemy.teleport(findBossRoom());
        }else {
            enemy.teleport(findFreeRoom());
        }

        setCharacterTurn(new doNothingGameCommand(enemy), enemy);
        enemyList.add(enemy);
        return enemy;
    }

    public Room findBossRoom(){
        Room bossRoom =null;
        List<Room> allRooms = dungeon.getRoomList();
        for (Room room: allRooms){
            if(room instanceof BossRoom){
                bossRoom = room;
            }
        }

        return bossRoom;
    }

    public TurnManager getTurnManager() {
        return turnManager;
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
        Map<Integer,Integer> fightMap = new HashMap<>();
        List<Fight> fightList = this.turnManager.getFightManager().getActiveFights();
        for(Fight fight : fightList){
            fightMap.put(fight.getCombatantOne().getId(),fight.getCombatantTwo().getId());
            fightMap.put(fight.getCombatantTwo().getId(),fight.getCombatantOne().getId());
        }

        return new GameState(playerList
                            , enemyList
                            , dungeon.getRoomList().stream().map(RoomMessage::new).toList()
                            , fightMap);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }


    /**
     * Removes a player from the game
     * @param character the player that is to be removed
     */
    public void removeCharacter(Character character) {
        FightManager fightManager = turnManager.getFightManager();

        if(fightManager.getAllCharactersInFights().contains(character)) {
            Fight fight = fightManager.getFight(character);
            fightManager.endFight(fight);
        }

        turnManager.getCommandMap().remove(character);

        if(character instanceof PlayableCharacter){
            playerList.remove(character);
        }
        if(character instanceof EnemyCharacter){
            enemyList.remove(character);
        }

    }


    /**
     * Searches for all dead characters in the game
     * @return the characters that were searched for
     */
    public List<Character> searchAndRemoveAllDeadPlayableCharacters(){
        Map<Character, GameCommand> commandMap = turnManager.getCommandMap();
        List<Character> playableCharactersToRemove = new ArrayList<>();

        for (Character character: commandMap.keySet()) {
            if (character.getHp() <= 0) {
                playableCharactersToRemove.add(character);
            }
        }
        return playableCharactersToRemove;
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
