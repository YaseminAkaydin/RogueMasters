package de.rougemaster.dungeon.game.gameCommand.playableCharacterCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.characterExceptions.InventoryItemMissingException;
import de.rougemaster.dungeon.character.playerCharacter.PlayableCharacter;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;
import de.rougemaster.dungeon.item.Item;

public class useItemGameCommand extends GameCommand {
    PlayableCharacter character;
    Item item;
    public useItemGameCommand(PlayableCharacter character, Item item) {
        this.character = character;
        this.item= item;

    }
    public void execute() {
        try {
            character.useItem(item);
        } catch (InventoryItemMissingException e) {
            e.printStackTrace();
        }
    }
}
