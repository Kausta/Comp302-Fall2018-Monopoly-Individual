package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.DI;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

import javax.swing.*;

public class JoinGameView extends CenteredNavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(JoinGameView.class);

    @Override
    public void onEnter() {
        super.onEnter();

        Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Join a Game", false))
                .addVerticalSpace(30)
                // TODO: JFormatted Text Field & Validation
                .addLabeledComponent("Game Ip Address: ", new JTextField("127.0.0.1"))
                .addVerticalSpace(15)
                .addLabeledComponent("Game Port Number: ", new JTextField("3000"))
                .addVerticalSpace(15)
                .addLabeledComponent("Local Player Count: ", new JComboBox<>(new Integer[]{1, 2, 3, 4}))
                .addVerticalSpace(15)
                .addButton("JOIN THE GAME", () -> {
                })
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());
    }
}
