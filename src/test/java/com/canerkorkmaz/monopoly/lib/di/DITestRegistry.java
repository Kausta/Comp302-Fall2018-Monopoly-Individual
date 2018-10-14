package com.canerkorkmaz.monopoly.lib.di;

import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;

public class DITestRegistry {
    public DITestRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        registry.registerSingleton(ILoggerFactory.class, TestLoggerFactory.class);
    }
}
