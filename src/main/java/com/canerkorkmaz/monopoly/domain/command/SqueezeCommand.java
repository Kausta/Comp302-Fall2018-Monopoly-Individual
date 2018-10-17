package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class SqueezeCommand extends BaseCommand {
    public static final String IDENTIFIER = "SQUEEZE_COMMAND";
    private static final long serialVersionUID = 1L;

    private final int roll1;
    private final int roll2;

    public SqueezeCommand(int roll1, int roll2) {
        super(IDENTIFIER);
        this.roll1 = roll1;
        this.roll2 = roll2;
    }

    public int getRoll1() {
        return roll1;
    }

    public int getRoll2() {
        return roll2;
    }
}
