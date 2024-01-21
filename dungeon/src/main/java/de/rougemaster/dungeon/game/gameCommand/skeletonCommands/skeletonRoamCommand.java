package de.rougemaster.dungeon.game.gameCommand.skeletonCommands;

import de.rougemaster.dungeon.character.Character;
import de.rougemaster.dungeon.character.enemyCharacter.skeleton.Skeleton;
import de.rougemaster.dungeon.dungeon.Room;
import de.rougemaster.dungeon.game.gameCommand.GameCommand;

import java.util.List;

public class skeletonRoamCommand extends GameCommand {
    Skeleton character;
    List<Room> rooms;

    public skeletonRoamCommand(Character character, List<Room> rooms) {
        this.character = (Skeleton) character;
        this.rooms = rooms;
    }
    public void execute() {
        character.skeletonRoam(rooms);
    }
}