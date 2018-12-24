package com.canerkorkmaz.monopoly.data.model;

public class BonusModel extends TileModel {
    private static final long serialVersionUID = 1L;
    private final static double moneyGain = 250;
    private final static double extraGain = 50;

    public BonusModel() {
        super(TileType.BONUS);
    }

    @Override
    public void handlePass(PlayerModel model) {
        model.setMoney(model.getMoney() + moneyGain);
    }

    @Override
    public void handleLand(PlayerModel model) {
        this.handlePass(model);
        model.setMoney(model.getMoney() + extraGain);
    }

    public double getMoneyGain() {
        return moneyGain;
    }

    public double getExtraGain() {
        return extraGain;
    }
}
