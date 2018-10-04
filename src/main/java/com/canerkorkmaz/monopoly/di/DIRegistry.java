package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.di.impl.LoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;

public class DIRegistry {
    public DIRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        // Register Logger Factory
        registry.registerSingleton(ILoggerFactory.class, LoggerFactory.class);
    }
}
