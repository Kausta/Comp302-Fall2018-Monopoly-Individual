package com.canerkorkmaz.monopoly.lib.di;

import com.canerkorkmaz.monopoly.data.model.LocalPlayerData;
import com.canerkorkmaz.monopoly.domain.service.LocalPlayerRepository;
import com.canerkorkmaz.monopoly.lib.logger.DefaultLoggerFactory;
import com.canerkorkmaz.monopoly.lib.threading.SocketThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.SwingThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.ViewModelThreadScheduler;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.threading.IThreadScheduler;

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
        registry.registerSingleton(SocketThreadScheduler.class, SocketThreadScheduler.class);
        // Data Models
        registry.registerSingleton(LocalPlayerData.class, LocalPlayerData.class);
        // Services
        registry.register(LocalPlayerRepository.class);
    }
}
