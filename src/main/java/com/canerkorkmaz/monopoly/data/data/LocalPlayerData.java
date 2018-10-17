package com.canerkorkmaz.monopoly.data.data;

public class LocalPlayerData {
    private int numLocalPlayers;
    private String[] localPlayerNames;

    public LocalPlayerData() {
    }

    public int getNumLocalPlayers() {
        return numLocalPlayers;
    }

    public void setNumLocalPlayers(int numLocalPlayers) {
        this.numLocalPlayers = numLocalPlayers;
    }

    public String[] getLocalPlayerNames() {
        return this.localPlayerNames;
    }

    public void setLocalPlayerNames(String[] localPlayerNames) {
        this.localPlayerNames = localPlayerNames;
    }
}
