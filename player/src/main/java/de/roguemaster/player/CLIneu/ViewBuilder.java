package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.DungeonData;
import de.roguemaster.player.CLIneu.DataForView.ItemData;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;
import de.roguemaster.player.CLIneu.View.*;

import java.util.List;

public class ViewBuilder {
    private Terminal terminal;
    private ViewComponent currentView;
    private MainGameView mainGameView;
    private DungeonMapView dungeonMapView;
    private InventoryView inventoryView;
    private StartScreenView startScreenView;
    private StartingLobbyView startingLobbyView;
    private JoiningLobbyView joiningLobbyView;
    private LeaderBoardView leaderBoardView;
    private RoomData roomData;
    private DungeonData dungeonData;
    private ItemData itemData;
    private PlayerData playerData;

    public ViewBuilder(Terminal terminal) {
        this.terminal = terminal;
        this.roomData = new RoomData(
                "normal",
                "skeleton",
                List.of(new ItemData(0,"Sword","A Sword only the mighty can wield",20),
                        new ItemData(1,"Shield","A Shield for weak individuals",5),
                        new ItemData(2,"Potion","Potion, heal yourself ffs",10),
                        new ItemData(3,"Book","Book, with a lot of pictures",10)),
                List.of(new String[]{"south", "west", "east", "north"}),
                5);
        this.dungeonData = dungeonData;
        this.itemData = itemData;
        this.playerData = new PlayerData(
                2,
                10,
                2,
                List.of(new ItemData(0,"Sword","A Sword only the mighty can wield",20),
                        new ItemData(1,"Shield","A Shield for weak individuals",5),
                        new ItemData(2,"Potion","Potion, heal yourself ffs",10),
                        new ItemData(3,"Book","Book, with a lot of pictures",10)));


        this.mainGameView = buildMainGameView(terminal);
        this.dungeonMapView = buildDungeonMapView(terminal);
        this.inventoryView = buildInventoryView(terminal);
        this.startScreenView = buildStartScreenView(terminal);
        this.startingLobbyView = buildStartingLobbyView(terminal);
        this.joiningLobbyView = buildJoiningLobbyView(terminal);
        this.leaderBoardView = buildLeaderBoardView(terminal);
        this.currentView = startScreenView;
    }

    public MainGameView buildMainGameView(Terminal terminal) {
        return new MainGameView(terminal,this.roomData, this.playerData);
    }

    public DungeonMapView buildDungeonMapView(Terminal terminal) {
        return new DungeonMapView(terminal);
    }

    public InventoryView buildInventoryView(Terminal terminal) {
        return new InventoryView(terminal,
                this.playerData);
    }

    public StartScreenView buildStartScreenView(Terminal terminal) {
        return new StartScreenView(terminal);
    }
    public StartingLobbyView buildStartingLobbyView(Terminal terminal) {
        return new StartingLobbyView(terminal);
    }

    public JoiningLobbyView buildJoiningLobbyView(Terminal terminal) {
        return new JoiningLobbyView(terminal);
    }

    public LeaderBoardView buildLeaderBoardView(Terminal terminal) {
        return new LeaderBoardView(terminal);
    }

    public ViewComponent getCurrentView() {
        return currentView;
    }

    public void setCurrentView(ViewComponent currentView) {
        this.currentView = currentView;
    }

    public MainGameView getMainGameView() {
        return mainGameView;
    }

    public void setMainGameView(MainGameView mainGameView) {
        this.mainGameView = mainGameView;
    }

    public DungeonMapView getDungeonMapView() {
        return dungeonMapView;
    }

    public void setDungeonMapView(DungeonMapView dungeonMapView) {
        this.dungeonMapView = dungeonMapView;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public void setInventoryView(InventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }

    public StartScreenView getStartScreenView() {
        return startScreenView;
    }

    public void setStartScreenView(StartScreenView startScreenView) {
        this.startScreenView = startScreenView;
    }

    public StartingLobbyView getStartingLobbyView() {
        return startingLobbyView;
    }

    public void setStartingLobbyView(StartingLobbyView startingLobbyView) {
        this.startingLobbyView = startingLobbyView;
    }

    public JoiningLobbyView getJoiningLobbyView() {
        return joiningLobbyView;
    }

    public void setJoiningLobbyView(JoiningLobbyView joiningLobbyView) {
        this.joiningLobbyView = joiningLobbyView;
    }

    public LeaderBoardView getLeaderBoardView() {
        return leaderBoardView;
    }

    public void setLeaderBoardView(LeaderBoardView leaderBoardView) {
        this.leaderBoardView = leaderBoardView;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    public DungeonData getDungeonData() {
        return dungeonData;
    }

    public void setDungeonData(DungeonData dungeonData) {
        this.dungeonData = dungeonData;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }
}
