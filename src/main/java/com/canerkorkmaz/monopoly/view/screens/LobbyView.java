package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.LobbyViewModel;

import javax.swing.*;
import java.awt.*;

public class LobbyView extends CenteredNavigationView {
    private LobbyViewModel viewModel;

    @Injected
    public LobbyView(LobbyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final Form.Builder formBuilder = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Lobby", false))
                .addVerticalSpace(30);

        Font userFont = new Font("monospaced", Font.PLAIN, 18);
        String[] userNames = viewModel.getPlayerNames();
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
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());
    }

    private void startGame() {

    }
}
