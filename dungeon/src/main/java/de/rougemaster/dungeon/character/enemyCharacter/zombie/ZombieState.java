package de.rougemaster.dungeon.character.enemyCharacter.zombie;

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
}
