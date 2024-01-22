package de.roguemaster.enemy.devil.devilState;


import de.roguemaster.enemy.CommandHolder;
import de.roguemaster.enemy.devil.Devil;
import de.roguemaster.enemy.devil.DevilState;

public class DevilSleep  implements DevilState {
    @Override
    public DevilState fight(Devil devil) {
        return new DevilP1StrongAttack();
    }

    @Override
    public DevilState roam() {
        return new DevilSleep();
    }

    @Override
    public CommandHolder createCommand() {
        return new CommandHolder("doNothing", "");
    }

}
