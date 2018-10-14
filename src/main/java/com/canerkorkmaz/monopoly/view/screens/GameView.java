package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.TimerUtils;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

public class GameView extends CenteredNavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(GameView.class);

    @Override
    public void onEnter() {
        super.onEnter();

        // Add a hello text into the middle
        this.setContentPane(new TitleLabel());
    }
}
