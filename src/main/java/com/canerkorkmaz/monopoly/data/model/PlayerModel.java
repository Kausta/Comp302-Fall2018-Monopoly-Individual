package com.canerkorkmaz.monopoly.data.model;

import java.awt.*;

public class PlayerModel {
    private String playerName;
    private double money;
    private int initialRoll = 0;
    private int initialRol2 = 0;
    private Color playerColor;
    private int origin;

    public PlayerModel(String playerName, double money, Color playerColor, int origin) {
        this.playerName = playerName;
        this.money = money;
        this.playerColor = playerColor;
        this.origin = origin;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isActive() {
        return money >= 0;
    }

    public int getInitialRoll() {
        return initialRoll + initialRol2;
    }

    public String getInitialRollString() {
        return "" + initialRoll + ", " + initialRol2;
    }

    public void setInitialRoll(int roll1, int roll2) {
        this.initialRoll = roll1;
        this.initialRol2 = roll2;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public Color getBackgroundColor() {
        return playerColor.brighter().brighter();
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }
}
