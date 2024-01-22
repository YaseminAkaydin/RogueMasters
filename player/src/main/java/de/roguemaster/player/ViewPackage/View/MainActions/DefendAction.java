package de.roguemaster.player.ViewPackage.View.MainActions;

import de.roguemaster.player.ViewPackage.Command;

public class DefendAction implements MainAction {
    @Override
    public Command createCommand() {
        return Command.defend();
    }
}
