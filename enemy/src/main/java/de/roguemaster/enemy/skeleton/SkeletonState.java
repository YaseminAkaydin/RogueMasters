package de.roguemaster.enemy.skeleton;

import de.roguemaster.enemy.CommandHolder;

public interface SkeletonState {
    /**
     * Returns Skeleton next action in form of a State.
     * This gets triggered every Round that the Devil is inside a fight.
     * @param skeleton the skeleton that is doing the action.
     * @return The next Action as a State.
     */
    public SkeletonState fight(Skeleton skeleton);

    /**
     * Returns Skeleton next action in form of a State.
     * This gets triggered every Round that the Devil is NOT in a fight.
     * @return The next Action as a State.
     */
    public SkeletonState roam();

    /**
     * Returns the current Action of the Skeleton as a CommandHolder.
     * @return The current Action of the Skeleton as a CommandHolder.
     */
    public CommandHolder createCommand();
}
