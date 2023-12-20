package de.rougemaster.dungeon.character.enemyCharacter.devil.devilState;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.devil.DevilState;

public class DevilP1StrongAttack implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        if(devil.getHp() >= (devil.getMaxHp()/2)){
            return new DevilP1WeakAttack();
        }
        return new DevilRage();
    }

    @Override
    public DevilState roam() {
        return new DevilSleep();
    }
}
