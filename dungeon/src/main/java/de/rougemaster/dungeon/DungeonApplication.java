package de.rougemaster.dungeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DungeonApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DungeonApplication.class, args);
        Dungeon dungeon = new Dungeon(10,3); // Example difficulty level
        dungeon.printDungeonLayout(); // Print the layout
    }

}
