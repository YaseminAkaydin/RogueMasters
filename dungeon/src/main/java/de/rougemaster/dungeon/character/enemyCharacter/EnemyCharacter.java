package de.rougemaster.dungeon.character.enemyCharacter;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;

public abstract class EnemyCharacter extends Character {
    protected int killCount;
    protected static int baseExpDropAmount;
    protected int dangerLevel;

    /**
     * Drops the given Amount of exp to the given PlayerCharacter.
     * @param character the PlayerCharacter that has slain the EnemyCharacter.
     */
    public void dropExperience(PlayableCharacter character) {
        if(character == null){
            throw new IllegalArgumentException("Character can't be null");
        }
        int amountOfExperience = (int) Math.pow(baseExpDropAmount, 1.5);
        character.gainExperience(amountOfExperience);
    }

    public int getDangerLevel() {
        return dangerLevel;
    }
}
