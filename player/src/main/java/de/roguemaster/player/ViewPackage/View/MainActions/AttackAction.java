package de.roguemaster.player.ViewPackage.View.MainActions;

import de.roguemaster.player.ViewPackage.Command;

public class AttackAction implements MainAction {
    private final int attackedId;

    public AttackAction(int attackedId) {
        this.attackedId = attackedId;
    }

    @Override
    public Command createCommand() {
        return Command.attackCommand(attackedId);
    }
}