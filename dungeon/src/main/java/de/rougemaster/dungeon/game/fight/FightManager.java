package de.rougemaster.dungeon.game.fight;

import de.rougemaster.dungeon.character.Character;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FightManager {

    private List<Fight> activeFights;
    private Map<Character, Fight> activeCombatants;

    public FightManager(){
        this.activeFights = new ArrayList<>();
        this.activeCombatants = new HashMap<>();

    }

    /**
     * Starts a new fight. Should be called by TurnManager.
     * @param combatantOne one of the combatants. Should be the player
     * @param combatantTwo one of the combatants
     */
    public void startFight(Character combatantOne, Character combatantTwo){
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
    public void endFight(Fight fight){
        //TODO: Distribute rewards
        this.activeFights.remove(fight);
    }

    /**
     * Executes the next turn for all fights. Should be called once every 5 seconds by TurnManager.
     */
    public void executeAllTurns(){
        for (Fight fight: this.activeFights) {
            fight.executeTurn();
        }
    }

    /**
     * Returns the fight that the specified combatant is currently engaged in.
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
        List<Character> charactersInFights = new ArrayList<>();
        for (Map.Entry<Character, Fight> entry : activeCombatants.entrySet()) {
            charactersInFights.add(entry.getKey());
        }
        return charactersInFights;
    }
}
