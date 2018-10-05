package com.canerkorkmaz.monopoly.data;

import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

// TODO: Repository and use case between data configuration and view models
public class GameConfiguration {
    private final Logger logger;

    private boolean serverMode;
    private String ip;
    private int port;
    private int numLocalPlayers;
    private String[] localPlayerNames;

    @Injected
    public GameConfiguration(ILoggerFactory loggerFactory) {
        this.logger = loggerFactory.createLogger(GameConfiguration.class);
    }

    public boolean isServerMode() {
        return serverMode;
    }

    public void setServerMode(boolean serverMode) {
        this.serverMode = serverMode;
        if (this.serverMode) {
            this.ip = null;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNumLocalPlayers() {
        return numLocalPlayers;
    }

    public void setNumLocalPlayers(int numLocalPlayers) {
        this.numLocalPlayers = numLocalPlayers;
        this.localPlayerNames = new String[numLocalPlayers];
    }

    public String[] getLocalPlayerNames() {
        return this.localPlayerNames;
    }
}
