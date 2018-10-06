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
import com.canerkorkmaz.monopoly.viewmodel.JoinPeerViewModel;

import javax.swing.*;

public class JoinPeerView extends CenteredNavigationView {
    private final Logger logger;
    private final JoinPeerViewModel viewModel;

    private JTextField ipField = new JTextField("127.0.0.1");
    private JTextField peerPortField = new JTextField("3000");
    private JTextField portField = new JTextField("");

    @Injected
    public JoinPeerView(ILoggerFactory loggerFactory, JoinPeerViewModel viewModel) {
        this.logger = loggerFactory.createLogger(CreateGameView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Join Peer", false))
                .addVerticalSpace(30)
                .addLabeledComponent("Peer Ip: ", ipField)
                .addVerticalSpace(15)
                .addLabeledComponent("Peer Port: ", peerPortField)
                .addVerticalSpace(15)
                .addLabeledComponent("Your Port: ", portField)
                .addVerticalSpace(15)
                .addButton("JOIN THE GAME", this::validateAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        viewModel.getSuccessfullyCreated().runIfNotHandled((unit) -> this.getNavigator().navigatePush(LobbyView.class));

        this.setContentPane(form.getContent());
    }

    private void validateAndTriggerVM() {
        try {
            String ip = Validate.getValidatedIp(ipField.getText());
            int peerPort = Validate.getValidatedPort(peerPortField.getText());
            int port = Validate.getValidatedPort(portField.getText());

            viewModel.getOnCreateGameClick()
                    .trigger(new UIGameJoinData(ip, peerPort, port));
        } catch (Exception e) {
            logger.e(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
        }
    }
}
