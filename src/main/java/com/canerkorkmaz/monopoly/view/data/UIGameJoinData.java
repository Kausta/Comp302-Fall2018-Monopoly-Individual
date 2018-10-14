package com.canerkorkmaz.monopoly.view.data;

public class UIGameJoinData {
    private final String ipAddress;
    private final int peerPort;
    private final int port;

    public UIGameJoinData(String ipAddress, int peerPort, int port) {
        this.ipAddress = ipAddress;
        this.peerPort = peerPort;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPeerPort() {
        return peerPort;
    }

    public int getPort() {
        return port;
    }
}
