package de.rougemaster.dungeon.game.gameCommand.devilCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

public class devilRageCommand extends GameCommand {

    Devil character;
    public devilRageCommand(Character character) {
        this.character=(Devil) character;
    }

    @Override
    public void execute() {
        character.rage();
    }
}
