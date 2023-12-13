package de.roguemaster.player.CommandLineInterface;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * This class is responsible for:
 *  - UI Output --> DungeonMap, PlayerInfos, Game State
 *  - Input --> interpret user input and translate it into a command/action
 *  - Procedure calls --> translate user actions into procedure calls and send them to the gRPC module for remote execution
 *  - Listen Response --> Listen for response from gRPC calls and handle them
 *  - Map update --> Get updates about the map and update the game state like map changes etc
 *  - Check user input
 *  - Save history of commands from user ??
 *  - Error handling
 */

public class CommandLineWriter {
    private String outputString;

    // Lanterna
    private static Terminal terminal;
    private static Screen screen;
    private static Dungeon dungeon;


    CommandLineWriter(CLIBuilder builder) {
        outputString = builder.outputString;
    }

    /**
     * Writes the output to the console.
      */
    public void write () throws IOException {
        /**
         *  Step 1: Draw Map with info about width and height + rooms + player position (if changed)
         *  Step 2: Draw Player Info at the bottom of the screen (if changed)
         *  Step 3: Draw Game State, which contains monster, items (if changed)
         *  Step 4: Draw Menu, which contains the options for the player, at the bottom of the screen (if changed)
         **/


        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        for(int y = 0; y < dungeon.getHeight(); y++) {
            for(int x = 0; x < dungeon.getWidth(); x++) {
                textGraphics.putString(x, y, String.valueOf(dungeon.getLayout()[y][x]));
            }
        }


        textGraphics.putString(1, 1, outputString);
        screen.refresh();

    }



    /**
     * Reads the input from the console.
     */
    public String read() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke != null && keyStroke.getKeyType() == KeyType.Character) {
            return String.valueOf(keyStroke.getCharacter());
        }
        return null;
    }

    public static class CLIBuilder {
        private String outputString;

        public CLIBuilder() {
            outputString = "";
        }

        /**
         * TODO: Change the String room to Room room
         * Add a room to the output.
         * @param battle Room which should be outputted.
         * @return Buildet
         */
        public CLIBuilder createRoom(String room) {
            outputString += "Room: " + room + "\n";
            return this;
        }

        public CLIBuilder createBattle(String battle) {
            outputString += "Battle: " + battle + "\n";
            return this;
        }

        public CLIBuilder createMap(Dungeon dungeon) {
            //outputString += "Map: " + map + "\n";
            return this;
        }

        public CLIBuilder createSmallStats(String stats) {
            outputString += "Stats (Small): " + stats + "\n";
            return this;
        }

        public CLIBuilder createBigStats(String stats) {
            outputString += "Stats (Big): " + stats + "\n";
            return this;
        }

        public CLIBuilder createItemDescription(String itemDescription) {
            outputString += "Item: " + itemDescription + "\n";
            return this;
        }

        public CLIBuilder createCharacterDescription(String characterDescription) {
            outputString += "Character: " + characterDescription + "\n";
            return this;
        }

        /**
         * Adds Options
         * @param options n Options
         * @return Builder
         */
        public CLIBuilder option(String... options) {
            for(String option: options){
                outputString += option + "\n";
            }
            return this;
        }

        public CLIBuilder createMenu(String menu) {
            outputString += "Menu: " + menu + "\n";
            return this;
        }

        public CLIBuilder createLobbyScreen(String lobbyScreen) {
            outputString += "Lobby: " + lobbyScreen + "\n";
            return this;
        }

        public CLIBuilder createStartScreen(String startScreen) {
            outputString += "Start Screen: " + startScreen + "\n";
            return this;
        }

        /**
         * Creates a new CLW with the given output.
         * @return CommandLineWriter der die Ausgaben.
         */
        public CommandLineWriter build () {
            return new CommandLineWriter(this);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            //dungeon.createDungeon(new char[screen.getTerminalSize().getRows()][screen.getTerminalSize().getColumns()],
                  //  screen.getTerminalSize().getColumns(), screen.getTerminalSize().getRows(), new java.util.Random(), new ArrayList<Room>((Collection<? extends Room>) new Room(2, 2, 5, 5)));

            // Example usage of the builder
            CommandLineWriter writer = new CommandLineWriter.CLIBuilder()
                    // .createMap(dungeon) // Add more elements as needed
                    //.createSmallStats("Health: 100, MP: 50")
                    .build();

            writer.write();

            // Reading user input
            System.out.println("Enter a command: ");
            String userInput = writer.read();
            System.out.println("You entered: " + userInput);


            // Keep the screen open until a key is pressed
            terminal.readInput();
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CommandLineWriter com = new CommandLineWriter.CLIBuilder.fight().Option("Angreifen", "Verteidigen", "Flüchten").build();
}