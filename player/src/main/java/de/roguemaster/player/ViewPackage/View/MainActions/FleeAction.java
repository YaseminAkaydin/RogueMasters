package de.roguemaster.player.ViewPackage.View.MainActions;

import de.roguemaster.player.ViewPackage.Command;

public class FleeAction implements MainAction{
    private final int roomId;

    public FleeAction(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public Command createCommand() {
        return Command.fleeCommand(roomId);
    }
}
