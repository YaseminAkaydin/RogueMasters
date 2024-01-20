package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilSpikeShield implements DevilState {

    @Override
    public DevilState fight(Devil devil) {
        return new DevilRageAttack();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }
}
