package com.canerkorkmaz.monopoly.data.data;

import com.canerkorkmaz.monopoly.constants.BoardConfiguration;
import com.canerkorkmaz.monopoly.data.model.TileModel;

import java.util.List;

public class BoardData {
    private final List<TileModel> tileModels = BoardConfiguration.tileTypes;

    public BoardData() {
    }

    public List<TileModel> getTileModels() {
        return tileModels;
    }
}
