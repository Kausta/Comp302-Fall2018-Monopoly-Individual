package com.canerkorkmaz.monopoly;

import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.di.DIRegistry;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.ViewUtils;
import com.canerkorkmaz.monopoly.view.navigation.NavigationContainer;
import com.canerkorkmaz.monopoly.view.screens.SplashView;

import javax.swing.*;

/**
 * Main entry point of the application
 */
public class Application implements Runnable {
    private final Logger logger;

    @Injected
    public Application(ILoggerFactory loggerFactory, CommandDispatcher dispatcher) {
        // Start command dispatcher
        dispatcher.runDispatcher();

        this.logger = loggerFactory.createLogger(Application.class);

        logger.i("Created new Monopoly Application");
    }

    public static void main(String[] args) {
        // Create registry
        DIRegistry registry = new DIRegistry();
        registry.registerDefaults();

        SwingUtilities.invokeLater(DI.get(Application.class));
    }

    @Override
    public void run() {
        logger.i("Running Monopoly Application");
        ViewUtils.setSystemLookAndFeel();

        NavigationContainer navView = new NavigationContainer(SplashView.class);
        ViewUtils.createWindowFromView(navView);
    }
}
