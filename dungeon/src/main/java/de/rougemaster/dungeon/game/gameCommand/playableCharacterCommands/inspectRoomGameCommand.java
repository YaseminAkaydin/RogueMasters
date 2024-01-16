package de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

public class inspectRoomGameCommand extends GameCommand {
    PlayableCharacter character;
    Room room;
    public inspectRoomGameCommand(PlayableCharacter character, Room room) {
        this.character = character;
        this.room = room;
    }
    public void execute() {
      character.inspectRoom();
    }
}
