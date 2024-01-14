package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class JoiningLobbyView extends ViewComponent {

    private String currentID = "";

    public JoiningLobbyView(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Input Lobbycode: " + currentID);
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }
}

