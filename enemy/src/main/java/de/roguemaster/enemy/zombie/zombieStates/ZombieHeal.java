package de.roguemaster.enemy.zombie.zombieStates;

import de.roguemaster.enemy.zombie.Zombie;
import de.roguemaster.enemy.zombie.ZombieState;

public class ZombieHeal implements ZombieState {
    @Override
    public ZombieState fight(Zombie zombie) {
        return new ZombieBite();
    }

    @Override
    public ZombieState roam() {
        return new ZombieRoamWait();
    }
}
