package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameJoinData;

public class JoinPeerViewModel {
    private Logger logger;
    private UIEvent<Unit> successfullyJoined;
    private Event<UIGameJoinData> onJoinGameClick;

    @Injected
    public JoinPeerViewModel(ILoggerFactory loggerFactory,
                             UIEvent<Unit> successfullyCreated,
                             Event<UIGameJoinData> onCreateGameClick) {
        this.logger = loggerFactory.createLogger(JoinPeerViewModel.class);
        this.successfullyJoined = successfullyCreated;
        this.onJoinGameClick = onCreateGameClick;

        onCreateGameClick.runIfNotHandled((data) -> joinGame(data.getIpAddress(), data.getPeerPort(), data.getPort()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyJoined;
    }

    public Event<UIGameJoinData> getOnCreateGameClick() {
        return onJoinGameClick;
    }

    private void joinGame(String ip, int peerPort, int port) {
        logger.i(String.format("Joining %s:%s, starting on %s",
                ip,
                peerPort,
                port));
        successfullyJoined.trigger(Unit.INSTANCE);
    }
}
