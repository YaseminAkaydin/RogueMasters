package de.rougemaster.dungeon.game.gameCommand.devilCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.devil.Devil;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

public class devilCalmCommand extends GameCommand {
        Devil character;
        public devilCalmCommand(Character character){
                this.character= (Devil) character;
        }
        public void execute(){
                character.calm();
        }
}
