package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class RollCommand extends BaseCommand {
    public static final String IDENTIFIER = "ROLL_COMMAND";

    private final String playerName;
    private final int roll1;
    private final int roll2;

    public RollCommand(String playerName, int roll1, int roll2) {
        super(IDENTIFIER);
        this.playerName = playerName;
        this.roll1 = roll1;
        this.roll2 = roll2;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getRoll1() {
        return roll1;
    }

    public int getRoll2() {
        return roll2;
    }
}
