package de.rougemaster.dungeon.character.enemyCharacter;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;

import java.util.Random;

public class EnemyCharacterFactory {
    private static final int SKELETON_MAX_LEVEL = 13;
    private static final int ZOMBIE_MAX_LEVEL = 8;

    /**
     * Creates an enemy with the given strength
     * @param enemyTyp the corresponding enemyTyp as String
     * @return the created enemy
     */
    public EnemyCharacter createEnemy(EnemyTyp enemyTyp, int averageLevel){
        return switch (enemyTyp) {
            case Zombie -> new Zombie(calculateRandomEnemyLevel(averageLevel, ZOMBIE_MAX_LEVEL));
            case Skeleton -> new Skeleton(calculateRandomEnemyLevel(averageLevel, SKELETON_MAX_LEVEL));
            case Devil -> new Devil();
        };
    }

    private static int calculateRandomEnemyLevel(int averageLevel ,int maxLevel) {
        int randomOffset = new Random().nextInt(5)-2;
        int enemyLevel = Math.min(averageLevel + randomOffset, maxLevel);
        return Math.max(enemyLevel, 1);
    }

    //Enum for the different enemy types
    public enum EnemyTyp{
        Zombie,
        Skeleton,
        Devil
    }
}
