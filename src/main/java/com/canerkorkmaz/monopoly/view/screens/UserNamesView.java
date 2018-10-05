package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.data.UINameData;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.UserNamesViewModel;

import javax.swing.*;

public class UserNamesView extends CenteredNavigationView {
    private Logger logger;
    private UserNamesViewModel viewModel;

    private JTextField[] nameFields;

    @Injected
    public UserNamesView(ILoggerFactory loggerFactory, UserNamesViewModel viewModel) {
        this.logger = loggerFactory.createLogger(UserNamesView.class);
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final int playerCount = viewModel.getPlayerCount();
        logger.d("Initializing user names view for " + playerCount + " players");
        nameFields = new JTextField[playerCount];

        final Form.Builder formBuilder = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Enter User Names", false))
                .addVerticalSpace(30);
        for (int i = 0; i < playerCount; i++) {
            nameFields[i] = new JTextField();
            formBuilder.addLabeledComponent("User " + (i + 1) + " name: ", nameFields[i])
                    .addVerticalSpace(15);
        }
        final Form form = formBuilder.addVerticalSpace(15)
                .addButton("CONTINUE", this::validateNamesAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .build();

        this.setContentPane(form.getContent());

        viewModel.getSuccessfullySetNames().runIfNotHandled((unit) ->
                this.getNavigator().navigatePush(LobbyView.class));
    }

    private void validateNamesAndTriggerVM() {
        String[] names = new String[nameFields.length];
        for (int i = 0; i < nameFields.length; i++) {
            String text = nameFields[i].getText();
            if (text == null || (text = text.trim()).equals("")) {
                JOptionPane.showMessageDialog(null, "You cannot leave a name empty");
                return;
            }
            names[i] = text;
        }
        viewModel.getOnContinueClick().trigger(new UINameData(names));
    }
}
