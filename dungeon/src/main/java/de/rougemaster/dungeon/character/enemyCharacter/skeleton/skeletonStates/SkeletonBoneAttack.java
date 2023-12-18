package de.rougemaster.dungeon.character.enemyCharacter.skeleton.skeletonStates;

import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.SkeletonState;

public class SkeletonBoneAttack implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        if(skeleton.getHp() >= (skeleton.getMaxHp()/2)){
            return new SkeletonPrepareExplosion();
        }
        return new SkeletonAttack();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonWait();
    }
}
