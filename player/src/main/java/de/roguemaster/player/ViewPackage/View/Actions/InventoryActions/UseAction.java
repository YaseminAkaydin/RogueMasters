package de.roguemaster.player.ViewPackage.View.Actions.InventoryActions;

import de.roguemaster.player.ViewPackage.Command;

public class UseAction implements ItemAction {
    private final int itemId;

    public UseAction(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public Command createCommand() {
        return Command.useItemCommand(itemId);
    }

    @Override
    public boolean isInternalAction() {
        return false;
    }
}
