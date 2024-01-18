package de.rougemaster.dungeon.character.enemyCharacter.skeleton;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Skeleton extends EnemyCharacter {

    SkeletonState state;
    private static final int baseExpDropAmount = 20;

    private static final int boneAttackBonusDamage = 5;

    public Skeleton(int dangerLevel) {
        super(baseExpDropAmount);
        this.dangerLevel = dangerLevel;

        if(dangerLevel <= 0) {
            dangerLevel = 1;
        }
        if(dangerLevel > 8) {
            dangerLevel = 8;
        }

        if(dangerLevel < 5){
            this.attack= 2;
            this.defense=3;
            this.maxHp=3;
            this.hp=3;
            for(int i = dangerLevel; i>1; i--){
                attack += 3;
                defense += 4;
                maxHp +=  4;
                hp += 4;
            }
        }

        if(dangerLevel >= 5){
            this.attack= 14;
            this.defense=19;
            this.maxHp=19;
            this.hp=19;
            for(int i = dangerLevel; i>1; i--){
                attack += 5;
                defense += 5;
                maxHp +=  6;
                hp += 6;
            }
        }

    }

    /**
     * Skeleton does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void swordAttack(Character character){
        character.setHp(character.getHp()-(this.attack - character.getDefense()));
    }

    /**
     * Increases the defense of the Skeleton for a round in a fight
     */
    public void defend() {
        this.defense *= 2;
    }

    /**
     * Lowers the defense of the skeleton for a round in a fight.
     * WARNING: Only called by FightManager, which ensures defending is handled correctly.
     */
    public void stopDefending(){
        this.defense /= 2;
    }

    /**
     * Skeleton does a "bone"-attack against a character.
     * @param character enemy character
     */
    public void boneAttack(Character character){
        character.setHp(character.getHp()-(this.attack + boneAttackBonusDamage - character.getDefense()));
    }

    /**
     * Skeleton does a "bonesplosion"-attack against a character.
     * @param character enemy character
     */
    public void bonesplosionAttack(Character character){
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
