package de.rougemaster.dungeon.game.gameCommand.zombieCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

import java.util.List;

public class zombieRoamCommand extends GameCommand {
    Zombie character;

    public zombieRoamCommand(Character character) {
        this.character = (Zombie) character;

    }
    public void execute() {
        character.roam();
    }
}