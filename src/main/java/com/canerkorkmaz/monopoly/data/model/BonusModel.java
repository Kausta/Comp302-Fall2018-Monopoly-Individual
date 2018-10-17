package com.canerkorkmaz.monopoly.data.model;

public class BonusModel extends TileModel {
    private static final long serialVersionUID = 1L;
    private final double moneyGain = 250;
    private final double extraGain = 100;

    public BonusModel() {
        super(TileType.BONUS);
    }

    @Override
    public void handlePass(PlayerModel model) {
        model.setMoney(model.getMoney() + moneyGain);
    }

    public double getMoneyGain() {
        return moneyGain;
    }

    public double getExtraGain() {
        return extraGain;
    }
}
