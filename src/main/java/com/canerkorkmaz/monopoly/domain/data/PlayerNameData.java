package com.canerkorkmaz.monopoly.domain.data;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

import java.util.List;

public class PlayerNameData extends BaseCommand {
    public static final String IDENTIFIER = "PLAYER_NAMES";
    private final List<String> playerNames;

    public PlayerNameData(List<String> playerNames) {
        super(IDENTIFIER);
        this.playerNames = playerNames;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }
}
