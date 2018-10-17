package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class EndMovementCommand extends BaseCommand {
    public static final String IDENTIFIER = "END_MOVEMENT";
    private static final long serialVersionUID = 1L;

    public EndMovementCommand() {
        super(IDENTIFIER);
    }
}
