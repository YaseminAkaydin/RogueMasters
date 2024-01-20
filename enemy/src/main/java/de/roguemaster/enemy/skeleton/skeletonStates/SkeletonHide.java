package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

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
