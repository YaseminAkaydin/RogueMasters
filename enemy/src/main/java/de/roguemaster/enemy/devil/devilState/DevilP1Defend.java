package de.roguemaster.enemy.devil.devilState;

import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilP1Defend  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        if(devil.getHp() >= (devil.getMaxHp()/2)){
            return new DevilP1StrongAttack();
        }
        return new DevilRage();
    }

    @Override
    public DevilState roam() {
        return new DevilSleep();
    }
}
