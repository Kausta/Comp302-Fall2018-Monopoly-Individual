package com.canerkorkmaz.monopoly.domain.data;

import java.awt.*;
import java.io.Serializable;

public class InitialPlayerData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String playerName;
    private final double money;
    private final Color playerColor;
    private final int origin;

    public InitialPlayerData(String playerName, double money, Color playerColor, int origin) {
        this.playerName = playerName;
        this.money = money;
        this.playerColor = playerColor;
        this.origin = origin;
    }

    public String getPlayerName() {
        return playerName;
    }

    public double getMoney() {
        return money;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public int getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "InitialPlayerData{" +
                "playerName='" + playerName + '\'' +
                ", money=" + money +
                ", playerColor=" + playerColor +
                ", origin=" + origin +
                '}';
    }
}
