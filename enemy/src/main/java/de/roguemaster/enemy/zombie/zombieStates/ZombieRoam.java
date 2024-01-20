package de.roguemaster.enemy.zombie.zombieStates;


import de.roguemaster.enemy.zombie.Zombie;
import de.roguemaster.enemy.zombie.ZombieState;

public class ZombieRoam implements ZombieState {
    @Override
    public ZombieState fight(Zombie zombie) {
        return new ZombieAttack();
    }

    @Override
    public ZombieState roam() {
        return new ZombieRoamWait();
    }
}
