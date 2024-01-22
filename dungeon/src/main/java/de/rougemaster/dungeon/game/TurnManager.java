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
    private void handleCharacterInput() {
        //COPY COMMAND MAP
        Map<Character, GameCommand> copyCommandMap = startTurn();

        //EXECUTE COMMANDS
        for (GameCommand gameCommand : copyCommandMap.values()) {
            gameCommand.execute();
        }

        //HANDLE FIGHTS
        setAllFightActions(copyCommandMap);
        fightManager.executeAllTurns();

        //Remove all character that are already dead or already have a fight
        for (Character character : commandMap.keySet()) {
            if (character.getHp() <= 0 || fightManager.getAllCharactersInFights().contains(character)) {
                copyCommandMap.remove(character);
            }
        }

        for (Character character1 : copyCommandMap.keySet()) {
            Room room1 = character1.getCurrentRoom();
            for (Character character2: copyCommandMap.keySet()) {
                Room room2 = character2.getCurrentRoom();

                if(character1.equals(character2)){
                    continue;
                }

                //Check if characters are in the same room
                if(room1 != null && room2 != null && (room1.equals(room2) && !character1.equals(character2))) {
                    fightManager.startFight(character1, character2);
                }
            }
        }

        //CLEAN COMMAND MAP
        cleanCommandMap();
    }


    /**
     * Iterates through the command map and executes the commands.
     */
    private Map<Character, GameCommand> startTurn() {
        // Copy Command map
        Map<Character, GameCommand> copyCommandMap =  new HashMap<>(commandMap);

        //EXECUTE FLEE
        for (Map.Entry<Character, GameCommand> entry : copyCommandMap.entrySet()) {
            Character character = entry.getKey();
            GameCommand command = entry.getValue();

            if (command instanceof fleeGameCommand fleeCommand) {
                //Check if Character is in a fight
                if (!fightManager.getAllCharactersInFights().contains(character)) {
                    continue;
                }

                fleeCommand.execute();

                //Stop fight and remove commands from command map if character flees
                Fight fight = fightManager.getFight(character);
                commandMap.remove(fight.getCombatantTwo());
                commandMap.remove(fight.getCombatantOne());
                fightManager.endFight(fight);
            }
        }

        return copyCommandMap;
    }

    private void setAllFightActions(Map<Character, GameCommand> copyCommandMap) {
        List<Character> allFighters = fightManager.getAllCharactersInFights();

        //SET FIGHT ACTIONS
        for (Map.Entry<Character, GameCommand> entry : copyCommandMap.entrySet()) {
            Character character = entry.getKey();
            GameCommand command = entry.getValue();

            //Check if character is in a fight
            if (allFighters.contains(character)) {

                Fight fight = fightManager.getFight(character);
                Character combatantOne = fight.getCombatantOne();
                Character combatantTwo = fight.getCombatantTwo();

                Character targetCombatant = (character == combatantOne) ? combatantOne : combatantTwo;
                handleFightAction(fight, targetCombatant, command);
            }
        }
    }

    /**
     * Handles the fight actions of a character. Translates and sets the command to a combat action in FightManager.
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
     * Sets all commands in the command map to do nothing.
     */
    private void cleanCommandMap (){
        for (Character character : commandMap.keySet()) {
            doNothingGameCommand newCommand = new doNothingGameCommand(character);
            commandMap.put(character, newCommand);
        }
    }

    public void setCharacterTurn(Character character, GameCommand command) {
        commandMap.put(character, command);
    }

    /**
     * Resets the turn manager.
     */
    public void reset(){
        this.commandMap=null;
        this.fightManager=null;
    }

}