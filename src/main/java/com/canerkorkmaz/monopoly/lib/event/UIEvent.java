package com.canerkorkmaz.monopoly.lib.event;

import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.di.impl.SwingThreadScheduler;

public class UIEvent<T> extends Event<T> {

    @Injected
    public UIEvent(SwingThreadScheduler scheduler) {
        super(scheduler);
    }
}
