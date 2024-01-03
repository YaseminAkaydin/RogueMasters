package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 *  View for the Leaderboard
 *  TODO: Get Leaderboard from Server and print Leaderboard vom Ding her ganz entspannt
 *  TODO: LeaderboardData Klasse erstellen, wenn wir später ein Leaderboard haben und es auch erstellen und saven
 */
public class LeaderBoardView extends ViewComponent {

    public LeaderBoardView(Terminal terminal) {
        super(terminal);
    }

    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putString("Press 'B' to go back");
            terminal.setCursorPosition(0, 2);
            terminal.putString("Showing Leaderboard");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

