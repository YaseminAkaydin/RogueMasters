package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.ViewPackage.DataForView.DungeonData;
import de.roguemaster.player.ViewPackage.DataForView.ItemData;
import de.roguemaster.player.ViewPackage.DataForView.PlayerData;
import de.roguemaster.player.ViewPackage.DataForView.RoomData;
import de.roguemaster.player.ViewPackage.View.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewBuilder {

    private final MainGameView mainGameView;
    private final DungeonMapView dungeonMapView;
    private final InventoryView inventoryView;
    private final StartScreenView startScreenView;
    private final StartingLobbyView startingLobbyView;
    private final JoiningLobbyView joiningLobbyView;
    private final LeaderBoardView leaderBoardView;
    private final DungeonData dungeonData;
    private final PlayerData playerData;

    public ViewBuilder(Terminal terminal) {
        this.dungeonData = generateTestDungeon();
        this.playerData = generateTestPlayerData();
        this.dungeonMapView = buildDungeonMapView(terminal);
        this.mainGameView = buildMainGameView(terminal);
        this.inventoryView = buildInventoryView(terminal);
        this.startScreenView = buildStartScreenView(terminal);
        this.startingLobbyView = buildStartingLobbyView(terminal);
        this.joiningLobbyView = buildJoiningLobbyView(terminal);
        this.leaderBoardView = buildLeaderBoardView(terminal);
    }

    public MainGameView buildMainGameView(Terminal terminal) {
        return new MainGameView(terminal, this.dungeonData, this.playerData);
    }

    public DungeonMapView buildDungeonMapView(Terminal terminal) {
        return new DungeonMapView(terminal, this.playerData, this.dungeonData);
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

    // Getter & Setter
    public MainGameView getMainGameView() {
        return mainGameView;
    }

    public DungeonMapView getDungeonMapView() {
        return dungeonMapView;
    }

    public InventoryView getInventoryView() {
        return inventoryView;
    }

    public StartScreenView getStartScreenView() {
        return startScreenView;
    }

    public StartingLobbyView getStartingLobbyView() {
        return startingLobbyView;
    }

    public JoiningLobbyView getJoiningLobbyView() {
        return joiningLobbyView;
    }

    public LeaderBoardView getLeaderBoardView() {
        return leaderBoardView;
    }

    public DungeonData getDungeonData() {
        return dungeonData;
    }

    public static DungeonData generateTestDungeon() {
        List<RoomData> rooms = new ArrayList<>();

        // Example items for the rooms
        List<ItemData> commonItems = List.of(
                new ItemData(0, "Sword", "A Sword only the mighty can wield", 20),
                new ItemData(1, "Shield", "A Shield for weak individuals", 5),
                new ItemData(2, "Potion", "Potion, heal yourself ffs", 10),
                new ItemData(3, "Book", "Book, with a lot of pictures", 10)
        );

        // Create rooms with types, monsters, items, IDs, and adjacent rooms
        RoomData room1 = new RoomData("BossRoom", "Skeleton", null, 1,
                Map.of("EAST", 2));
        RoomData room2 = new RoomData("DungeonRoom", "Skeleton", commonItems.get(0), 2,
                Map.of("NORTH", 5, "WEST", 1, "SOUTH", 3, "EAST", 4));
        RoomData room3 = new RoomData("DungeonRoom", "Zombie", null, 3,
                Map.of("NORTH", 2));
        RoomData room4 = new RoomData("DungeonRoom", "Zombie", null, 4,
                Map.of("WEST", 2));
        RoomData room5 = new RoomData("DungeonRoom", "Zombie", null, 5,
                Map.of("WEST", 6, "SOUTH", 2, "EAST", 8));
        RoomData room6 = new RoomData("DungeonRoom", "Zombie", null, 6,
                Map.of("NORTH", 7, "EAST", 5));
        RoomData room7 = new RoomData("DungeonRoom", "Zombie", null, 7,
                Map.of("SOUTH", 6));
        RoomData room8 = new RoomData("TreasureRoom", "Zombie", null, 8,
                Map.of("NORTH", 9, "WEST", 5));
        RoomData room9 = new RoomData("DungeonRoom", "Zombie", null, 9,
                Map.of("SOUTH", 8, "EAST", 10));
        RoomData room10 = new RoomData("DungeonRoom", "Zombie", null, 10,
                Map.of("WEST", 9));

        // Add rooms to the list
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);
        rooms.add(room10);

        return new DungeonData(rooms);
    }

    public static PlayerData generateTestPlayerData() {
        return new PlayerData(
                1,
                10,
                0,
                List.of(new ItemData(0, "Sword", "A Sword only the mighty can wield", 20),
                        new ItemData(1, "Shield", "A Shield for weak individuals", 5),
                        new ItemData(2, "Potion", "Potion, heal yourself ffs", 10),
                        new ItemData(3, "Book", "Book, with a lot of pictures", 10)),
                2);
    }

}
