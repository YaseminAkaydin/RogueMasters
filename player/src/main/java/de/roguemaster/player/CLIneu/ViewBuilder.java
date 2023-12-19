package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.PlayerData;
import de.roguemaster.player.CLIneu.DataForView.RoomData;
import de.roguemaster.player.CLIneu.View.*;

import java.util.List;

public class ViewBuilder {
    public MainGameView buildMainGameView(Terminal terminal) {
        return new MainGameView(terminal,
                new RoomData("normal",
                        "skeleton",
                        List.of(new String[]{"Sword", "Shield"}),
                        List.of(new String[]{"south", "west", "east", "north"}),
                        "Sword",
                        5),new PlayerData(2,10,2,List.of(new String[]{"Sword", "Shield", "Potion", "Book"})));
    }

    public DungeonMapView buildDungeonMapView(Terminal terminal) {
        return new DungeonMapView(terminal);
    }

    public InventoryView buildInventoryView(Terminal terminal) {
        return new InventoryView(terminal,  new PlayerData(2,10,2,List.of(new String[]{"Sword", "Shield", "Potion", "Book"})));
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
}
