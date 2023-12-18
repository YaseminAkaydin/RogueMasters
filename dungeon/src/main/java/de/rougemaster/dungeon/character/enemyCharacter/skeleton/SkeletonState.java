package de.rougemaster.dungeon.character.enemyCharacter.skeleton;

public interface SkeletonState {
    /**
     * Returns Skeleton next action in form of a State.
     * This gets triggered every Round that the Zombie is inside a fight.
     * @param skeleton the skeleton that is doing the action.
     * @return The next Action as a State.
     */
    public SkeletonState fight(Skeleton skeleton);

    /**
     * Returns Skeleton next action in form of a State.
     * This gets triggered every Round that the Zombie is NOT in a fight.
     * @return The next Action as a State.
     */
    public SkeletonState roam();
}
