package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.Validate;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.data.UIGameCreationData;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.CreateGameViewModel;

import javax.swing.*;

public class CreateGameView extends CenteredNavigationView {
    private Logger logger;
    private CreateGameViewModel viewModel;

    private JTextField portField;
    private JComboBox<Integer> playerCountField;

    @Injected
    public CreateGameView(ILoggerFactory loggerFactory, CreateGameViewModel viewModel) {
        this.logger = loggerFactory.createLogger(CreateGameView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        portField = new JTextField("3000");
        playerCountField = new JComboBox<>(new Integer[]{1, 2, 3, 4});

        final Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Create a New Game", false))
                .addVerticalSpace(30)
                .addLabeledComponent("Game Port Number: ", portField)
                .addVerticalSpace(15)
                .addLabeledComponent("Local Player Count: ", playerCountField)
                .addVerticalSpace(15)
                .addButton("CREATE THE GAME", this::validateAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());

        this.viewModel.getSuccessfullyCreated().runIfNotHandled((unit) -> {
            this.getNavigator().navigatePush(UserNamesView.class);
        });
    }

    private void validateAndTriggerVM() {
        try {
            int port = Validate.getValidatedPort(portField.getText());
            int localPlayerCount = Validate.getValidatedLocalPlayers(playerCountField.getSelectedItem());

            viewModel.getOnCreateGameClick()
                    .trigger(new UIGameCreationData(port, localPlayerCount));
        } catch (Exception e) {
            logger.e(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error occured: " + e.getMessage());
        }
    }
}
