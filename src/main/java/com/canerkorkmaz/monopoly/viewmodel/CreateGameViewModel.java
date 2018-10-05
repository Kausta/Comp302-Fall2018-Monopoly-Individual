package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.GameConfiguration;
import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UIGameCreationData;

public class CreateGameViewModel {
    private Logger logger;
    private UIEvent<Unit> successfullyCreated;
    private Event<UIGameCreationData> onCreateGameClick;
    private GameConfiguration configuration;

    @Injected
    public CreateGameViewModel(ILoggerFactory loggerFactory,
                               UIEvent<Unit> successfullyCreated,
                               Event<UIGameCreationData> onCreateGameClick,
                               GameConfiguration configuration) {
        this.logger = loggerFactory.createLogger(CreateGameViewModel.class);
        this.successfullyCreated = successfullyCreated;
        this.onCreateGameClick = onCreateGameClick;
        this.configuration = configuration;

        onCreateGameClick.runIfNotHandled((data) -> createGame(data.getPort(), data.getNumLocalPlayers()));
    }

    public UIEvent<Unit> getSuccessfullyCreated() {
        return this.successfullyCreated;
    }

    public Event<UIGameCreationData> getOnCreateGameClick() {
        return onCreateGameClick;
    }

    private void createGame(int port, int numLocalPlayers) {
        logger.i(String.format("Starting on %s with %s local players",
                port,
                numLocalPlayers));
        configuration.setServerMode(true);
        configuration.setPort(port);
        configuration.setNumLocalPlayers(numLocalPlayers);
        successfullyCreated.trigger(Unit.INSTANCE);
    }
}
