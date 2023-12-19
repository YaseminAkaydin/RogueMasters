package de.rougemaster.dungeon.character.enemyCharacter.devil.devilState;

import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.character.enemyCharacter.devil.DevilState;

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
