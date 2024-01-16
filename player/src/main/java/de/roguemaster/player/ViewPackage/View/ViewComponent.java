package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.ItemData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class ViewComponent {
    // ALl
    protected final Terminal terminal;
    protected TextGraphics tg;

    // MGV, IV
    protected RoomData roomData;
    protected PlayerData playerData;
    protected DungeonData dungeonData;
    protected final Random random = new Random();
    protected List<ItemData> inventoryItems;
    // DMV
    protected List<RoomData> rooms;

    public abstract void display();

    // For MGV
    protected ViewComponent(Terminal terminal, RoomData roomData, PlayerData playerData, DungeonData dungeonData){
        this.terminal = terminal;
        this.roomData = roomData;
        this.playerData = playerData;
        this.dungeonData = dungeonData;
        this.rooms = dungeonData.getRooms();
        this.inventoryItems = playerData.getInventory();

    }
    // For DMV
    protected ViewComponent(Terminal terminal, PlayerData playerData, DungeonData dungeonData){
        this.terminal = terminal;
        this.playerData = playerData;
        this.dungeonData = dungeonData;
        this.rooms = dungeonData.getRooms();
        this.inventoryItems = playerData.getInventory();

    }
    // For IV
    protected ViewComponent(Terminal terminal, PlayerData playerData){
        this.terminal = terminal;
        this.playerData = playerData;
        this.inventoryItems = playerData.getInventory();
    }
    protected ViewComponent(Terminal terminal){
        this.terminal = terminal;
    }

    protected void displayPlayerStats(TextGraphics tg) throws IOException {
        int statsStartY = terminal.getTerminalSize().getRows() - 1; // Below the room
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, statsStartY, "LEVEL: " + playerData.getLevel() +
                " HP: " + playerData.getHp() +
                "/10 EXP: " + playerData.getExperience() +
                "  -- 'M' = Map -- 'I' = Inventory -- 'S' = RoomView");
    }
    protected void clearAndInitializeGraphics() throws IOException {
        terminal.clearScreen();
        tg = terminal.newTextGraphics();
    }

}
