package de.rougemaster.dungeon;

import de.rougemaster.dungeon.lobby.LobbyFacade;

import java.util.Scanner;

public class DungeonApplication {
    static boolean running = true;
    public static void main(String[] args) {

        System.out.println("Starting RogueMaster Server...");

        // TODO: Start ServerStub
        // ServerStub serverStub = new ServerStub();

        LobbyFacade lobbyFacade = LobbyFacade.getInstance();
        System.out.println("Server started!");

        // Thread serverThread = new Thread(() -> { /* Server logic */ });
        // serverThread.start();

        // Use a separate thread to handle user input
        Thread userInputThread = new Thread(() -> handleUserInput());
        userInputThread.start();

        // Wait for the user to press q to quit
        try {
            userInputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO: Stop ServerStub and perform any necessary cleanup
        // serverStub.stop();

        System.out.println("Server stopped.");
    }

    private static void handleUserInput() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("Press 'q' to quit");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                running = false;
            } else {
                System.out.println("Invalid input. Please press 'q' to quit.");
            }
        }

        scanner.close();
    }
}