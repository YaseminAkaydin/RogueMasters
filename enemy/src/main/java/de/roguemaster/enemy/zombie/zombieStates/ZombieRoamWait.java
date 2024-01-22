package de.roguemaster.enemy.zombie.zombieStates;


import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.zombie.Zombie;
import de.roguemaster.enemy.zombie.ZombieState;

public class ZombieRoamWait implements ZombieState {
    @Override
    public ZombieState fight(Zombie zombie) {
        return new ZombieAttack();
    }

    @Override
    public ZombieState roam() {
        return new ZombieSearch();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("doNothing", "");
    }
}
