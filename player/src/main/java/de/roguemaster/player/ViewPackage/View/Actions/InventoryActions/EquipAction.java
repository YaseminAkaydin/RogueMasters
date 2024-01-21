package de.roguemaster.player.ViewPackage.View.Actions.InventoryActions;

import de.roguemaster.player.ViewPackage.Command;

public class EquipAction implements ItemAction{
    private final int itemId;

    public EquipAction(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public Command createCommand() {
        System.out.println("Equip item: " + itemId);
        return Command.equipItemCommand(itemId);
    }

    @Override
    public boolean isInternalAction() {
        return false;
    }
}
