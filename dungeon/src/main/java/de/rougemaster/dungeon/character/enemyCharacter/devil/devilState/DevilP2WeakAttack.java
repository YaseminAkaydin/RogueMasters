package de.rougemaster.dungeon.character.enemyCharacter.devil.devilState;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.devil.DevilState;

public class DevilP2WeakAttack  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilSpikeShield();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }
}
