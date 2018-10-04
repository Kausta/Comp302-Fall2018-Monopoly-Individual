package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.DI;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.util.TimerUtils;
import com.canerkorkmaz.monopoly.view.components.JCenteredPanel;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.NavigationView;

import javax.swing.*;

public class SplashView extends NavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(SplashView.class);

    private JCenteredPanel root = new JCenteredPanel();

    @Override
    public void onEnter() {
        root.setBackground(Colors.BACKGROUND_COLOR);

        // Add a hello text into the middle
        root.setContentPane(new TitleLabel());

        logger.i("Created Splash Frame");

        TimerUtils.runDelayedOnce(1000, e -> navigateToMenuView());
    }

    private void navigateToMenuView() {
        this.getNavigator().navigateReplace(new MenuView());
    }

    @Override
    public JComponent getRoot() {
        return root;
    }
}
