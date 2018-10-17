package com.canerkorkmaz.monopoly.data.model;

public class KocSquareModel extends TileModel {
    private static final long serialVersionUID = 1L;

    public KocSquareModel() {
        super(TileType.KOC_SQUARE);
    }

    @Override
    public void handleLand(PlayerModel model) {
        model.setShouldRollAgain(true, "You landed on Koc Square with " + model.getRollString() + ", re-roll");
    }
}
