package com.canerkorkmaz.monopoly.domain.service;

import com.canerkorkmaz.monopoly.constants.Configuration;
import com.canerkorkmaz.monopoly.data.data.PlayerData;
import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.domain.data.InitialPlayerData;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PlayerRepository {
    // Some nice colors
    private static final Color[] colors = new Color[]{new Color(230, 25, 75), new Color(60, 180, 75), new Color(255, 225, 25), new Color(0, 130, 200), new Color(245, 130, 48), new Color(145, 30, 180), new Color(70, 240, 240), new Color(240, 50, 230), new Color(210, 245, 60), new Color(250, 190, 190), new Color(0, 128, 128), new Color(230, 190, 255), new Color(170, 110, 40), new Color(255, 250, 200), new Color(128, 0, 0), new Color(170, 255, 195), new Color(128, 128, 0), new Color(255, 215, 180)};
    private final Logger logger;
    private final LocalPlayerRepository localPlayerRepository;
    private final PlayerData playerData;
    private final ConnectionRepository connectionRepository;

    @Injected
    public PlayerRepository(ILoggerFactory loggerFactory,
                            LocalPlayerRepository localPlayerRepository,
                            PlayerData playerData,
                            ConnectionRepository connectionRepository) {
        this.logger = loggerFactory.createLogger(PlayerRepository.class);
        this.localPlayerRepository = localPlayerRepository;
        this.playerData = playerData;
        this.connectionRepository = connectionRepository;
    }

    private static List<Color> getRandomColorStream(int i) {
        return Stream.of(colors)
                .limit(i)
                .collect(Collectors.toList());
    }

    public List<InitialPlayerData> initPlayerData(List<String> names) {
        List<String> localNames = Arrays.asList(localPlayerRepository.getLocalPlayerNames());
        List<Color> playerColors = getRandomColorStream(names.size());
        return IntStream.range(0, names.size())
                .mapToObj(i -> {
                    String name = names.get(i);
                    Color color = playerColors.get(i);
                    return new InitialPlayerData(name, Configuration.INITIAL_MONEY, color, localNames.contains(name) ? 0 : 1);
                })
                .collect(Collectors.toList());
    }

    public void initPlayersFromData(List<InitialPlayerData> playerDatas) {
        Map<String, PlayerModel> players = playerDatas.stream()
                .map(x -> new PlayerModel(x.getPlayerName(), x.getMoney(), x.getPlayerColor(), x.getOrigin()))
                .collect(Collectors.toMap(PlayerModel::getPlayerName, x -> x));
        playerData.setPlayerList(players);
        List<String> initialOrder = playerDatas.stream()
                .map(InitialPlayerData::getPlayerName)
                .collect(Collectors.toList());
        playerData.setPlayerOrder(initialOrder);
    }

    public void setInitialRoll(String name, int roll1, int roll2) {
        playerData.getPlayer(name)
                .setInitialRoll(roll1, roll2);
    }

    public List<PlayerModel> getPlayerList() {
        return playerData.getPlayerOrderedList();
    }

    public int getPlayerCount() {
        return playerData.getPlayerOrderedList().size();
    }

    public void sortAccordingToInitialRoll() {
        List<String> order = playerData.getPlayerList().values().stream()
                .sorted((p1, p2) -> p2.getInitialRoll() - p1.getInitialRoll())
                .map(PlayerModel::getPlayerName)
                .collect(Collectors.toList());

        playerData.setPlayerOrder(order);
    }

    public boolean isFromThisClient(PlayerModel model) {
        logger.i("" + model.getOrigin() + " == " + connectionRepository.getOrigin());
        return model.getOrigin() == connectionRepository.getOrigin();
    }
}
