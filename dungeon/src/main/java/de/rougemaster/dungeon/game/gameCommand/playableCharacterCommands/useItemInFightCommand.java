package de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands;

import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.item.Item;

public class useItemInFightCommand extends GameCommand {

    Item item;

    public useItemInFightCommand(Item item){
        this.item = item;
    }

    @Override
    public void execute() {

    }
}
