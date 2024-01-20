package de.roguemaster.enemy.zombie;

import de.roguemaster.enemy.CommandHolder;

public interface ZombieState {
    /**
     * Returns Zombies next action inform of a State.
     * This gets triggered every Round that the Zombie is inside a fight.
     * @param zombie the Zombie that is doing the action.
     * @return The next Action as a State.
     */
    public ZombieState fight(Zombie zombie);

    /**
     * Returns Zombies next action inform of a State.
     * This gets triggered every Round that the Zombie is NOT in a fight.
     * @return The next Action as a State.
     */
    public ZombieState roam();

    /**
     * Returns the current Action of the Zombie as a CommandHolder.
     * @return The current Action of the Zombie as a CommandHolder.
     */
    public CommandHolder createCommand();
}
