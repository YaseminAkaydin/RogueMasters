package de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands;

import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.item.Item;

public class deleteItemGameCommand extends GameCommand {
    PlayableCharacter player;
    Item item;

    public deleteItemGameCommand(PlayableCharacter player, Item item){
        this.player = player;
        this.item = item;
    }

    @Override
    public void execute() {
        player.deleteItem(item);
    }
}
