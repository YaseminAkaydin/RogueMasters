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
}
