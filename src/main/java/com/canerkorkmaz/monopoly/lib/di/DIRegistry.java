package com.canerkorkmaz.monopoly.lib.di;

import com.canerkorkmaz.monopoly.data.model.ConnectionData;
import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.data.model.SocketConnection;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.logger.DefaultLoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;

public class DIRegistry {
    public DIRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        // Register Logger Factory
        registry.registerSingleton(ILoggerFactory.class, DefaultLoggerFactory.class);
        registry.registerSingleton(EventFactory.class);
        // Data Models
        registry.registerSingleton(LocalPlayerData.class);
        registry.registerSingleton(ConnectionData.class);
        registry.registerSingleton(SocketConnection.class);
        // Services
        registry.register(LocalPlayerRepository.class);
        registry.register(ConnectionRepository.class);
    }
}
