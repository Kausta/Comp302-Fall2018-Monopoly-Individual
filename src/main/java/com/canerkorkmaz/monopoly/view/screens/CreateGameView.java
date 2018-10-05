package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.DI;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

import javax.swing.*;

public class CreateGameView extends CenteredNavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(CreateGameView.class);

    @Override
    public void onEnter() {
        super.onEnter();

        Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Create a New Game", false))
                .addVerticalSpace(30)
                // TODO: JFormatted Text Field & Validation
                .addLabeledComponent("Game Port Number: ", new JTextField("3000"))
                .addVerticalSpace(15)
                .addLabeledComponent("Local Player Count: ", new JComboBox<>(new Integer[]{1, 2, 3, 4}))
                .addVerticalSpace(15)
                .addButton("CREATE THE GAME", () -> {
                })
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());
    }
}
