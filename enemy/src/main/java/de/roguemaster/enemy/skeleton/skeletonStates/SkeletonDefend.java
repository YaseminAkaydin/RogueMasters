package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

public class SkeletonDefend implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        if(skeleton.getHp() >= (skeleton.getMaxHp()/10)){
            return new SkeletonPrepareExplosion();
        }
        return new SkeletonPrepareBone();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonWait();
    }
}
