package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class PassCommand extends BaseCommand {
    public static final String IDENTIFIER = "PASS_COMMAND";
    private static final long serialVersionUID = 1L;

    private final int location;

    public PassCommand(int location) {
        super(IDENTIFIER);
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "PassCommand{" +
                "location=" + location +
                '}';
    }
}
