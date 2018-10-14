package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.domain.data.PlayerNameData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
import com.canerkorkmaz.monopoly.lib.command.StartCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;

import java.util.ArrayList;
import java.util.Arrays;

public class LobbyViewModel {
    private final LocalPlayerData configuration;
    private final ConnectionRepository connectionRepository;
    private final UIEvent<Boolean> successOrFailure;
    private final UIEvent<ArrayList<String>> userNamesChanged;
    private final Event<Unit> startGame;
    private final ArrayList<String> playerNames = new ArrayList<>();

    @Injected
    public LobbyViewModel(LocalPlayerData configuration,
                          ConnectionRepository connectionRepository,
                          EventFactory eventFactory) {
        this.configuration = configuration;
        this.connectionRepository = connectionRepository;
        this.successOrFailure = eventFactory.createUIEvent();
        this.userNamesChanged = eventFactory.createUIEvent();
        this.startGame = eventFactory.createVMEvent();
        playerNames.addAll(Arrays.asList(configuration.getLocalPlayerNames()));
        userNamesChanged.trigger(this.playerNames);

        if (isServerMode()) {
            connectionRepository.getOnReceive().subscribeOnce(this::onServerReceive);
        } else {
            connectionRepository.send(new PlayerNameData(Arrays.asList(configuration.getLocalPlayerNames())));
            connectionRepository.getOnReceive().subscribeOnce(this::onClientReceive);
        }
        startGame.subscribe((unit) -> {
            connectionRepository.send(new StartCommand());
            this.successOrFailure.trigger(true);
        });
    }

    private void onClientReceive(BaseCommand command) {
        switch (command.getIdentifier()) {
            case PlayerNameData.IDENTIFIER:
                playerNames.clear();
                PlayerNameData data = (PlayerNameData) command;
                playerNames.addAll(data.getPlayerNames());
                userNamesChanged.trigger(this.playerNames);
                connectionRepository.getOnReceive().subscribeOnce(this::onClientWaitStart);
                break;
            case ClosedCommand.IDENTIFIER:
                successOrFailure.trigger(false);
                break;
            default:
                connectionRepository.getOnReceive().subscribeOnce(this::onClientReceive);
                break;
        }
    }

    private void onClientWaitStart(BaseCommand command) {
        switch (command.getIdentifier()) {
            case StartCommand.IDENTIFIER:
                successOrFailure.trigger(true);
                break;
            case ClosedCommand.IDENTIFIER:
                successOrFailure.trigger(false);
                break;
            default:
                connectionRepository.getOnReceive().subscribeOnce(this::onClientReceive);
                break;
        }
    }

    private void onServerReceive(BaseCommand command) {
        switch (command.getIdentifier()) {
            case PlayerNameData.IDENTIFIER:
                PlayerNameData data = (PlayerNameData) command;
                playerNames.addAll(data.getPlayerNames());
                userNamesChanged.trigger(this.playerNames);
                // So that no one can join now, as we're interested in one master one client only
                connectionRepository.setGameStarted(true);
                connectionRepository.send(new PlayerNameData(playerNames));
                break;
            case ClosedCommand.IDENTIFIER:
                playerNames.clear();
                playerNames.addAll(Arrays.asList(configuration.getLocalPlayerNames()));
                connectionRepository.getOnReceive().subscribeOnce(this::onServerReceive);
                connectionRepository.setGameStarted(false);
                break;
            default:
                connectionRepository.getOnReceive().subscribeOnce(this::onServerReceive);
                break;
        }
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

