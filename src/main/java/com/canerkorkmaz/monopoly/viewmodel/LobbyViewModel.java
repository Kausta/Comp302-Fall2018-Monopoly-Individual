package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.data.LocalPlayerData;
import com.canerkorkmaz.monopoly.domain.command.ClosedCommand;
import com.canerkorkmaz.monopoly.domain.command.StartCommand;
import com.canerkorkmaz.monopoly.domain.data.InitialPlayerData;
import com.canerkorkmaz.monopoly.domain.data.PlayerNameData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.domain.service.PlayerRepository;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyViewModel {
    private final Logger logger;
    private final LocalPlayerData configuration;
    private final ConnectionRepository connectionRepository;
    private final PlayerRepository playerRepository;
    private final UIEvent<Boolean> successOrFailure;
    private final UIEvent<ArrayList<String>> userNamesChanged;
    private final Event<Unit> startGame;
    private final ArrayList<String> playerNames = new ArrayList<>();

    @Injected
    public LobbyViewModel(ILoggerFactory logger,
                          LocalPlayerData configuration,
                          ConnectionRepository connectionRepository,
                          PlayerRepository playerRepository,
                          EventFactory eventFactory) {
        this.logger = logger.createLogger(LobbyViewModel.class);
        this.configuration = configuration;
        this.connectionRepository = connectionRepository;
        this.playerRepository = playerRepository;
        this.successOrFailure = eventFactory.createUIEvent();
        this.userNamesChanged = eventFactory.createUIEvent();
        this.startGame = eventFactory.createVMEvent();

    }

    public void initLobby() {
        playerNames.addAll(Arrays.asList(configuration.getLocalPlayerNames()));
        userNamesChanged.trigger(this.playerNames);

        if (isServerMode()) {
            connectionRepository.receiveRemoteOnce(this::onServerReceive);
        } else {
            connectionRepository.receiveRemoteOnce(this::onClientReceive);
            connectionRepository.sendRemote(new PlayerNameData(Arrays.asList(configuration.getLocalPlayerNames())));
        }
        startGame.subscribe((unit) -> {
            List<InitialPlayerData> playerData = playerRepository.initPlayerData(playerNames);
            connectionRepository.sendRemote(new StartCommand(playerData));
            playerRepository.initPlayersFromData(playerData);
            connectionRepository.setGameStarted(true);
            this.successOrFailure.trigger(true);
        });
    }

    private boolean onClientReceive(BaseCommand command) {
        logger.i("Got " + command.toString());
        switch (command.getIdentifier()) {
            case PlayerNameData.IDENTIFIER:
                playerNames.clear();
                PlayerNameData data = (PlayerNameData) command;
                playerNames.addAll(data.getPlayerNames());
                userNamesChanged.trigger(this.playerNames);
                break;
            case StartCommand.IDENTIFIER:
                List<InitialPlayerData> startData = ((StartCommand) command).getStartData();
                connectionRepository.setGameStarted(true);
                playerRepository.initPlayersFromData(startData);
                successOrFailure.trigger(true);
                return true;
            case ClosedCommand.IDENTIFIER:
                successOrFailure.trigger(false);
                return true;
            default:
                break;
        }
        return false;
    }

    private boolean onServerReceive(BaseCommand command) {
        logger.i("Got " + command.toString());
        switch (command.getIdentifier()) {
            case PlayerNameData.IDENTIFIER:
                PlayerNameData data = (PlayerNameData) command;
                List<String> newPlayerNames = data.getPlayerNames();
                for (String newPlayer : newPlayerNames) {
                    String name = newPlayer;
                    if (playerNames.contains(newPlayer)) {
                        for (int i = 1; ; i++) {
                            if (!playerNames.contains(newPlayer + i)) {
                                break;
                            }
                            name = newPlayer + i;
                        }
                    }
                    playerNames.add(name);
                }
                userNamesChanged.trigger(this.playerNames);
                // So that no one can join now, as we're interested in one master one client only
                connectionRepository.setGameStarted(true);
                connectionRepository.sendRemote(new PlayerNameData(playerNames));
                return true;
            case ClosedCommand.IDENTIFIER:
                playerNames.clear();
                playerNames.addAll(Arrays.asList(configuration.getLocalPlayerNames()));
                connectionRepository.setGameStarted(false);
                return false;
            default:
                break;
        }
        return false;
    }

    public UIEvent<Boolean> getSuccessOrFailure() {
        return successOrFailure;
    }

    public boolean isServerMode() {
        return connectionRepository.isServer();
    }

    public Event<Unit> getStartGame() {
        return startGame;
    }

    public UIEvent<ArrayList<String>> getUserNamesChanged() {
        return userNamesChanged;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }
}

