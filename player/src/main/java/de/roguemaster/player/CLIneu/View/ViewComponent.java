package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.DungeonData;
import de.roguemaster.player.CLIneu.DataForView.ItemData;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;

import java.util.List;
import java.util.Random;

public abstract class ViewComponent {
    // ALl
    protected final Terminal terminal;

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
        this.inventoryItems = playerData.getInventoryItems();

    }
    // For DMV
    protected ViewComponent(Terminal terminal, PlayerData playerData, DungeonData dungeonData){
        this.terminal = terminal;
        this.playerData = playerData;
        this.dungeonData = dungeonData;
        this.rooms = dungeonData.getRooms();
        this.inventoryItems = playerData.getInventoryItems();

    }
    // For IV
    protected ViewComponent(Terminal terminal, PlayerData playerData){
        this.terminal = terminal;
        this.playerData = playerData;
        this.inventoryItems = playerData.getInventoryItems();
    }
    protected ViewComponent(Terminal terminal){
        this.terminal = terminal;
    }

}
