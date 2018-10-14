package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.data.ClientData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameJoinData;

public class JoinPeerViewModel {
    private final Logger logger;
    private final ConnectionRepository repository;
    private final UIEvent<Unit> successfullyJoined;
    private final UIEvent<String> errorOccurred;
    private final Event<UIGameJoinData> onJoinGameClick;

    @Injected
    public JoinPeerViewModel(ILoggerFactory loggerFactory,
                             ConnectionRepository repository,
                             EventFactory eventFactory) {
        this.logger = loggerFactory.createLogger(JoinPeerViewModel.class);
        this.repository = repository;
        this.successfullyJoined = eventFactory.createUIEvent();
        this.errorOccurred = eventFactory.createUIEvent();
        this.onJoinGameClick = eventFactory.createVMEvent();

        onJoinGameClick.subscribe((data) -> joinGame(data.getIpAddress(), data.getPeerPort(), data.getPort()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyJoined;
    }

    public UIEvent<String> getErrorOccurred() {
        return this.errorOccurred;
    }

    public Event<UIGameJoinData> getOnCreateGameClick() {
        return onJoinGameClick;
    }

    private void joinGame(String ip, int peerPort, int port) {
        logger.i(String.format("Joining %s:%s, starting on %s",
                ip,
                peerPort,
                port));
        try {
            this.repository.setClientData(new ClientData(ip, peerPort));
            this.repository.start();
        } catch (Exception e) {
            this.errorOccurred.trigger(e.getMessage());
        }
        successfullyJoined.trigger(Unit.INSTANCE);
    }
}
