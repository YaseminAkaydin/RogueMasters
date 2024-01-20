package de.roguemaster.enemy;

public class CommandHolder {
    private String command;
    private String target;

    public CommandHolder(String command, String target) {
        this.command = command;
        this.target = target;
    }

    public String getCommand() {
        return command;
    }

    public String getTarget() {
        return target;
    }
}
