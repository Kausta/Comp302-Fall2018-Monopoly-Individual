package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;

public class DITestRegistry {
    public DITestRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        registry.registerSingleton(ILoggerFactory.class, TestLoggerFactory.class);
    }
}
