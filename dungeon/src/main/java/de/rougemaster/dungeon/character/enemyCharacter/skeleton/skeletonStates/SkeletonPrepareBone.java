package de.rougemaster.dungeon.character.enemyCharacter.skeleton.skeletonStates;

import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.SkeletonState;

public class SkeletonPrepareBone implements SkeletonState {

    @Override
    public SkeletonState fight(Skeleton skeleton) {
        return new SkeletonBoneAttack();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonWait();
    }
}
