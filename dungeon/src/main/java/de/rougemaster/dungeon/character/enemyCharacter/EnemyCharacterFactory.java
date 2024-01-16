package de.rougemaster.dungeon.character.enemyCharacter;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;

public class EnemyCharacterFactory {

    /**
     * Creates an enemy with the given strength
     * @param enemyTyp the corresponding enemyTyp as String
     * @param dangerLevel the dangerLevel of the Enemy
     * @return the created enemy
     */
    public EnemyCharacter createEnemy(EnemyTyp enemyTyp, int dangerLevel){
        switch (enemyTyp){
            case Zombie:
                return new Zombie(dangerLevel);
            case Skeleton:
                return new Skeleton();
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
