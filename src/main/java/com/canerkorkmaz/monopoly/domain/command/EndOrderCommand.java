package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class EndOrderCommand extends BaseCommand {
    public static final String IDENTIFIER = "END_ROLL_COMMAND";

    private final int playerIndex;

    public EndOrderCommand(int playerIndex) {
        super(IDENTIFIER);
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
