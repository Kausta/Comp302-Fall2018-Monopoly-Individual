package com.canerkorkmaz.monopoly.data.data;

import com.canerkorkmaz.monopoly.data.model.PlayerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerData {
    private List<String> playerOrder = new ArrayList<>();
    private Map<String, PlayerModel> playerList = new HashMap<>();
    private List<PlayerModel> playerOrderedList = null;

    public PlayerData() {
    }

    public PlayerModel getPlayer(String name) {
        return playerList.get(name);
    }

    public Map<String, PlayerModel> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Map<String, PlayerModel> playerList) {
        this.playerList = playerList;
    }

    public List<String> getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(List<String> playerOrder) {
        this.playerOrder = playerOrder;
        playerOrderedList = null;
    }

    public List<PlayerModel> getPlayerOrderedList() {
        playerOrderedList = playerOrder.stream()
                .map(name -> playerList.get(name))
                .collect(Collectors.toList());
        return playerOrderedList;
    }
}
