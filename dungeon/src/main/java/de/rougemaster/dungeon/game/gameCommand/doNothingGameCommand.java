package de.rougemaster.dungeon.game.gameCommand;

import de.rougemaster.dungeon.character.Character;

public class doNothingGameCommand extends GameCommand {
    Character character;

    public doNothingGameCommand(Character character) {
        this.character = character;
    }

    public void execute() {
        character.doNothing();
    }
}
