package com.canerkorkmaz.monopoly.data.model;

public class GoTileModel extends TileModel {
    private static final long serialVersionUID = 1L;
    private static final double moneyGain = 200;

    public GoTileModel() {
        super(TileType.GO_TILE);
    }

    @Override
    public void handlePass(PlayerModel model) {
        model.setMoney(model.getMoney() + getMoneyGain());
    }

    @Override
    public void handleLand(PlayerModel model) {
        this.handlePass(model);
    }

    public double getMoneyGain() {
        return moneyGain;
    }
}
