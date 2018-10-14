package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.GameViewModel;

public class GameView extends CenteredNavigationView {
    private final Logger logger;
    private final GameViewModel viewModel;

    @Injected
    public GameView(ILoggerFactory loggerFactory, GameViewModel viewModel){
        this.logger = loggerFactory.createLogger(GameView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        this.bindEvents();

        // Add a hello text into the middle
        this.setContentPane(new TitleLabel());
    }

    private void bindEvents() {
        viewModel.getCloseApplication().subscribe(unit ->
                this.getNavigator().exitApplication());
    }
}
