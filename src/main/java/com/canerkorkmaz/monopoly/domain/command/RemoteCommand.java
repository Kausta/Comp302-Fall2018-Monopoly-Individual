package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class RemoteCommand extends BaseCommand {
    public static final String IDENTIFIER = "REMOTE_COMMAND";
    private static final long serialVersionUID = 368115873267408074L;
    private final BaseCommand innerCommand;

    public RemoteCommand(BaseCommand innerCommand) {
        super(IDENTIFIER);
        this.innerCommand = innerCommand;
    }

    public BaseCommand getInnerCommand() {
        return innerCommand;
    }

    @Override
    public String toString() {
        return "RemoteCommand{" +
                "identifier='" + getIdentifier() + "', " +
                "innerCommand=" + innerCommand +
                '}';
    }
}
