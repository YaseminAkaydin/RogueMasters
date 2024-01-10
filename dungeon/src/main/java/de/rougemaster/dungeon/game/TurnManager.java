package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.fight.FightManager;
import de.rougemaster.dungeon.lobby.Lobby;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TurnManager {
    FightManager fightManager;
    Lobby lobby;
    Game game;
    GameState gameState;
    PlayableCharacter character;
    Map<Character, GameCommand> commandMap;
    Map<FightManager, List<Character>> fightList;




    //kriegt einen Character von der Lobby sowie den GameCommand (map)
    public TurnManager(Map<Character, GameCommand> commandMap) {
        this.commandMap=commandMap;
    }


    /**
     * Kopiert die aktuelle Command Map mit den aktuellen Commands pro Character.
     */
    public Map<Character,GameCommand> startTurn(){
        Map<Character,GameCommand> copyCommandMap= commandMap;
        List<Character> allFights= fightManager.getAllCharactersInFights();
        for (Map.Entry<Character, GameCommand> entry : commandMap.entrySet()) {
            Character character = entry.getKey();
            GameCommand command = entry.getValue();
            if(allFights.contains(character)){
                //TODO: FightCommand ausführen
            }
        }

        for (Character character: allFights) {
            //TODO: do Nothing eintragen
        }

        return copyCommandMap;
    }

    /**
     * K端mmert sich um den konkreten Input des Caracters. Geht mit den konkreten Commands um, die ein Spieler/Gegener
     * eingegeben hat. Sollten sich zwei Spieler in einem Raum befinden, wird an den FightManager weitergegeben.
     */
    public void handleCharacterInput(){
        Map<Character, GameCommand> commandMap= startTurn();
        // Iteration durch die Map, um Charactere im gleichen Raum zu finden
        for (Map.Entry<Character, GameCommand> entry1 : commandMap.entrySet()) {
            Character character1 = entry1.getKey();
            Room room1 = character1.getCurrentRoom();

            for (Map.Entry<Character, GameCommand> entry2 : commandMap.entrySet()) {
                Character character2 = entry2.getKey();
                Room room2 = character2.getCurrentRoom();

                if (room1.equals(room2) && !character1.equals(character2)) {
                    fightManager.startFight(character1, character2);
                }
            }
        }
        for (Map.Entry<Character, GameCommand> entry : commandMap.entrySet()) {
            Character character = entry.getKey();
            GameCommand command = entry.getValue();
            //TODO: GameCommand.execute(command) aufrufen für den jeweiligen Character
        }
        endTurn(commandMap);

    }


    /**
     * Sendet das Signal an alle, dass der Turn vorbei ist(triggerNextTurn vom Game aus)
     * und die Tabelle wird mit doNothing gef端llt. Hier wird auch rausgesucht,
     * welche Leute sich gleichzeitig in einem Raum befinden und somit sich in einem Kampf befinden.
     */
    public void endTurn(Map<Character, GameCommand> commansMap){
        // Iteration durch die Map, um Charactere im gleichen Raum zu finden
        for (Map.Entry<Character, GameCommand> entry1 : commandMap.entrySet()) {
            Character character1 = entry1.getKey();
            Room room1 = character1.getCurrentRoom();

            for (Map.Entry<Character, GameCommand> entry2 : commandMap.entrySet()) {
                Character character2 = entry2.getKey();
                Room room2 = character2.getCurrentRoom();

                if (room1.equals(room2) && !character1.equals(character2)) {
                    fightManager.startFight(character1, character2);
                }
            }
        }
        // Iteration durch die Map, um jedem Character den GameCommand "doNothing" zuzuweisen
        for (Character character : commandMap.keySet()) {
            //TODO: für jeden Character den GameCommand "doNothing" eintragen
        }
        //TODO: TriggerNextTurn von Game aufrufen

    }





}