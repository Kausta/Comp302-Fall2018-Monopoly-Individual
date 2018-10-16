package com.canerkorkmaz.monopoly.data.data;

public class ConnectionData {
    private Mode mode;
    private String ip;
    private int port;

    public ConnectionData() {
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
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

    public static enum Mode {
        SERVER,
        CLIENT
    }
}
