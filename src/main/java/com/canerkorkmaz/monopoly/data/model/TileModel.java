package com.canerkorkmaz.monopoly.data.model;

import java.io.Serializable;

public class TileModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private TileType tileType;

    public TileModel(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void handlePass(PlayerModel model) {

    }
}
