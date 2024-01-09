package de.rougemaster.dungeon.character.enemyCharacter.devil;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Devil extends EnemyCharacter {

    private DevilState state;

    /**
     * Devil does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void flameSwordAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if devil and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }


        /*TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */

        character.setHp(character.getHp()-(this.attack - character.getDefense()));
    }

    /**
     * Increases the defense of the Devil for a round in a fight.
     */
    public void defend() {
        this.defense *= 2;
    }

    /**
     * Lowers the defense of the devil for a round in a fight.
     * WARNING: Only called by FightManager, which ensures defending is handled correctly.
     */
    public void stopDefending(){
        this.defense /= 2;
    }

    /**
     * Increases the defense of the Devil for a round in a fight
     */
    public void SpikeShield() {
        /*
         * TODO: The Following implementation is a example and shouldn't be used for the final product.
         *       There should be a FightManager to handle this. */
        this.defense *= 2;
        //TODO: On attack damage the attacker
    }

    /**
     * Devil does a special-attack against a character.
     * If the enemy character does a move it kills them automatically
     * @param character enemy character
     */
    public void playerKillerAttack(Character character){
        //TODO: character could be changed to the type PlayableCharacter (useful?)
        //TODO: check if devil and character are fighting.

        //Check if character is in the same Room as PlayerCharacter
        if(this.currentRoom.equals(character.getCurrentRoom())){
            return;
        }

        //TODO: Check if Character does a move that is not nothing
        character.setHp(0);
    }

    /**
     * Gives you the next action of Devil inside a fight
     * @return the action in form of a DevilState
     */
    public DevilState getFightAction(){
        state = state.fight(this);
        return state;
    }

    /**
     * Gives you the next Action of Devil that is roaming
     * @return the Action in form of a DevilState
     */
    public DevilState getRoamAction(){
        state = state.roam();
        return state;
    }

}
