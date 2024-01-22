package de.rougemaster.dungeon.game.gameCommand.skeletonCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.dungeon.Dungeon;
import de.rougemaster.dungeon.dungeon.DungeonRoom;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.Game;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

import java.util.List;

public class skeletonRoamCommand extends GameCommand {
    Skeleton character;
    Room rooms;

    public skeletonRoamCommand(Character character) {
        this.character = (Skeleton) character;

    }
    public void execute() {
        character.skeletonRoam();
    }
}