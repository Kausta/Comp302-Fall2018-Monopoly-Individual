package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.GameConfiguration;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameJoinData;

public class JoinGameViewModel {
    private Logger logger;
    private UIEvent<Unit> successfullyJoined;
    private Event<UIGameJoinData> onJoinGameClick;
    private GameConfiguration configuration;

    @Injected
    public JoinGameViewModel(ILoggerFactory loggerFactory,
                             UIEvent<Unit> successfullyCreated,
                             Event<UIGameJoinData> onCreateGameClick,
                             GameConfiguration configuration) {
        this.logger = loggerFactory.createLogger(JoinGameViewModel.class);
        this.successfullyJoined = successfullyCreated;
        this.onJoinGameClick = onCreateGameClick;
        this.configuration = configuration;

        onCreateGameClick.runIfNotHandled((data) -> joinGame(data.getIpAddress(), data.getPort(), data.getNumLocalPlayers()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyJoined;
    }

    public Event<UIGameJoinData> getOnCreateGameClick() {
        return onJoinGameClick;
    }

    private void joinGame(String ip, int port, int numLocalPlayers) {
        logger.i(String.format("Joining %s:%s with %s local players",
                ip,
                port,
                numLocalPlayers));
        configuration.setServerMode(false);
        configuration.setIp(ip);
        configuration.setPort(port);
        configuration.setNumLocalPlayers(numLocalPlayers);
        successfullyJoined.trigger(Unit.INSTANCE);
    }
}
