package de.roguemaster.player.CLIneu.View;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Wenn das Spiel Gestartet wird, wird diese View angezeigt.
 * TODO: Verschönern
 */
public class StartScreenView extends ViewComponent {

    public StartScreenView(Terminal terminal) {
        super(terminal);
    }
    @Override
    public void display() {
        try {
            terminal.clearScreen();
            terminal.setCursorPosition(0, 0);
            terminal.putCharacter('1');
            terminal.putCharacter('.');
            terminal.putString(" Start Lobby");
            terminal.putCharacter('\n');
            terminal.putCharacter('2');
            terminal.putCharacter('.');
            terminal.putString(" Join Lobby");
            terminal.putCharacter('\n');
            terminal.putCharacter('3');
            terminal.putCharacter('.');
            terminal.putString(" See Leaderboard");
            terminal.putCharacter('\n');
            terminal.putCharacter('4');
            terminal.putCharacter('.');
            terminal.putString(" Exit");
            terminal.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
