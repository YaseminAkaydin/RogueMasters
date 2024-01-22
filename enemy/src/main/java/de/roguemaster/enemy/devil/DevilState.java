package de.roguemaster.enemy.devil;

import de.roguemaster.enemy.CommandHolder;

public interface DevilState {

    /**
     * Returns Devil next action in form of a State.
     * This gets triggered every Round that the Devil is inside a fight.
     * @param devil the Devil that is doing the action.
     * @return The next Action as a State.
     */
    public DevilState fight(Devil devil);

    /**
     * Returns Devil next action in form of a State.
     * This gets triggered every Round that the Devil is NOT in a fight.
     * @return The next Action as a State.
     */
    public DevilState roam();

    /**
     * Returns the current Action of the Devil as a CommandHolder.
     * @return The current Action of the Devil as a CommandHolder.
     */
    public CommandHolder createCommand();
}
