package de.roguemaster.player.ViewPackage.View.MainActions;

import de.roguemaster.player.ViewPackage.Command;

public class PickupAction implements MainAction{
    private final int itemId;

    public PickupAction(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public Command createCommand() {
        return Command.pickupCommand(itemId);
    }
}
