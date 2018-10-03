package com.canerkorkmaz.monopoly;

import com.canerkorkmaz.monopoly.di.LoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.utils.ViewUtils;
import com.canerkorkmaz.monopoly.view.SplashView;

import javax.swing.*;

public class Application implements Runnable {

    private Logger logger = LoggerFactory.getInstance().createLogger(Application.class);

    public Application() {
        logger.i("Created new Monopoly Application");
    }

    @Override
    public void run() {
        logger.i("Running Monopoly Application");

        SplashView initialView = new SplashView();
        ViewUtils.createWindowFromView(initialView);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }
}
