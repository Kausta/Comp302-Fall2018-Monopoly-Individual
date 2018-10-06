package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.Validate;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.data.UIGameJoinData;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.JoinGameViewModel;

import javax.swing.*;

public class JoinGameView extends CenteredNavigationView {
    private final Logger logger;
    private final JoinGameViewModel viewModel;

    private JTextField ipField = new JTextField("127.0.0.1");
    private JTextField portField = new JTextField("3000");
    private JComboBox<Integer> playerCountField = new JComboBox<>(new Integer[]{1, 2, 3, 4});

    @Injected
    public JoinGameView(ILoggerFactory loggerFactory, JoinGameViewModel viewModel) {
        this.logger = loggerFactory.createLogger(CreateGameView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Join a Game", false))
                .addVerticalSpace(30)
                .addLabeledComponent("Game Ip Address: ", ipField)
                .addVerticalSpace(15)
                .addLabeledComponent("Game Port Number: ", portField)
                .addVerticalSpace(15)
                .addLabeledComponent("Local Player Count: ", playerCountField)
                .addVerticalSpace(15)
                .addButton("JOIN THE GAME", this::validateAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        viewModel.getSuccessfullyCreated().runIfNotHandled((unit) -> this.getNavigator().navigatePush(UserNamesView.class));

        this.setContentPane(form.getContent());
    }

    private void validateAndTriggerVM() {
        try {
            String ip = Validate.getValidatedIp(ipField.getText());
            int port = Validate.getValidatedPort(portField.getText());
            int players = Validate.getValidatedLocalPlayers(playerCountField.getSelectedItem());

            viewModel.getOnCreateGameClick()
                    .trigger(new UIGameJoinData(ip, port, players));
        } catch (Exception e) {
            logger.e(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error occured: " + e.getMessage());
        }
    }
}
