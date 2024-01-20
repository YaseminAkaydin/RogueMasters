package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilRageAttack implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilP2StrongAttack();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }
}
