package de.rougemaster.dungeon.character.enemyCharacter.skeleton;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Skeleton extends EnemyCharacter {

    SkeletonState state;

    private static final int boneAttackBonusDamage = 5;

    /**
     * Skeleton does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void swordAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if skeleton and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }

        /*TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */

        character.setHp(character.getHp()-(this.attack - character.getDefense()));
    }

    /**
     * Increases the defense of the Skeleton for a round in a fight
     */
    public void defend() {
        /*
         * TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */
        this.defense *= 2;
    }

    /**
     * Skeleton does a "bone"-attack against a character.
     * @param character enemy character
     */
    public void boneAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if skeleton and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }

        /*TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */

        character.setHp(character.getHp()-(this.attack + boneAttackBonusDamage - character.getDefense()));
    }

    /**
     * Skeleton does a "bonesplosion"-attack against a character.
     * @param character enemy character
     */
    public void bonesplosionAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if skeleton and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }

        /*TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */

        character.setHp(character.getHp()-((int)(this.attack * 2.5) - character.getDefense()));
    }

    /**
     * Gives you the next action of Skeleton inside a fight
     * @return the action in form of a SkeletonState
     */
    public SkeletonState getFightAction(){
        state = state.fight(this);
        return state;
    }

    /**
     * Gives you the next Action of Skeleton that is roaming
     * @return the Action in form of a SkeletonState
     */
    public SkeletonState getRoamAction(){
        state = state.roam();
        return state;
    }
}
