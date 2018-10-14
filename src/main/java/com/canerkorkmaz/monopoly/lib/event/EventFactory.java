package com.canerkorkmaz.monopoly.lib.event;

import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.threading.NamedThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.SwingThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.ViewModelThreadScheduler;

public class EventFactory {
    private final Logger logger;
    private final SwingThreadScheduler swingThreadScheduler;
    private final ViewModelThreadScheduler viewModelThreadScheduler;
    private final NamedThreadScheduler socketSendThreadScheduler;
    private final NamedThreadScheduler socketReceiveThreadScheduler;

    @Injected
    public EventFactory(ILoggerFactory loggerFactory) {
        this.logger = loggerFactory.createLogger(EventFactory.class);
        this.swingThreadScheduler = new SwingThreadScheduler();
        this.viewModelThreadScheduler = new ViewModelThreadScheduler();
        this.socketSendThreadScheduler = new NamedThreadScheduler(2, "Socket-Send");
        this.socketReceiveThreadScheduler = new NamedThreadScheduler(2, "Socket-Receive");
    }

    public <T> UIEvent<T> createUIEvent() {
        return new UIEvent<>(swingThreadScheduler);
    }

    public <T> Event<T> createVMEvent() {
        return new Event<>(viewModelThreadScheduler);
    }

    public <T> Event<T> createSocketSendEvent() {
        return new Event<>(socketSendThreadScheduler);
    }

    public <T> Event<T> createSocketReceiveEvent() {
        return new Event<>(socketReceiveThreadScheduler);
    }
}
