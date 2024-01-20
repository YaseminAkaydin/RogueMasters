package de.roguemaster.enemy.devil;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;

public class Devil extends Enemy {
    DevilState state;

    @Override
    public CommandHolder getNextTurn(boolean isFighting){
        if(isFighting) {
            state.fight(this);
            return state.createCommand();
        }else {
            state.roam();
            return state.createCommand();
        }
    }
}
