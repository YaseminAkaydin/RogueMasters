package de.rougemaster.dungeon.lobby;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacterFactory;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.GameState;

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
     * @param lobbyMessage The lobby message that is to be translated
     * @return The Translated Game command
     */
    private GameCommand translateCommand(LobbyMessage lobbyMessage){
        if(lobbyMessage == null){
            throw new IllegalArgumentException("LobbyCommand can't be null.");
        }
        //TODO: Translate LobbyMessage to GameCommand
        return null;
    }

    /**
     * Enters a LobbyCommand into the Game. The Command is executed by the Game.
     * @param clientId the ClientID of the User
     * @param lobbyMessage The Lobby command that is to be entered.
     */
    public void enterCommand(int clientId, LobbyMessage lobbyMessage){
        Character clientCharacter = characterMap.get(clientId);
        GameCommand clientGameCommand = translateCommand(lobbyMessage);
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
