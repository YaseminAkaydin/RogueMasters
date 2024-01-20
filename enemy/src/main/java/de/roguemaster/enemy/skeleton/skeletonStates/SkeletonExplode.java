package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

public class SkeletonExplode implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        return new SkeletonExplode();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonExplode();
    }
}
