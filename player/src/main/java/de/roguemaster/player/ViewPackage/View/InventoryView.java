package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.ItemData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryView extends ViewComponent {
    // Define the states for InventoryView
    public enum State {
        MAIN_OPTIONS,
        DROP_ITEM,
        USE_CONSUMABLE
        // Add other states as needed
    }

    private State currentState;

    private final Map<Integer, String> optionMappings = new HashMap<>();
    private final Map<Integer, String> dropItemOptionMappings = new HashMap<>();
    private final Map<Integer, String> useConsumableOptionMappings = new HashMap<>();


    public InventoryView(Terminal terminal, PlayerData playerData) {
        super(terminal, playerData);
        this.currentState = State.MAIN_OPTIONS;
    }

    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            tg.setForegroundColor(TextColor.ANSI.WHITE);

            drawTitle(tg);
            drawItemList(tg);
            switch (currentState) {
                case MAIN_OPTIONS:
                    displayOptions(tg);
                    break;
                case DROP_ITEM:
                    displayDropItemOptions(tg);
                    break;
                case USE_CONSUMABLE:
                    displayUseConsumableOptions(tg);
                    break;
                // Other cases as needed
            }

            // Display player stats
            displayPlayerStats(tg);

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawTitle(TextGraphics tg) {
        // Display inventory title
        String title = "Inventory";
        tg.putString(2, 1, title); // Adjust the position as needed
    }

    private void drawItemList(TextGraphics tg) {
        // Display the list of inventory items
        int startY = 3; // Adjust the starting Y position as needed
        for (int i = 0; i < inventoryItems.size(); i++) {
            String itemString = (i + 1) + ". " +
                    inventoryItems.get(i).getName() +
                    ": " +
                    inventoryItems.get(i).getDescription() + " | Attribute: " + inventoryItems.get(i).getAttributes();
            tg.putString(2, startY + i, itemString);
        }
    }

    private void displayOptions(TextGraphics tg) {

        AtomicInteger optionsStartY = new AtomicInteger(inventoryItems.size() + 6);
        AtomicInteger optionNumber = new AtomicInteger(1); // Start with option number 1

        // Example: Display 'Drop Item' only if inventory has items
        tg.putString(2, optionsStartY.get() - 1, "Options");
        if (!inventoryItems.isEmpty()) {
            String dropItemOption = optionNumber + ". Drop Item";
            tg.putString(2, optionsStartY.getAndIncrement(), dropItemOption);
            optionMappings.put(optionNumber.getAndIncrement(), "Drop Item");
        }
        // Display 'Use Consumable' option if any consumable items are present
        long consumableCount = inventoryItems.stream()
                .filter(item -> item.getDescription().contains("Potion") || item.getDescription().contains("Book"))
                .count();
        if (consumableCount > 0) {
            String useConsumableOption = optionNumber + ". Use Consumable";
            tg.putString(2, optionsStartY.getAndIncrement(), useConsumableOption);
            optionMappings.put(optionNumber.getAndIncrement(), "Use Consumable");
        }

    }

    private void displayDropItemOptions(TextGraphics tg) {
        AtomicInteger optionsStartY = new AtomicInteger(inventoryItems.size() + 6);
        AtomicInteger optionNumber = new AtomicInteger(1);
        tg.putString(2, optionsStartY.get() - 1, "Choose Item ID to drop");
        for (ItemData item : inventoryItems) {
            String optionText = optionNumber.get() + ". Drop " + optionNumber.get();
            dropItemOptionMappings.put(optionNumber.get(), "Drop " + optionNumber.getAndIncrement());
            tg.putString(2, optionsStartY.getAndIncrement(), optionText);
        }
        tg.putString(2, optionsStartY.getAndIncrement(), optionNumber.get() + ". Go back");
        dropItemOptionMappings.put(optionNumber.get(), "Go back");
    }

    private void displayUseConsumableOptions(TextGraphics tg) {
        AtomicInteger optionsStartY = new AtomicInteger(inventoryItems.size() + 6);
        AtomicInteger optionNumber = new AtomicInteger(1);
        tg.putString(2, optionsStartY.get() - 1, "Choose Item ID to use");
        for (ItemData item : inventoryItems) {
            if (item.getDescription().contains("Potion") || item.getDescription().contains("Book")) {
                String itemType = item.getDescription().contains("Potion") ? "Potion" : "Book";
                String optionText = optionNumber.get() + ". Use " + itemType;
                useConsumableOptionMappings.put(optionNumber.get(), "Use " + itemType);
                tg.putString(2, optionsStartY.getAndIncrement(), optionText);
                optionNumber.incrementAndGet();
            }
        }
        tg.putString(2, optionsStartY.getAndIncrement(), optionNumber.get() + ". Go back");
        useConsumableOptionMappings.put(optionNumber.get(), "Go back");
    }


    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        // Call display to refresh the view
        display();
    }

    public State getCurrentState() {
        return currentState;
    }

    public Map<Integer, String> getOptionMappings() {
        return optionMappings;
    }

    public Map<Integer, String> getDropItemOptionMappings() {
        return dropItemOptionMappings;
    }

    public Map<Integer, String> getUseConsumableOptionMappings() {
        return useConsumableOptionMappings;
    }
}
