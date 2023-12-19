package de.rougemaster.dungeon.character.enemyCharacter.skeleton.skeletonStates;

import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.SkeletonState;

public class SkeletonAttack implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        if(skeleton.getHp() >= (skeleton.getMaxHp()/10)){
            return new SkeletonPrepareExplosion();
        }
        return new SkeletonDefend();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonWait();
    }
}
