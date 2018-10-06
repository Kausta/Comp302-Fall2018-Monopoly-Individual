package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameCreationData;

public class CreateGameViewModel {
    private Logger logger;
    private UIEvent<Unit> successfullyCreated;
    private Event<UIGameCreationData> onCreateGameClick;

    @Injected
    public CreateGameViewModel(ILoggerFactory loggerFactory,
                               UIEvent<Unit> successfullyCreated,
                               Event<UIGameCreationData> onCreateGameClick) {
        this.logger = loggerFactory.createLogger(CreateGameViewModel.class);
        this.successfullyCreated = successfullyCreated;
        this.onCreateGameClick = onCreateGameClick;

        onCreateGameClick.runIfNotHandled((data) -> createGame(data.getPort()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyCreated;
    }

    public Event<UIGameCreationData> getOnCreateGameClick() {
        return onCreateGameClick;
    }

    private void createGame(int port) {
        logger.i(String.format("Starting on %s",
                port));
        successfullyCreated.trigger(Unit.INSTANCE);
    }
}
