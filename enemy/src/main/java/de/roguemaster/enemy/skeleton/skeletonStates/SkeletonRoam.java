package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

public class SkeletonRoam implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        return new SkeletonAttack();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonHide();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("roam", "");
    }
}
