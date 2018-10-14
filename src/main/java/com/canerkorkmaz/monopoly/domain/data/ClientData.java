package com.canerkorkmaz.monopoly.domain.data;

public class ClientData {
    private final String ip;
    private final int port;

    public ClientData(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
