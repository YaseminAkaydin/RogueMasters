package de.roguemaster.player.ViewPackage.View.Actions.InventoryActions;

import de.roguemaster.player.ViewPackage.Command;

public interface ItemAction {
    Command createCommand();
    boolean isInternalAction();
}
