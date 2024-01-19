package de.roguemaster.player.ViewPackage.View.MainActions;

import de.roguemaster.player.ViewPackage.Command;

public class MoveAction implements MainAction{
    private final int roomId;

    public MoveAction(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public Command createCommand() {
        return Command.moveCommand(roomId);
    }
}
