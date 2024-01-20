package de.roguemaster.enemy.skeleton.skeletonStates;


import de.roguemaster.enemy.skeleton.Skeleton;
import de.roguemaster.enemy.skeleton.SkeletonState;

public class SkeletonBoneAttack implements SkeletonState {
    @Override
    public SkeletonState fight(Skeleton skeleton) {
        if(skeleton.getHp() >= (skeleton.getMaxHp()/10)){
            return new SkeletonPrepareExplosion();
        }
        return new SkeletonAttack();
    }

    @Override
    public SkeletonState roam() {
        return new SkeletonWait();
    }
}
