package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.DataForView.RoomData;

import java.util.List;

/**
 * This class is responsible for displaying the dungeon map
 * TODO: Überlegung wie die Räumen mit corridoren ohne krasse algorithmen implementiert werden kann
 */
public class DungeonMapView implements ViewComponent {
    // Attributes for dungeon map
    private Terminal terminal;
    private List<RoomData> dummyDungeon;

    public DungeonMapView(Terminal terminal) {
        this.terminal = terminal;
    }
    private void createDummyDungeon() {
        // Populate dummyDungeon with sample rooms and connections
    }

    @Override
    public void display() {
        // Display the dungeon map using Lanterna components
    }
}
