package com.canerkorkmaz.monopoly.view.data;

public class UIGameCreationData {
    private final int port;
    private final int numLocalPlayers;

    public UIGameCreationData(int port, int numLocalPlayers) {
        this.port = port;
        this.numLocalPlayers = numLocalPlayers;
    }

    public int getPort() {
        return port;
    }

    public int getNumLocalPlayers() {
        return numLocalPlayers;
    }
}
