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
     *
     * @param fight fight to be ended.
     */
    public void endFight(Fight fight) {
        Character combatantOne = fight.getCombatantOne();
        Character combatantTwo = fight.getCombatantTwo();
        int enemyLevel = 0;
        if (combatantOne instanceof EnemyCharacter) {
            ((EnemyCharacter) combatantOne).dropExperience((PlayableCharacter) combatantTwo);
            enemyLevel = ((EnemyCharacter) combatantOne).getDangerLevel();

        } else {
            if(combatantTwo instanceof EnemyCharacter) {
                ((EnemyCharacter) combatantTwo).dropExperience((PlayableCharacter) combatantOne);
                enemyLevel = ((EnemyCharacter) combatantTwo).getDangerLevel();
            }
        }
        int itemLevel = 1;
        switch (enemyLevel) {
            case 2:
                itemLevel = 2;
                break;
            case 3, 4:
                itemLevel = 3;
                break;
            case 5, 6:
                itemLevel = 4;
                break;
            case 7, 8:
                itemLevel = 5;
                break;
        }
        Room room;
        if(combatantOne.getHp()<=0){
            room = combatantTwo.getCurrentRoom();
        }else{
            room = combatantOne.getCurrentRoom();
        }

        room.setItem(ItemFactory.createRandomItem(itemLevel));
        this.activeCombatants.remove(combatantOne);
        this.activeCombatants.remove(combatantTwo);
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
     *
     * @return a List of Character objects who are currently in fights.
     */
    public List<Character> getAllCharactersInFights() {
        List<Character> charactersInFights = new ArrayList<>();
        for (Map.Entry<Character, Fight> entry : activeCombatants.entrySet()) {
            charactersInFights.add(entry.getKey());
        }
        return charactersInFights;
    }

    public List<Fight> getActiveFights() {
        return activeFights;
    }
}
