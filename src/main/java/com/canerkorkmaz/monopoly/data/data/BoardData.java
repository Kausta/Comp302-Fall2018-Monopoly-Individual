package com.canerkorkmaz.monopoly.data.data;

import com.canerkorkmaz.monopoly.constants.BoardConfiguration;
import com.canerkorkmaz.monopoly.data.model.TileModel;

public class BoardData {
    private final TileModel[] tileModels = BoardConfiguration.tileTypes;

    public BoardData() {
    }

    public TileModel[] getTileModels() {
        return tileModels;
    }
}
