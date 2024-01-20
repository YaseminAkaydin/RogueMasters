package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilP2StrongAttack  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilP2Defend();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }
}
