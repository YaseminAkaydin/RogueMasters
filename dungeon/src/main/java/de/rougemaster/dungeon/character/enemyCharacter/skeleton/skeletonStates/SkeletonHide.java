package de.rougemaster.dungeon.character.enemyCharacter.skeleton.skeletonStates;

import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.SkeletonState;

public class SkeletonHide implements SkeletonState {

    @Override
    public SkeletonState fight(Skeleton skeleton) {
        return new SkeletonAttack();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonHide();
    }
}
