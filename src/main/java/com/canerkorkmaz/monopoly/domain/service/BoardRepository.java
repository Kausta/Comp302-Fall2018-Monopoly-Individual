package com.canerkorkmaz.monopoly.domain.service;

import com.canerkorkmaz.monopoly.data.data.BoardData;
import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.data.model.TileModel;
import com.canerkorkmaz.monopoly.data.model.TileType;
import com.canerkorkmaz.monopoly.lib.di.Injected;

public class BoardRepository {
    private final BoardData data;
    private final PlayerRepository repository;

    @Injected
    public BoardRepository(BoardData data, PlayerRepository repository) {
        this.data = data;
        this.repository = repository;
    }

    public TileModel getBoardTile(int location) {
        return data.getTileModels()[location];
    }

    public TileType getTileType(int location) {
        return getBoardTile(location).getTileType();
    }

    public boolean isTileType(int location, TileType type) {
        return getTileType(location) == type;
    }

    public TileModel getBoardTile(PlayerModel model) {
        return this.getBoardTile(model.getLocation());
    }

    public TileType getTileType(PlayerModel model) {
        return this.getTileType(model.getLocation());
    }

    public boolean isTileType(PlayerModel model, TileType type) {
        return this.isTileType(model.getLocation(), type);
    }

    public void handleTilePass(PlayerModel model, int location) {
        TileModel tile = getBoardTile(location);
        tile.handlePass(model);
    }
}
