package de.roguemaster.enemy;

import de.roguemaster.facade.EnemyTyp;

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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * Returns the Typ of the Enemy
     * @return Typ of the Enemy
     */
    public abstract EnemyTyp getTyp();
}
