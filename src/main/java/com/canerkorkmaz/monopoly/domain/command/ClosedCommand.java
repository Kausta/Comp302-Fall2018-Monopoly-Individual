package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class ClosedCommand extends BaseCommand {
    public static final String IDENTIFIER = "CLOSED";

    public ClosedCommand() {
        super(IDENTIFIER);
    }
}
