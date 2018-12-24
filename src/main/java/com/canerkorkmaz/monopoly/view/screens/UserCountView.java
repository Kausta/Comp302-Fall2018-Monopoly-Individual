package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.util.Validate;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.data.UICountData;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.UserCountViewModel;

import javax.swing.*;

public class UserCountView extends CenteredNavigationView {
    private final UserCountViewModel viewModel;

    private JComboBox<Integer> playerCountField;

    @Injected
    public UserCountView(UserCountViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        playerCountField = new JComboBox<>(new Integer[]{1, 2, 3, 4});

        final Form form = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Monopoly - Local Player Setup", false))
                .addVerticalSpace(30)
                .addLabeledComponent("Local Player Count: ", playerCountField)
                .addButton("CONTINUE", this::validateCountAndTriggerVM)
                .addVerticalSpace(15)
                .build();

        this.setContentPane(form.getContent());

        viewModel.getSuccessfullySetCount().subscribe((unit) ->
                this.getNavigator().navigatePush(UserNamesView.class));
    }

    private void validateCountAndTriggerVM() {
        try {
            int playerCount = Validate.getValidatedLocalPlayers(playerCountField.getSelectedItem());
            if (playerCount < 0) {
                throw new RuntimeException();
            }
            viewModel.getOnContinueClick().trigger(new UICountData(playerCount));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid player count");
        }
    }
}
