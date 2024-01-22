package de.rougemaster.dungeon.character.enemyCharacter;
import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;


public abstract class EnemyCharacter extends Character {
    protected int killCount;
    protected int baseExpDropAmount;
    protected int dangerLevel;

    public EnemyCharacter(int baseExpDropAmount){
        this.baseExpDropAmount = baseExpDropAmount;
    }

     @Override
    public int getEXP() {
        return (int) Math.pow(dangerLevel, 1.5)*baseExpDropAmount;
    }

    public int getLevel(){
        return dangerLevel;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }


}
