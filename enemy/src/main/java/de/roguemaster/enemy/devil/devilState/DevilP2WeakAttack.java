package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilP2WeakAttack  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilSpikeShield();
    }

    @Override
    public DevilState roam() {
        return new DevilRageCalm();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("swordAttack", "");
    }
}
