package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.LobbyViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LobbyView extends CenteredNavigationView {
    private final Logger logger;
    private final LobbyViewModel viewModel;

    @Injected
    public LobbyView(ILoggerFactory loggerFactory, LobbyViewModel viewModel) {
        this.logger = loggerFactory.createLogger(LobbyView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        viewModel.initLobby();
        this.draw(viewModel.getPlayerNames());
        this.viewModel.getUserNamesChanged().subscribe(this::draw);
        this.viewModel.getSuccessOrFailure().subscribe((success) -> {
            if (success) {
                this.getNavigator().navigateReplace(GameView.class);
            } else {
                this.getNavigator().navigatePop();
            }
        });
    }

    private void draw(ArrayList<String> userNames) {
        logger.i("Redrawing lobby with new players");
        final Form.Builder formBuilder = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Lobby", false))
                .addVerticalSpace(30);

        Font userFont = new Font("monospaced", Font.PLAIN, 18);
        for (String userName : userNames) {
            JLabel userText = new JLabel(userName);
            userText.setFont(userFont);
            userText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            formBuilder.addComponent(userText)
                    .addVerticalSpace(10);
        }
        formBuilder.addVerticalSpace(5);

        if (viewModel.isServerMode()) {
            formBuilder.addButton("START GAME", this::startGame);
        } else {
            JLabel waitingText = new JLabel("WAITING FOR HOST ...");
            waitingText.setFont(new Font("monospaced", Font.BOLD, 24));
            waitingText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            formBuilder.addComponent(waitingText);
        }

        final Form form = formBuilder
                .addVerticalSpace(15)
                // .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());
        this.getNavigator().redraw(this.getRoot());
    }

    private void startGame() {
        this.viewModel.getStartGame().trigger(Unit.INSTANCE);
    }
}
