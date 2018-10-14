package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.data.ServerData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameCreationData;

public class CreateGameViewModel {
    private final Logger logger;
    private final ConnectionRepository repository;
    private final UIEvent<Unit> successfullyCreated;
    private final UIEvent<String> errorOccurred;
    private final Event<UIGameCreationData> onCreateGameClick;

    @Injected
    public CreateGameViewModel(ILoggerFactory loggerFactory,
                               ConnectionRepository repository,
                               EventFactory eventFactory) {
        this.logger = loggerFactory.createLogger(CreateGameViewModel.class);
        this.repository = repository;
        this.successfullyCreated = eventFactory.createUIEvent();
        this.errorOccurred = eventFactory.createUIEvent();
        this.onCreateGameClick = eventFactory.createVMEvent();

        onCreateGameClick.subscribe((data) -> createGame(data.getPort()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyCreated;
    }

    public UIEvent<String> getErrorOccurred() {
        return this.errorOccurred;
    }

    public Event<UIGameCreationData> getOnCreateGameClick() {
        return onCreateGameClick;
    }

    private void createGame(int port) {
        logger.i(String.format("Starting on %s",
                port));
        try {
            this.repository.setServerData(new ServerData(port));
            this.repository.start();
        } catch (Exception e) {
            this.errorOccurred.trigger(e.getMessage());
        }
        successfullyCreated.trigger(Unit.INSTANCE);
    }
}
