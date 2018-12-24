package com.canerkorkmaz.monopoly.data.model;

import java.util.Random;

public class RollOnceModel extends TileModel {
    private static final long serialVersionUID = 1L;
    private static final Random r = new Random();

    public RollOnceModel() {
        super(TileType.ROLL_ONCE);
    }

    @Override
    public void handleLand(PlayerModel model) {
        model.setShouldRollOnce(true);
        model.setRollOnceRoll(r.nextInt(6) + 1);
    }
}
