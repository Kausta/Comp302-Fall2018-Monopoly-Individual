package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.GameConfiguration;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UINameData;

public class UserNamesViewModel {
    private final GameConfiguration configuration;
    private final UIEvent<Unit> successfullySetNames;
    private final Event<UINameData> onContinueClick;

    @Injected
    public UserNamesViewModel(GameConfiguration configuration,
                              UIEvent<Unit> successfullySetNames,
                              Event<UINameData> onContinueClick) {
        this.configuration = configuration;
        this.successfullySetNames = successfullySetNames;
        this.onContinueClick = onContinueClick;

        this.onContinueClick.runIfNotHandled((data) -> this.setNames(data.getNames()));
    }

    public int getPlayerCount() {
        return this.configuration.getNumLocalPlayers();
    }

    public UIEvent<Unit> getSuccessfullySetNames() {
        return successfullySetNames;
    }

    public Event<UINameData> getOnContinueClick() {
        return onContinueClick;
    }

    private void setNames(String[] names) {
        String[] configurationNames = this.configuration.getLocalPlayerNames();
        if (configurationNames.length != names.length) {
            throw new RuntimeException("Problem, configuration is out of sync");
        }
        System.arraycopy(names, 0, configurationNames, 0, configurationNames.length);
        this.successfullySetNames.trigger(Unit.INSTANCE);
    }
}
