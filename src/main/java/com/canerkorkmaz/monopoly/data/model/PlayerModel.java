package com.canerkorkmaz.monopoly.data.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private String rollAgainMessage = null;
    private boolean nextTurnReverse = false;
    private boolean shouldSqueeze = false;
    private boolean shouldRollOnce = false;
    private int rollOnceRoll = 0;
    private final List<PropertyTileModel> tiles = new ArrayList<>();
    private boolean canBuyProperty = false;
    private PropertyTileModel buyableProperty = null;
    private String message = null;


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
        this.rollAgainMessage = null;
        this.shouldRollAgain = value;
    }
    public void setShouldRollAgain(boolean value, String message) {
        this.rollAgainMessage = message;
        this.shouldRollAgain = value;
    }

    public boolean getNextTurnReverse() {
        return this.nextTurnReverse;
    }

    public void setNextTurnReverse(boolean nextTurnReverse) {
        this.nextTurnReverse = nextTurnReverse;
    }

    public String getRollAgainMessage() {
        return rollAgainMessage;
    }

    public boolean isShouldSqueeze() {
        return shouldSqueeze;
    }

    public void setShouldSqueeze(boolean shouldSqueeze) {
        this.shouldSqueeze = shouldSqueeze;
    }

    public boolean isShouldRollOnce() {
        return shouldRollOnce;
    }

    public void setShouldRollOnce(boolean shouldRollOnce) {
        this.shouldRollOnce = shouldRollOnce;
    }

    public int getRollOnceRoll() {
        return rollOnceRoll;
    }

    public void setRollOnceRoll(int rollOnceRoll) {
        this.rollOnceRoll = rollOnceRoll;
    }

    public List<PropertyTileModel> getTiles() {
        return tiles;
    }

    public boolean isCanBuyProperty() {
        return this.canBuyProperty;
    }

    public void setCanBuyProperty(boolean canBuyProperty) {
        this.canBuyProperty = canBuyProperty;
    }

    public PropertyTileModel getBuyableProperty() {
        return buyableProperty;
    }

    public void setBuyableProperty(PropertyTileModel buyableProperty) {
        this.buyableProperty = buyableProperty;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
