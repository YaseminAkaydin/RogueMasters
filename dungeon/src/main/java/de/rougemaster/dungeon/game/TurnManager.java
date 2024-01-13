package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.fight.FightManager;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.gameCommand.doNothingGameCommand;
import de.rougemaster.dungeon.lobby.Lobby;


import java.util.*;

public class TurnManager {
    FightManager fightManager;
    PlayableCharacter character;
    Map<Character, GameCommand> commandMap;
    Map<FightManager, List<Character>> fightList;

    public Map<Character, GameCommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Character, GameCommand> commandMap) {
        this.commandMap = commandMap;
    }

    //kriegt einen Character von der Lobby sowie den GameCommand (map)
    public TurnManager() {
        this.commandMap=new HashMap<>();
    }

    public void executeTurn(){
        startTurn();
        handleCharacterInput();
    }


    /**
     * Kopiert die aktuelle Command Map mit den aktuellen Commands pro Character.
     */
    private Map<Character,GameCommand> startTurn(){
        Map<Character,GameCommand> copyCommandMap= commandMap;
        List<Character> allFights= fightManager.getAllCharactersInFights();
        fightManager.executeAllTurns();
        for (Map.Entry<Character, GameCommand> entry : commandMap.entrySet()) {
            Character currentCharacter = entry.getKey();
            if(allFights.contains(currentCharacter)){
                doNothingGameCommand newCommand = new doNothingGameCommand(character);
                entry.setValue(newCommand);
            }
        }

        return copyCommandMap;
    }

    /**
     * K端mmert sich um den konkreten Input des Caracters. Geht mit den konkreten Commands um, die ein Spieler/Gegener
     * eingegeben hat. Sollten sich zwei Spieler in einem Raum befinden, wird an den FightManager weitergegeben.
     */
    private void handleCharacterInput(){
        Map<Character, GameCommand> commandMap= startTurn();
        for (Map.Entry<Character, GameCommand> entry : commandMap.entrySet()) {
            GameCommand command = entry.getValue();
            command.execute();

        }
        // Iteration durch die Map, um Charactere im gleichen Raum zu finden
        for (Map.Entry<Character, GameCommand> entry1 : commandMap.entrySet()) {
            Character character1 = entry1.getKey();
            Room room1 = character1.getCurrentRoom();

            for (Map.Entry<Character, GameCommand> entry2 : commandMap.entrySet()) {
                Character character2 = entry2.getKey();
                Room room2 = character2.getCurrentRoom();


                List<Character> fightingCharacters = fightManager.getAllCharactersInFights();
                if (fightingCharacters.contains(character1) || fightingCharacters.contains(character2)){
                    continue;
                }

                if (room1.equals(room2) && !character1.equals(character2)) {
                    fightManager.startFight(character1, character2);
                }

            }
        }

        for (Map.Entry<Character,GameCommand> entry: commandMap.entrySet()) {
            doNothingGameCommand newCommand = new doNothingGameCommand(character);
            entry.setValue(newCommand);
        }

    }









}