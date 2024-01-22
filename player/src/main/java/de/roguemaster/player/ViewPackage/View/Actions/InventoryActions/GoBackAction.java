package de.roguemaster.player.ViewPackage.View.Actions.InventoryActions;

import de.roguemaster.player.ViewPackage.Command;
import de.roguemaster.player.ViewPackage.View.InventoryView;

public class GoBackAction implements ItemAction {
    @Override
    public Command createCommand() {
        // No command is needed for internal UI actions
        return null;
    }

    @Override
    public boolean isInternalAction() {
        // This indicates it's an internal UI action
        return true;
    }

    public void execute(InventoryView inventoryView) {
        // Logic to change the current state of the InventoryView
        // For example, go back to the main menu or previous screen
        inventoryView.setCurrentState(InventoryView.State.MAIN_OPTIONS);
    }
}

