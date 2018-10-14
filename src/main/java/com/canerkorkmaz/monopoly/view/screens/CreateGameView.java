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

    @Injected
    public CreateGameView(ILoggerFactory loggerFactory, CreateGameViewModel viewModel) {
        this.logger = loggerFactory.createLogger(CreateGameView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        portField = new JTextField("3000");

        final Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Create a New Game", false))
                .addVerticalSpace(30)
                .addLabeledComponent("Your Port Number: ", portField)
                .addVerticalSpace(15)
                .addButton("CREATE THE GAME", this::validateAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());

        this.viewModel.getSuccessfullyCreated().subscribe((unit) -> this.getNavigator().navigatePush(LobbyView.class));
        viewModel.getErrorOccurred().subscribe((message) -> JOptionPane.showMessageDialog(null, "Error occurred: " + message));
    }

    private void validateAndTriggerVM() {
        try {
            int port = Validate.getValidatedPort(portField.getText());

            viewModel.getOnCreateGameClick()
                    .trigger(new UIGameCreationData(port));
        } catch (Exception e) {
            logger.e(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
        }
    }
}
