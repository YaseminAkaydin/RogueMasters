package de.rougemaster.dungeon.game.gameCommand;

import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.item.Item;

public class equipItemGameCommand extends GameCommand{
    PlayableCharacter player;
    Item item;

    public equipItemGameCommand(PlayableCharacter player, Item item){
        this.player = player;
        this.item = item;
    }

    @Override
    public void execute() {
        try {
            player.equipItem(item);
        } catch (InventoryItemMissingException e) {
            throw new RuntimeException(e);
        }
    }
}
