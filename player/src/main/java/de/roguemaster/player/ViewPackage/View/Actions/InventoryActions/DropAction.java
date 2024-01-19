package de.roguemaster.player.ViewPackage.View.Actions.InventoryActions;

import de.roguemaster.player.ViewPackage.Command;

public class DropAction implements ItemAction {
    private final int itemId;

    public DropAction(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public Command createCommand() {
        // Logic to drop the item
        System.out.println("Dropped item with ID: " + itemId);
        return Command.dropItemCommand(itemId);

    }

    @Override
    public boolean isInternalAction() {
        return false;
    }
}



