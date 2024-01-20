package de.rougemaster.dungeon.game.gameCommand.zombieCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.zombie.Zombie;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

public class zombieHealCommand extends GameCommand {
    Zombie character;
    public zombieHealCommand(Character character) {
        this.character= (Zombie) character;
    }

    @Override
    public void execute() {
        character.heal();
    }
}
