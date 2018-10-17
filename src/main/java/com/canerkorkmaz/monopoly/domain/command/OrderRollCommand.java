package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class OrderRollCommand extends BaseCommand {
    public static final String IDENTIFIER = "ORDER_ROLL_COMMAND";

    private final String playerName;
    private final int initialRoll1;
    private final int initialRoll2;

    public OrderRollCommand(String playerName, int initialRoll1, int initialRoll2) {
        super(IDENTIFIER);
        this.playerName = playerName;
        this.initialRoll1 = initialRoll1;
        this.initialRoll2 = initialRoll2;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getInitialRoll1() {
        return initialRoll1;
    }

    public int getInitialRoll2() {
        return initialRoll2;
    }
}
