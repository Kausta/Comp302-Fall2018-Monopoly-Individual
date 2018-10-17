package com.canerkorkmaz.monopoly.data.model;

import java.awt.*;

public class PlayerModel {
    // Name and color
    private String playerName;
    private Color playerColor;
    // Player origin (as connected client) and initial ordering rolls
    private int origin;
    private int initialRoll = 0;
    private int initialRol2 = 0;
    private int initialRollSum = 0;
    // State variables
    private double money;
    private int location = 10;
    private int order = 0;
    private int roll1 = 0;
    private int roll2 = 0;
    private boolean shouldRollAgain = false;

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
        return initialRollSum;
    }

    public String getInitialRollString() {
        return "" + initialRoll + ", " + initialRol2;
    }

    public void setInitialRoll(int roll1) {
        this.initialRollSum = roll1;
    }

    public void setInitialRoll(int roll1, int roll2) {
        this.initialRoll = roll1;
        this.initialRol2 = roll2;
        this.initialRollSum = roll1 + roll2;
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

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRoll() {
        return roll1 + roll2;
    }

    public String getRollString() {
        return "" + roll1 + ", " + roll2;
    }

    public void setRoll(int x1, int x2) {
        this.roll1 = x1;
        this.roll2 = x2;
    }

    public boolean shouldRollAgain() {
        return shouldRollAgain;
    }
    public void setShouldRollAgain(boolean value) {
        this.shouldRollAgain = value;
    }
}
