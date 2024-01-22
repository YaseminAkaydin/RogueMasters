package de.rougemaster.dungeon.game;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.fight.CombatAction;
import de.rougemaster.dungeon.game.fight.Fight;
import de.rougemaster.dungeon.game.fight.FightManager;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.doNothingGameCommand;
import de.rougemaster.dungeon.game.gameCommand.CharacterCommands.fleeGameCommand;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilFlameSwordAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilPlayerKillerAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilSpikeShieldCommand;
import de.rougemaster.dungeon.game.gameCommand.devilCommands.devilSwordAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.attackUsingEquipmentCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.defendingPlayerCommand;
import de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands.useItemInFightCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonBoneAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonBonesplosionAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.skeletonCommands.skeletonSwordAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieBiteAttackCommand;
import de.rougemaster.dungeon.game.gameCommand.zombieCommands.zombieClawAttackCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnManager {
    FightManager fightManager;
    PlayableCharacter character;

    Map<Character, GameCommand> commandMap;
    Map<FightManager, List<Character>> fightList;


    public TurnManager() {
        this.commandMap = new HashMap<>();
        this.fightManager= new FightManager();
    }

    public FightManager getFightManager() {
        return fightManager;
    }

    public void setFightManager(FightManager fightManager) {
        this.fightManager = fightManager;
    }

    public PlayableCharacter getCharacter() {
        return character;
    }

    public Map<Character, GameCommand> getCommandMap() {
        return commandMap;
    }

    public Map<FightManager, List<Character>> getFightList() {
        return fightList;
    }

    public void executeTurn() {
        handleCharacterInput();
    }


    /**
     * Iterates through the command map and executes the commands.
     */
    private Map<Character, GameCommand> startTurn() {
        //COPY COMMAND MAP
        Map<Character, GameCommand> copyCommandMap =  new HashMap<>(commandMap);

        //FLEE COMMAND
        for (Character character : copyCommandMap.keySet()){
            if(character.equals("flee")){
                fightManager.endFight(fightManager.getFight(character));
            }
        }

        //FIGHTMANAGER SET ACTIONS
        List<Character> allCharactersInFights = fightManager.getAllCharactersInFights();
        setAllFightActions(allCharactersInFights, copyCommandMap);
        fightManager.executeAllTurns();

        //DO NOTHING FOR ALL IN FIGHTS
        for (Character character : copyCommandMap.keySet()) {
            if (allCharactersInFights.contains(character)) {
                doNothingGameCommand newCommand = new doNothingGameCommand(character);
                copyCommandMap.remove(character);
            }
        }
        return copyCommandMap;
    }

    private void setAllFightActions(List<Character> allFighters, Map<Character, GameCommand> copyCommandMap) {
        for (Map.Entry<Character, GameCommand> entry : copyCommandMap.entrySet()) {
            Character currentCharacter = entry.getKey();
            GameCommand command = entry.getValue();

            if (allFighters.contains(currentCharacter)) {
                Fight fight = fightManager.getFight(currentCharacter);
                Character combatantOne = fight.getCombatantOne();
                Character combatantTwo = fight.getCombatantTwo();
                if (currentCharacter == combatantOne) {
                    handleFightAction(fight, combatantOne, command);
                } else {
                    handleFightAction(fight, combatantTwo, command);
                }
            }

        }
    }

    /**
     * Handles the fight actions of a character.
     * @param fight the fight
     * @param combatant the combatant
     * @param command the command
     */
    private void handleFightAction(Fight fight, Character combatant, GameCommand command) {
        if (command instanceof doNothingGameCommand) {
            fight.setCombatantAction(combatant, CombatAction.DO_NOTHING);
        } else if (command instanceof attackUsingEquipmentCommand) {
            fight.setCombatantAction(combatant, CombatAction.ATTACK);
        } else if (command instanceof defendingPlayerCommand) {
            fight.setCombatantAction(combatant, CombatAction.DEFEND);
        } else if (command instanceof fleeGameCommand) {
            fight.setCombatantAction(combatant, CombatAction.FLEE);
            fleeGameCommand fleeCommand = (fleeGameCommand) command;
            combatant.flee(fleeCommand.room);
            fightManager.endFight(fightManager.getFight(combatant));
        } else if (command instanceof useItemInFightCommand) {
            fight.setCombatantAction(combatant, CombatAction.USE_ITEM);
        } else if (command instanceof skeletonSwordAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.SKELETON_SWORD_ATTACK);
        } else if (command instanceof skeletonBoneAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.SKELETON_BONE_ATTACK);
        } else if (command instanceof skeletonBonesplosionAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.SKELETON_BONESPLOSION_ATTACK);
        } else if (command instanceof zombieClawAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.ZOMBIE_CLAW_ATTACK);
        } else if (command instanceof zombieBiteAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.ZOMBIE_BITE_ATTACK);
        } else if (command instanceof devilFlameSwordAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.DEVIL_FLAME_SWORD_ATTACK);
        } else if (command instanceof devilSpikeShieldCommand) {
            fight.setCombatantAction(combatant, CombatAction.DEVIL_SPIKE_SHIELD);
        } else if (command instanceof devilPlayerKillerAttackCommand) {
            fight.setCombatantAction(combatant, CombatAction.DEVIL_PLAYER_KILLER_ATTACK);
        } else if(command instanceof devilSwordAttackCommand){
            fight.setCombatantAction(combatant, CombatAction.DEVIL_SWORD_ATTACK);
        } else {
            fight.setCombatantAction(combatant, CombatAction.DO_NOTHING);
        }
    }


    /**
     * Iterates through the command map and executes the commands.
     */
    private void handleCharacterInput() {
        Map<Character, GameCommand> copyCommandMap = startTurn();
        for (Map.Entry<Character, GameCommand> entry : copyCommandMap.entrySet()) {
            GameCommand command = entry.getValue();
            command.execute();
        }

        // Iteration durch die Map, um Charactere im gleichen Raum zu finden
        for (Character character1 : copyCommandMap.keySet()) {
            Room room1 = character1.getCurrentRoom();

            for (Character character2: copyCommandMap.keySet()) {
                Room room2 = character2.getCurrentRoom();

                List<Character> fightingCharacters = fightManager.getAllCharactersInFights();
                if (fightingCharacters.contains(character1) || fightingCharacters.contains(character2)) {
                    continue;
                }

                if(room1 != null && room2 != null && (room1.equals(room2) && !character1.equals(character2))) {
                        fightManager.startFight(character1, character2);
                }
            }
        }

        cleanCommandMap();
    }

    private void cleanCommandMap (){
        for (Character character : commandMap.keySet()) {
            doNothingGameCommand newCommand = new doNothingGameCommand(character);
            commandMap.put(character, newCommand);
        }
    }

    public void setCharacterTurn(Character character, GameCommand command) {
        commandMap.put(character, command);
    }

    public void reset(){
        this.commandMap=null;
        this.fightManager=null;
    }

}