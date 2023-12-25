package de.roguemaster.player.CLIneu.DataForView;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import de.roguemaster.player.CLIneu.View.ViewComponent;

import java.io.IOException;
import java.util.List;

/**
 * DungeonData, only contains List of Rooms at first
 */
public class DungeonData {
    private final List<RoomData> rooms;
    public DungeonData(List<RoomData> rooms) {
        this.rooms = rooms;
    }

    public List<RoomData> getRooms() {
        return rooms;
    }

}
