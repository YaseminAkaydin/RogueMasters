package de.rougemaster.dungeon.game.fight;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.ItemFactory;
import de.rougemaster.dungeon.dungeon.Room;

import java.util.*;

public class FightManager {
    private List<Fight> activeFights;
    private Map<Character, Fight> activeCombatants;

    public FightManager() {
        this.activeFights = new ArrayList<>();
        this.activeCombatants = new HashMap<>();

    }

    /**
     * Starts a new fight. Should be called by TurnManager.
     *
     * @param combatantOne one of the combatants. Should be the player
     * @param combatantTwo one of the combatants
     */
    public void startFight(Character combatantOne, Character combatantTwo) {
        if (!this.activeCombatants.containsKey(combatantOne) && !this.activeCombatants.containsKey(combatantTwo)) {
            Fight newFight = new Fight(combatantOne, combatantTwo);
            this.activeFights.add(newFight);
            activeCombatants.put(combatantOne, newFight);
            activeCombatants.put(combatantTwo, newFight);
        }
    }

    /**
     * Ends a currently running fight and distributes the rewards.
     * @param fight fight to be ended.
     */
    public void endFight(Fight fight) {
        //GETTER FROM FIGHT;
        Character winner = fight.getCombatantOne().getHp() <= 0 ? fight.getCombatantTwo() : fight.getCombatantOne();
        Character loser = fight.getCombatantOne().getHp() <= 0 ? fight.getCombatantOne() : fight.getCombatantTwo();

        if(!(winner instanceof PlayableCharacter playerWinner)){
            return;
        }

        playerWinner.gainExperience(loser.getEXP());

        int itemLevel = switch (loser.getLevel()) {
            case 2,3,4 -> 2;
            case 5,6,7 -> 3;
            case 8,9,10 -> 4;
            case 11,12,13 -> 5;
            default -> 1;
        };

        playerWinner.gainItem(ItemFactory.createRandomItem(itemLevel));
        stopFight(fight);
    }

    /**
     * Stops a fight.
     * @param fight the fight to be stopped.
     */
    public void stopFight(Fight fight) {
        this.activeCombatants.remove(fight.getCombatantOne());
        this.activeCombatants.remove(fight.getCombatantTwo());
        this.activeFights.remove(fight);
    }

    /**
     * Executes the next turn for all fights. Should be called once every 5 seconds by TurnManager.
     */
    public void executeAllTurns() {
        Iterator<Fight> iterator = this.activeFights.iterator();
        while (iterator.hasNext()) {
            Fight fight = iterator.next();
            fight.executeTurn();
            if (fight.getCombatantOne().getHp() <= 0 || fight.getCombatantTwo().getHp() <= 0) {
                iterator.remove();
                endFight(fight);
            }
        }
    }

    /**
     * Returns the fight that the specified combatant is currently engaged in.
     *
     * @param combatant the character whose fight is to be retrieved.
     * @return the Fight object in which the combatant is engaged, or null if the combatant is not in a fight.
     */
    public Fight getFight(Character combatant) {
        return activeCombatants.get(combatant);
    }

    /**
     * Returns a list of all characters currently involved in fights.
     * @return a List of Character objects who are currently in fights.
     */
    public List<Character> getAllCharactersInFights() {
        return activeCombatants.keySet().stream().toList();
    }

    public boolean isCharacterInFight(Character character) {
        return getAllCharactersInFights().contains(character);
    }

    public List<Fight> getActiveFights() {
        return activeFights;
    }
}
