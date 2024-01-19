package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class JoiningLobbyView extends ViewComponent {

    private String currentID = "";
    private boolean success = true;

    public JoiningLobbyView(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void display() {
        try {
            clearAndInitializeGraphics();
            terminal.clearScreen();
            drawTitle(TextColor.ANSI.RED);
            String[] lines = asciiArt.split("\n");
            tg.putString(2, lines.length + 4, "Input Lobbycode: " + currentID);

            if (!success) {
                tg.putString(2, lines.length + 6, "INVALID CODE TRY AGAIN!");
            }
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public void setSuccess(boolean b) {
        this.success = b;
    }
}

