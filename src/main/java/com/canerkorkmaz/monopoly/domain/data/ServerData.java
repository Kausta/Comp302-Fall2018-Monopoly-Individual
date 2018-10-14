package com.canerkorkmaz.monopoly.domain.data;

public class ServerData {
    private final int port;

    public ServerData(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
