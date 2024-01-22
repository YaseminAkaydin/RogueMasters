package de.rougemaster.dungeon.lobby;
import de.rougemaster.dungeon.character.Character;

import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.enemy.EnemyFacade;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.*;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.*;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.*;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.*;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.*;
import de.rougemaster.dungeon.item.Item;

import java.util.*;

public class Lobby {
    private final Map<Integer, Character> characterMap;
    private final Game game;

    private int lobbyId;
    private LobbyThread lobbyThread;

    public Lobby(Game game) {
        characterMap = new HashMap<>();
        this.game = game;
        this.lobbyThread = new LobbyThread(this);
        new Thread(lobbyThread).start();
    }

    /**
     * Translates LobbyCommand to GameCommand
     * @param lobbyMessage The lobby message that is to be translated
     * @return The Translated Game command
     */
    private GameCommand translateCommand(Character character, LobbyMessage lobbyMessage) {

        if (lobbyMessage == null) {
            throw new IllegalArgumentException("LobbyCommand can't be null.");
        }

        int id;
        Room room= null;
        Item item= null;

        if (!Objects.equals(lobbyMessage.getTarget(), "")) {
            id = Integer.parseInt(lobbyMessage.getTarget().substring(1));
        } else {
            id = -1;
        }

        if (lobbyMessage.getTarget().startsWith("r")) {
            room = game
                    .getDungeon()
                    .getRoomList()
                    .stream()
                    .filter(room1 -> room1.getId() == id)
                    .toList().get(0);
        } else {
            Room roomOfCharacter = character.getCurrentRoom();
            item = roomOfCharacter.getItem();
        }
        if (lobbyMessage.getCommand().startsWith("drop") || lobbyMessage.getCommand().startsWith("use") || lobbyMessage.getCommand().startsWith("equip")) {
            PlayableCharacter player = (PlayableCharacter) character;
            item = player.getInventory().stream().filter(item1 -> item1.getId() == id).toList().get(0);
        }


        if (character instanceof PlayableCharacter) {
            switch (lobbyMessage.getCommand()) {
                case "move" -> {return new moveGameCommand(character, room);} // passt
                case "doNothing" -> {return new doNothingGameCommand(character);} // juckt erstmal nicht
                case "flee" -> {return new fleeGameCommand(character, room);} // passt
                case "attackUsingEquipment" -> {return new attackUsingEquipmentCommand();}
                case "defend" -> {return new defendingPlayerCommand();}
                case "equipItem" -> {return new equipItemGameCommand((PlayableCharacter) character, item);}
                case "takeItem" -> {return new takeItemInRoomGameCommand((PlayableCharacter) character, item);}
                case "useItem" -> {return new useItemGameCommand((PlayableCharacter) character, item);}
                case "useItemInFight" -> {return new inspectRoomGameCommand((PlayableCharacter) character, room);}
                case "dropItem" -> {return new deleteItemGameCommand((PlayableCharacter)character,item);}
            }
        }

        if (character instanceof Zombie) {
            switch (lobbyMessage.getCommand()) {
                case ("clawAttack") -> {return new zombieClawAttackCommand();}
                case ("biteAttack") -> {return new zombieBiteAttackCommand();}
            }
        }

        if (character instanceof Skeleton) {
            switch (lobbyMessage.getCommand()) {
                case ("defend") -> {return new defendingSkeletonCommand();}
                case ("boneAttack") -> {return new skeletonBoneAttackCommand();}
                case ("bonesExplosion") -> {return new skeletonBonesplosionAttackCommand();}
                case ("skeletonSwordAttack") -> {return new skeletonSwordAttackCommand();}
            }
        }

        if (character instanceof Devil) {
            switch (lobbyMessage.getCommand()) {
                case ("defend") -> {return new devilDefendCommand();}
                case ("flameSwordAttack") -> {return new devilFlameSwordAttackCommand();}
                case ("devilSwordAttack") -> {return new devilSwordAttackCommand();}
                case ("playerKillerAttack") -> {return new devilPlayerKillerAttackCommand();}
                case ("spikeShield") -> {return new devilSpikeShieldCommand();}
            }
        }
        return new doNothingGameCommand(character);
    }

    /**
     * Enters a LobbyCommand into the Game. The Command is executed by the Game.
     * @param clientId the ClientID of the User
     * @param lobbyMessage The Lobby command that is to be entered.
     */
    public void enterCommand(int clientId, LobbyMessage lobbyMessage){
        Character clientCharacter = characterMap.get(clientId);
        GameCommand clientGameCommand = translateCommand(clientCharacter,lobbyMessage);
        game.setCharacterTurn(clientGameCommand, clientCharacter);
    }

    public int getClientID(Character character){
        int result=-1;
        for (Map.Entry<Integer, Character> entry: characterMap.entrySet()) {
            if(entry.getValue().equals(character)){
                result=entry.getKey();
            }
        }
        return result;
    }

    /**
     * Registers a User to the Lobby
     * @param clientId The Identifikator of the client
     * @param lobbyCharType The character type of the client
     */
    public int joinClientLobby(int clientId, LobbyCharType lobbyCharType){
        //Check if clientId and character have a valid value.
        if(clientId<=0){
            return -1;
        }

        //Check if Client is already registered
        if(characterMap.containsKey(clientId)){
            return -1;
        }
        Character joinedCharacter;
        if(lobbyCharType == LobbyCharType.PlayableCharacter) {
            joinedCharacter = game.addPlayer();
        }else{
            joinedCharacter = game.addEnemy(lobbyCharType);
        }
        characterMap.put(clientId, joinedCharacter);

        return joinedCharacter.getId();
    }

    /**
     * Removes a User from the Lobby
     * @param clientId The Identifikator of the client
     */
    public void leaveClientLobby(int clientId){
        //Check if clientId is in lobby.
        if(!characterMap.containsKey(clientId)){
            return;
        }
        game.removeCharacter(characterMap.get(clientId));
    }


    /**
     * Character dies and is removed from the game
     * @param character The character that is to be removed
     */
    public void killCharacter(Character character){
        EnemyFacade enemyFacade= EnemyFacade.getInstance();
        if(!characterMap.containsValue(character)){
            return;
        }
        character.die();
        game.removeCharacter(character);

    }

    /**
     * Starts the game
     */
    public void startGame(){
        EnemyFacade enemyFacade = EnemyFacade.getInstance();
        enemyFacade.requestEnemy(lobbyId, LobbyCharType.Devil);
        enemyFacade.requestEnemy(lobbyId, LobbyCharType.Skeleton);
        enemyFacade.requestEnemy(lobbyId, LobbyCharType.Skeleton);
        enemyFacade.requestEnemy(lobbyId, LobbyCharType.Skeleton);
        //enemyFacade.requestEnemy(lobbyId, LobbyCharType.Zombie);
    }

    /**
     * Sends the next Turn signal to LobbyFacade.
     */
    public void nextTurn(){
        GameState gameStateCopy = game.getGameState();
        LobbyFacade.getInstance().sendNextTurn(characterMap.keySet(), gameStateCopy);
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public GameState getGameState() {
        return game.getGameState();
    }

    public Game getGame() {
        return game;
    }

    public Set<Integer> getLobbyIds () {
        return characterMap.keySet();
    }
}
