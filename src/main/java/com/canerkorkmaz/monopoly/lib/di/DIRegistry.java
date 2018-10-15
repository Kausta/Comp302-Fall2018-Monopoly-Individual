package com.canerkorkmaz.monopoly.lib.di;

import com.canerkorkmaz.monopoly.data.model.ConnectionData;
import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.data.socket.SocketConnection;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.logger.DefaultLoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.view.resources.MonopolyImageLoader;

public class DIRegistry {
    public DIRegistry() {

    }

    public void registerDefaults() {
        DI registry = DI.getInstance();
        // Register Factories
        registry.registerSingleton(ILoggerFactory.class, DefaultLoggerFactory.class);
        registry.registerSingleton(EventFactory.class);
        // Command Dispatcher
        registry.registerSingleton(CommandDispatcher.class);
        // Data Models ( these are singleton because they represent some object that would normally be taken from storage/db )
        registry.registerSingleton(LocalPlayerData.class);
        registry.registerSingleton(ConnectionData.class);
        registry.registerSingleton(SocketConnection.class);
        // Services
        registry.register(LocalPlayerRepository.class);
        registry.register(ConnectionRepository.class);
        // Resource Loaders
        registry.registerSingleton(MonopolyImageLoader.class );
    }
}
