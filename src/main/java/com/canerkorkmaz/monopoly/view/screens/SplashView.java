package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.di.DI;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.util.TimerUtils;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

public class SplashView extends CenteredNavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(SplashView.class);

    @Override
    public void onEnter() {
        super.onEnter();

        // Add a hello text into the middle
        this.setContentPane(new TitleLabel());

        logger.i("Created Splash Frame");

        TimerUtils.runDelayedOnce(1000, e -> this.getNavigator().navigateReplace(MenuView.class));
    }
}
