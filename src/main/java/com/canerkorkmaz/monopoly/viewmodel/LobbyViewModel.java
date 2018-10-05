package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.GameConfiguration;
import com.canerkorkmaz.monopoly.di.Injected;

public class LobbyViewModel {
    private GameConfiguration configuration;

    @Injected
    public LobbyViewModel(GameConfiguration configuration) {
        this.configuration = configuration;
    }

    public String[] getPlayerNames() {
        String[] userNames = this.configuration.getLocalPlayerNames();
        String[] convertedNames = new String[userNames.length];
        for (int i = 0; i < userNames.length; i++) {
            convertedNames[i] = "Local User " + (i + 1) + " - " + userNames[i];
        }
        return convertedNames;
    }

    public boolean isServerMode() {
        return this.configuration.isServerMode();
    }
}
