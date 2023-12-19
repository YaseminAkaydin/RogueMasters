package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;

import java.io.IOException;
import java.util.List;

public class InventoryView implements ViewComponent {
    // Attributes for inventory items
    private Terminal terminal;
    private List<String> inventoryItems;
    private PlayerData playerData;

    public InventoryView(Terminal terminal, PlayerData playerData) {
        this.terminal = terminal;
        this.playerData = playerData;
        this.inventoryItems = playerData.getInventoryItems();

    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            TextGraphics tg = terminal.newTextGraphics();
            tg.setForegroundColor(TextColor.ANSI.WHITE);

            // Display inventory title
            String title = "Inventory";
            tg.putString(2, 1, title); // Adjust the position as needed

            // Display the list of inventory items
            int startY = 3; // Adjust the starting Y position as needed
            for (int i = 0; i < inventoryItems.size(); i++) {
                String itemString = (i + 1) + ". " + inventoryItems.get(i);
                tg.putString(2, startY + i, itemString);
            }

            // Display player options
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            int optionsStartY = inventoryItems.size() + 4; // Below the inventory items
            tg.putString(2, optionsStartY++, "Choose Options:");
            tg.putString(2, optionsStartY++, "1. Drop Item");
            tg.putString(2, optionsStartY++, "2. Use Consumable");
            tg.putString(2, optionsStartY, "3. Go back to MainView");

            // Display player stats
            displayPlayerStats(tg, playerData);

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void displayPlayerStats(TextGraphics tg, PlayerData playerData) throws IOException {
        int statsStartY = terminal.getTerminalSize().getRows() - 3; // Near the bottom of the terminal
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(2, statsStartY, "Level: " + playerData.getLevel() +
                " HP: " + playerData.getHp() +
                "/10 EXP: " + playerData.getExp());
    }
}
