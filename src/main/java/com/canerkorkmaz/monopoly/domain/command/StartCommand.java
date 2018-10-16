package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.domain.data.InitialPlayerData;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

import java.util.List;

public class StartCommand extends BaseCommand {
    public static final String IDENTIFIER = "START";
    private final List<InitialPlayerData> startData;

    public StartCommand(List<InitialPlayerData> startData) {
        super(IDENTIFIER);
        this.startData = startData;
    }

    public List<InitialPlayerData> getStartData() {
        return startData;
    }

    @Override
    public String toString() {
        return "StartCommand{" +
                "startData=" + startData +
                '}';
    }
}
