package de.roguemaster.enemy;

public class Enemy {
    //max hp plus getter

    private int hp;
    private int maxHp;

    /**
     * This method is called every round to evaluate and execute the next action.
     */
    public boolean evaluateSituation(){
        //TODO: implement this method to evaluate the situation and return true if the enemy should fight
        return false;
    }

    public CommandHolder getNextTurn(boolean fight){
        return new CommandHolder("move", "r1");
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
