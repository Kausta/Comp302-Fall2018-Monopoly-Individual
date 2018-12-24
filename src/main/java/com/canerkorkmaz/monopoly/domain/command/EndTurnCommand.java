package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class EndTurnCommand extends BaseCommand {
    public static final String IDENTIFIER = "END_TURN_COMMAND";
    private static final long serialVersionUID = -6809116315989126894L;

    private final int playerIndex;

    public EndTurnCommand(int playerIndex) {
        super(IDENTIFIER);
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
