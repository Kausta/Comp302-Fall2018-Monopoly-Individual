package com.canerkorkmaz.monopoly.data.model;

public class SqueezePlayModel extends TileModel {
    private static final long serialVersionUID = 1L;

    public SqueezePlayModel() {
        super(TileType.SQUEEZE_PLAY);
    }

    @Override
    public void handleLand(PlayerModel model) {
        model.setShouldSqueeze(true);
    }
}
