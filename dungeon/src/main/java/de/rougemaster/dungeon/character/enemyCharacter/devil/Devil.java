package de.rougemaster.dungeon.character.enemyCharacter.devil;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Devil extends EnemyCharacter {

    private DevilState state;
    private static final int flameDamage = 50;

    public Devil() {
        super(0);
        int dangerLvl = 15;

        this.attack= 100;
        this.defense=80;
        this.maxHp=150;
        this.hp=150;
        for(int i = dangerLvl; i>1; i--){
            attack += 10;
            defense += 5;
        }
    }

    /**
     * Devil does a "flameSword"-attack against a character.
     * @param character enemy character
     */
    public void flameSwordAttack(Character character){
        character.setHp(character.getHp()-(this.attack + flameDamage - character.getDefense()));
    }

    /**
     * Devil does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void swordAttack(Character character){
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
        this.defense *= 2;
    }

    /**
     * Devil does a special-attack against a character.
     * If the enemy character does a move it kills them automatically
     * @param character enemy character
     */
    public void playerKillerAttack(Character character){
        character.setHp(0);
    }

    public void calm(){
        this.attack= 100;
        this.defense= 65;
        this.maxHp=150;
        this.hp=150;
    }
    public void rage(){
        this.attack=105;
        this.defense=70;
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
