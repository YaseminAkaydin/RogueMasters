package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

public class SkeletonPrepareExplosion implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        return new SkeletonExplode();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonExplode();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("doNothing", "");
    }
}
