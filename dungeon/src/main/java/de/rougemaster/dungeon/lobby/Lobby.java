package de.rougemaster.dungeon.lobby;
import de.rougemaster.dungeon.character.Character;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacterFactory;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.doNothingGameCommand;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.fleeGameCommand;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.moveGameCommand;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.GameState;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilDefendCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilFlameSwordAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilPlayerKillerAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilSpikeShieldCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.*;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.defendingSkeletonCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonBoneAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonBonesplosionAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonSwordAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieBiteAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieClawAttackCommand;
import de.rougemaster.dungeon.item.Item;

import java.util.HashMap;
import java.util.Map;

public class Lobby {
    private final Map<Integer, Character> characterMap;
    private final Game game;

    private int lobbyId;

    public Lobby(Game game) {
        characterMap = new HashMap<>();
        this.game = game;
    }

    /**
     * Translates LobbyCommand to GameCommand
     *
     * @param lobbyMessage The lobby message that is to be translated
     * @return The Translated Game command
     */
    private GameCommand translateCommand(Character character, LobbyMessage lobbyMessage) {
        if (lobbyMessage == null) {
            throw new IllegalArgumentException("LobbyCommand can't be null.");
        }

        Room room= null;
        Item item= null;
        int id = Integer.parseInt(lobbyMessage.getTarget().substring(1));
        if (lobbyMessage.getTarget().startsWith("r")) {
            room = game
                    .getDungeon()
                    .getRoomList()
                    .stream()
                    .filter(room1 -> room1.getId() == id)
                    .toList().get(0);
        } else {
            Room roomOfCharacter = character.getCurrentRoom();
            item = roomOfCharacter.getItems().stream().filter(item1 -> item1.getId() == id).toList().get(0);

        }

        if (character instanceof PlayableCharacter) {
            PlayableCharacter playableCharacter = (PlayableCharacter) character;
            switch (lobbyMessage.getCommand()) {
                case ("move"):
                    return new moveGameCommand(character, character.getCurrentRoom());
                case ("doNothing"):
                    return new doNothingGameCommand(character);
                case ("flee"):
                    return new fleeGameCommand(character, room);
                case ("attackUsingEquipment"):
                    return new attackUsingEquipmentCommand();
                case ("defend"):
                    return new defendingPlayerCommand();
                case ("equipItem"):
                    return new equipItemGameCommand(playableCharacter, item);
                case ("takeItem"):
                    return new takeItemInRoomGameCommand(playableCharacter, item);
                case ("useItem"):
                    return new useItemGameCommand(playableCharacter, item);
                case ("useItemInFight"):
                    return new inspectRoomGameCommand(playableCharacter, room);
            }
        } else if (character instanceof Zombie) {
            Zombie zombie = (Zombie) character;
            switch (lobbyMessage.getCommand()) {
                case ("clawAttack"):
                    return new zombieClawAttackCommand();
                case ("biteAttack"):
                    return new zombieBiteAttackCommand();
            }
        } else if (character instanceof Skeleton) {
            switch (lobbyMessage.getCommand()) {
                case ("defend"):
                    return new defendingSkeletonCommand();
                case ("boneAttack"):
                    return new skeletonBoneAttackCommand();
                case ("bonesPlosion"):
                    return new skeletonBonesplosionAttackCommand();
                case ("swordAttack"):
                    return new skeletonSwordAttackCommand();
            }
        } else {
            switch (lobbyMessage.getCommand()) {
                case ("defend"):
                    return new devilDefendCommand();
                case ("flameSwordAttack"):
                    return new devilFlameSwordAttackCommand();
                case ("playerKillerAttack"):
                    return new devilPlayerKillerAttackCommand();
                case ("spikeShield"):
                    return new devilSpikeShieldCommand();
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

    /**
     * Registers a User to the Lobby
     * @param clientId The Identifikator of the client
     * @param lobbyCharType The character type of the client
     */
    public void joinClientLobby(int clientId, LobbyCharType lobbyCharType){
        //Check if clientId and character have a valid value.
        if(clientId<=0){
            return;
        }

        //Check if Client is already registered
        if(characterMap.containsKey(clientId)){
            return;
        }

        //switch case for different character types
        Character character = switch (lobbyCharType) {
            case PlayableCharacter -> new PlayableCharacter();
            case Zombie -> new EnemyCharacterFactory().createEnemy(EnemyCharacterFactory.EnemyTyp.Zombie, (int) (Math.random() * 8 + 1));
            case Skeleton -> new EnemyCharacterFactory().createEnemy(EnemyCharacterFactory.EnemyTyp.Skeleton, (int) (Math.random() * 12 + 1));
            case Devil -> new EnemyCharacterFactory().createEnemy(EnemyCharacterFactory.EnemyTyp.Devil, 15);

            default -> throw new IllegalArgumentException("LobbyCharType is not valid.");
        };
        //TODO: Add Character to Game
    }

    /**
     * Unregisters a User from the Lobby
     * @param clientId The Identifikator of the client
     */
    public void leaveClientLobby(int clientId){
        //Check if clientId is in lobby.
        if(!characterMap.containsKey(clientId)){
            return;
        }
        //TODO: Remove Character from Game
    }

    /**
     * Sends the next Turn signal to LobbyFacade.
     * @param gameState the current GameState
     */
    public void nextTurn(GameState gameState){
        //TODO: Trigger nextTurn method in LobbyFacade with all clientIds
        GameState gameStateCopy = game.getGameState();
        for (Integer clientId: characterMap.keySet()) {
            LobbyFacade.getInstance().sendNextTurn(clientId, gameStateCopy);
        }
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }
}
