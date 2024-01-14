package de.rougemaster.dungeon.game.gameCommand.CharacterCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

public class moveGameCommand extends GameCommand {
    Character character;
    Room room;
    public moveGameCommand(Character character, Room room) {
        this.character = character;
        this.room = room;
    }
    public void execute() {
        character.move(room);

    }
}
