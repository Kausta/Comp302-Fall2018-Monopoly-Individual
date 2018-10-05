package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.data.GameConfiguration;
import com.canerkorkmaz.monopoly.di.impl.DefaultLoggerFactory;
import com.canerkorkmaz.monopoly.di.impl.SwingThreadScheduler;
import com.canerkorkmaz.monopoly.di.impl.ViewModelThreadScheduler;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.IThreadScheduler;

public class DIRegistry {
    public DIRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        // Register Logger Factory
        registry.registerSingleton(ILoggerFactory.class, DefaultLoggerFactory.class);
        // Thread Schedulers
        registry.registerSingleton(IThreadScheduler.class, ViewModelThreadScheduler.class);
        registry.registerSingleton(ViewModelThreadScheduler.class, ViewModelThreadScheduler.class);
        registry.registerSingleton(SwingThreadScheduler.class, SwingThreadScheduler.class);

        registry.registerSingleton(GameConfiguration.class, GameConfiguration.class);
    }
}
