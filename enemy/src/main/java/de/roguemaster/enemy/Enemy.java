package de.roguemaster.enemy;

public abstract class Enemy {
    //max hp plus getter

    protected int hp;
    protected int maxHp;

    /**
     * Return the next Commands and continues the Enemystates
     * @param isFighting if the enemy is in a Fight
     * @return the next Command of the Enemy
     */
    public abstract CommandHolder getNextTurn(boolean isFighting);

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
