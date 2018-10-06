package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UINameData;

public class UserNamesViewModel {
    private final LocalPlayerRepository configuration;
    private final UIEvent<Unit> successfullySetNames;
    private final Event<UINameData> onContinueClick;
    private final Event<UINameData> onBackClick;

    @Injected
    public UserNamesViewModel(LocalPlayerRepository configuration,
                              UIEvent<Unit> successfullySetNames,
                              Event<UINameData> onContinueClick,
                              Event<UINameData> onBackClick) {
        this.configuration = configuration;
        this.successfullySetNames = successfullySetNames;
        this.onContinueClick = onContinueClick;
        this.onBackClick = onBackClick;

        this.onContinueClick.runIfNotHandled((data) -> this.setNames(data.getNames(), true));
        this.onBackClick.runIfNotHandled((data) -> this.setNames(data.getNames(), false));
    }

    public int getPlayerCount() {
        return this.configuration.getLocalPlayerCount();
    }

    public String[] getUserNames() {
        String[] names = this.configuration.getLocalPlayerNames();
        for(int i = 0;i < names.length;i++){
            if(names[i] == null) {
                names[i] = "";
            }
        }
        return names;
    }

    public UIEvent<Unit> getSuccessfullySetNames() {
        return successfullySetNames;
    }

    public Event<UINameData> getOnContinueClick() {
        return onContinueClick;
    }

    public Event<UINameData> getOnBackClick() {
        return onBackClick;
    }

    private void setNames(String[] names, boolean callSuccessful) {
        this.configuration.setLocalPlayerNames(names);
        if(callSuccessful) {
            this.successfullySetNames.trigger(Unit.INSTANCE);
        }
    }
}
