package de.roguemaster.enemy.devil;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.enemy.devil.devilState.DevilSleep;
import de.roguemaster.facade.EnemyTyp;

public class Devil extends Enemy {
    DevilState state;

    public Devil() {
        this.state = new DevilSleep();
    }

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

    @Override
    public EnemyTyp getTyp() {
        return EnemyTyp.Devil;
    }
}
