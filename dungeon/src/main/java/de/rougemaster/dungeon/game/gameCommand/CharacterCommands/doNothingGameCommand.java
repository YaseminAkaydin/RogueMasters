package de.rougemaster.dungeon.game.gameCommand.CharacterCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;


public class doNothingGameCommand extends GameCommand {

    public void execute() {
        character.doNothing();
    }
}


