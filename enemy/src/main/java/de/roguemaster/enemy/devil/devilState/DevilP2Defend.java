package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilP2Defend  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilP2WeakAttack();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }
}
