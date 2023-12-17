package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 *  View for the Leaderboard
 *  TODO: Get Leaderboard from Server and print Leaderboard
 */
public class LeaderBoardView implements ViewComponent {
    private Terminal terminal;

    public LeaderBoardView(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Showing Leaderboard");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

