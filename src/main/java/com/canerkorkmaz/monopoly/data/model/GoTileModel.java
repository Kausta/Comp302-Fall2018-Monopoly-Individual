package com.canerkorkmaz.monopoly.data.model;

import java.io.Serializable;

public class GoTileModel extends TileModel {
    private static final long serialVersionUID = 1L;
    private final double moneyGain = 200;

    public GoTileModel() {
        super(TileType.GO_TILE);
    }

    @Override
    public void handlePass(PlayerModel model) {
        model.setMoney(model.getMoney() + getMoneyGain());
    }

    public double getMoneyGain() {
        return moneyGain;
    }
}
