package com.canerkorkmaz.monopoly.lib.event;

import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.threading.SwingThreadScheduler;

public class UIEvent<T> extends Event<T> {

    @Injected
    public UIEvent(SwingThreadScheduler scheduler) {
        super(scheduler);
    }
}
