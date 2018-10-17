package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class RollOnceCommand extends BaseCommand {
    public static final String IDENTIFIER = "ROLL_ONCE_COMMAND";
    private static final long serialVersionUID = 1L;

    private final int playerRoll;
    private final int cardRoll;

    public RollOnceCommand(int playerRoll, int cardRoll) {
        super(IDENTIFIER);
        this.playerRoll = playerRoll;
        this.cardRoll = cardRoll;
    }

    public int getPlayerRoll() {
        return playerRoll;
    }

    public int getCardRoll() {
        return cardRoll;
    }
}
