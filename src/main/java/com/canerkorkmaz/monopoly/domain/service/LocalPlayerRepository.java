package com.canerkorkmaz.monopoly.domain.service;

import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

public class LocalPlayerRepository {
    private final Logger logger;
    private final LocalPlayerData localPlayerData;

    @Injected
    public LocalPlayerRepository(ILoggerFactory loggerFactory, LocalPlayerData data) {
        this.logger = loggerFactory.createLogger(LocalPlayerRepository.class);
        this.localPlayerData = data;
    }

    public int getLocalPlayerCount() {
        return this.localPlayerData.getNumLocalPlayers();
    }

    public void setLocalPlayerCount(int localPlayerCount) {
        int previous = this.localPlayerData.getNumLocalPlayers();
        this.localPlayerData.setNumLocalPlayers(localPlayerCount);
        // Persist old names while changing size
        String[] oldNames = this.localPlayerData.getLocalPlayerNames();
        String[] newNames = new String[localPlayerCount];
        if (oldNames != null) {
            int minSize = Math.min(previous, localPlayerCount);
            if (minSize >= 0) System.arraycopy(oldNames, 0, newNames, 0, minSize);
        }
        this.localPlayerData.setLocalPlayerNames(newNames);
    }

    public String[] getLocalPlayerNames() {
        return this.localPlayerData.getLocalPlayerNames();
    }

    public void setLocalPlayerNames(String[] names) {
        String[] configurationNames = this.localPlayerData.getLocalPlayerNames();
        if (configurationNames.length != names.length) {
            throw new RuntimeException("Problem, configuration is out of sync");
        }
        System.arraycopy(names, 0, configurationNames, 0, configurationNames.length);
    }
}
