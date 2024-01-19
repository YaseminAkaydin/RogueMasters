package de.roguemaster.player.ViewPackage.View;

import com.googlecode.lanterna.TextColor;
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
            clearAndInitializeGraphics();
            terminal.clearScreen();

            drawTitle(TextColor.ANSI.RED);
            String[] lines = asciiArt.split("\n");

            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

            tg.putString(2, lines.length + 4, "Press 'B' to go back");
            tg.putString(2, lines.length + 5, "Showing Leaderboard");

            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

