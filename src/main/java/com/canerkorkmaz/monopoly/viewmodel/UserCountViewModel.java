package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.data.UICountData;
import com.canerkorkmaz.monopoly.view.data.UINameData;

public class UserCountViewModel {
    private final LocalPlayerRepository configuration;
    private final UIEvent<Unit> successfullySetCount;
    private final Event<UICountData> onContinueClick;

    @Injected
    public UserCountViewModel(LocalPlayerRepository configuration,
                              UIEvent<Unit> successfullySetCount,
                              Event<UICountData> onContinueClick) {
        this.configuration = configuration;
        this.successfullySetCount = successfullySetCount;
        this.onContinueClick = onContinueClick;

        this.onContinueClick.runIfNotHandled((data) -> this.setPlayerCount(data.getPlayerCount()));
    }

    public UIEvent<Unit> getSuccessfullySetCount() {
        return successfullySetCount;
    }

    public Event<UICountData> getOnContinueClick() {
        return onContinueClick;
    }

    private void setPlayerCount(int count) {
        this.configuration.setLocalPlayerCount(count);
        this.successfullySetCount.trigger(Unit.INSTANCE);
    }
}
