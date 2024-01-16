package de.rougemaster.dungeon.character.enemyCharacter.zombie;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Zombie extends EnemyCharacter {

    //TODO: I dont know if this kind of inheritance works. (Check EnemyCharacter dropExperience)
    private static final int baseExpDropAmount = 10;
    private static final int biteBonusDamage = 5;

    private ZombieState state;

    public Zombie(int dangerLvl){
        if(dangerLvl <= 0) {
            dangerLvl = 1;
        }
        if(dangerLvl > 8) {
            dangerLvl = 8;
        }

        if(dangerLvl < 5){
            this.attack= 2;
            this.defense=3;
            this.maxHp=3;
            this.hp=3;
            for(int i = dangerLvl; i>1; i--){
                attack += 3;
                defense += 4;
                maxHp +=  4;
                hp += 4;
            }
        }

        if(dangerLvl > 5){
            this.attack= 14;
            this.defense=19;
            this.maxHp=19;
            this.hp=19;
            for(int i = dangerLvl; i>1; i--){
                attack += 5;
                defense += 5;
                maxHp +=  6;
                hp += 6;
            }
        }
    }

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
