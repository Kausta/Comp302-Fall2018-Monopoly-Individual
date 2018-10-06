package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.lib.di.Injected;

public class LobbyViewModel {
    private LocalPlayerData configuration;

    @Injected
    public LobbyViewModel(LocalPlayerData configuration) {
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
}
