package de.roguemaster.enemy.zombie;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.enemy.zombie.zombieStates.ZombieSearch;
import de.roguemaster.facade.EnemyTyp;

public class Zombie extends Enemy {
    private ZombieState state;

    public Zombie() {
        this.state = new ZombieSearch();
    }

    @Override
    public CommandHolder getNextTurn(boolean isFighting) {
        if(isFighting) {
            state = state.fight(this);
        }else {
            state = state.roam();
        }
        return state.createCommand();
    }

    @Override
    public EnemyTyp getTyp() {
        return EnemyTyp.Zombie;
    }
}
