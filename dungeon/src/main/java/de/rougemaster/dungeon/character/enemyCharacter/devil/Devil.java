package de.rougemaster.dungeon.character.enemyCharacter.devil;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.EnemyCharacter;

public class Devil extends EnemyCharacter {

    private static final int flameDamage = 10;

    public Devil() {
        super(0);
        id = ++idCounter;
        this.dangerLevel = 15;

        this.attack=85;
        this.defense=65;
        this.maxHp=150;
        this.hp=150;
    }

    /**
     * Devil does a "flameSword"-attack against a character.
     * @param character enemy character
     */
    public void flameSwordAttack(Character character){
        int netDamage = this.attack + flameDamage - character.getDefense();
        character.setHp(character.getHp()- Math.max(0, netDamage));
    }

    /**
     * Devil does a "sword"-attack against a character.
     * @param character enemy character
     */
    public void swordAttack(Character character){
        int netDamage = this.attack - character.getDefense();
        character.setHp(character.getHp()-Math.max(0, netDamage));
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
}
