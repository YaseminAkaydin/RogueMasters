package de.rougemaster.dungeon.character.enemyCharacter;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;

public class EnemyCharacterFactory {

    /**
     * Creates an enemy with the given strength
     * @param enemyTyp the corresponding enemyTyp as String
     * @return the created enemy
     */
    public EnemyCharacter createEnemy(EnemyTyp enemyTyp){
        int dangerLevel;
        switch (enemyTyp){
            case Zombie:
                // Dangerlevel random number between 1 and 8
                dangerLevel =1;
                //dangerLevel = (int) (Math.random() * 8 + 1);
                return new Zombie(dangerLevel);
            case Skeleton:
                dangerLevel =1;
                // Dangerlevel random number between 1 and 13
                //dangerLevel = (int) (Math.random() * 13 + 1);
                return new Skeleton(dangerLevel);
            case Devil:
                return new Devil();
        }
        throw new IllegalArgumentException("Not Implemented yet");
    }

    //Enum for the different enemy types
    public enum EnemyTyp{
        Zombie,
        Skeleton,
        Devil
    }
}
