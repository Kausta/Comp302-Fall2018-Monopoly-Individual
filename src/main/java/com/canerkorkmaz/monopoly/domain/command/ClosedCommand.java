package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class ClosedCommand extends BaseCommand {
    public static final String IDENTIFIER = "CLOSED";
    private static final long serialVersionUID = 6262974895659934181L;

    public ClosedCommand() {
        super(IDENTIFIER);
    }
}
