package de.roguemaster.enemy.zombie;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.Enemy;
import de.roguemaster.enemy.zombie.zombieStates.ZombieSearch;

public class Zombie extends Enemy {
    private ZombieState state;

    public Zombie() {
        this.state = new ZombieSearch();
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
