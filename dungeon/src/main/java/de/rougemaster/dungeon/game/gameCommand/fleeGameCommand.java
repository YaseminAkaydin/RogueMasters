package de.rougemaster.dungeon.game.gameCommand;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.dungeon.Room;

public class fleeGameCommand extends GameCommand {
    Character character;
    Room room;

    fleeGameCommand(Character character, Room room) {
        this.character = character;
        this.room = room;
    }

    public void execute() {
        character.flee(room);
    }
}

