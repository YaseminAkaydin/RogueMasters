package de.rougemaster.dungeon.character.enemyCharacter.zombie;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Zombie extends EnemyCharacter {

    //TODO: I dont know if this kind of inheritance works. (Check EnemyCharacter dropExperience)
    private static final int baseExpDropAmount = 10;
    private static final int biteBonusDamage = 5;

    private ZombieState state;

    /**
     * Zombie does a "claw"-attack against a character.
     * @param character enemy character
     */
    public void clawAttack(Character character) {
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if zombie and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }


        character.setHp(character.getHp()-(this.attack - character.getDefense()));
    }

    /**
     * Zombie does a "bite"-attack against a character
     * @param character enemy character
     */
    public void biteAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if zombie and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }

        character.setHp(character.getHp()-(this.attack + biteBonusDamage - character.getDefense()));
    }

    /**
     * Gives you the next action of zombie inside a fight
     * @return the action in form of a ZombieState
     */
    public ZombieState getFightAction(){
        state = state.fight(this);
        return state;
    }

    /**
     * Gives you the next Action of Zombie that is roaming
     * @return the Action in form of a ZombieState
     */
    public ZombieState getRoamAction(){
        state = state.roam();
        return state;
    }

}
