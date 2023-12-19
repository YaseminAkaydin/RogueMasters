package de.roguemaster.player.CLIneu;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Dummy Main zum testen der GUI
 */
public class Main {
    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Terminal terminal = terminalFactory.createTerminal();

            Game game = new Game(terminal);
            game.run();

            terminal.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

