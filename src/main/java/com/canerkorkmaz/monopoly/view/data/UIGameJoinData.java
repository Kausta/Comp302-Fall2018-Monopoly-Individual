package com.canerkorkmaz.monopoly.view.data;

public class UIGameJoinData {
    private final String ipAddress;
    private final int port;
    private final int numLocalPlayers;

    public UIGameJoinData(String ipAddress, int port, int numLocalPlayers) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.numLocalPlayers = numLocalPlayers;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public int getNumLocalPlayers() {
        return numLocalPlayers;
    }
}
