package de.roguemaster.player.CLIneu;

public enum InventoryAction {
    CHOOSE_ACTION, // Initial state where the player chooses Drop, Use, etc.
    DROP_ITEM,
    USE_CONSUMABLE,
    VIEWING // Viewing the inventory without making a choice
}
