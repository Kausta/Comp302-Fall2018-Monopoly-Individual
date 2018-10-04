package com.canerkorkmaz.monopoly;

import com.canerkorkmaz.monopoly.di.LoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.util.ViewUtils;
import com.canerkorkmaz.monopoly.view.navigation.NavigationContainer;
import com.canerkorkmaz.monopoly.view.screens.SplashView;

import javax.swing.*;

/**
 * Main entry point of the application
 */
public class Application implements Runnable {
    private Logger logger = new LoggerFactory().createLogger(Application.class);

    private Application() {
        logger.i("Created new Monopoly Application");
    }

    @Override
    public void run() {
        logger.i("Running Monopoly Application");

        NavigationContainer navView = new NavigationContainer(new SplashView());
        ViewUtils.createWindowFromView(navView);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }
}
