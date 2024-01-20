package de.roguemaster.enemy.zombie.zombieStates;

import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.zombie.Zombie;
import de.roguemaster.enemy.zombie.ZombieState;

public class ZombieAttack implements ZombieState {
    @Override
    public ZombieState fight(Zombie zombie) {
        return new ZombieFightWait();
    }

    @Override
    public ZombieState roam() {
        return new ZombieHeal();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("clawAttack", "");
    }
}
