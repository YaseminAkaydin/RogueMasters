package de.roguemaster.enemy.skeleton;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.enemy.skeleton.skeletonStates.SkeletonHide;

public class Skeleton extends Enemy {
    SkeletonState state;
    public Skeleton() {
        this.state = new SkeletonHide();
    }
    @Override
    public CommandHolder getNextTurn(boolean isFighting) {
        if(isFighting) {
            state.fight(this);
            return state.createCommand();
        }else {
            state.roam();
            return state.createCommand();
        }
    }
}
