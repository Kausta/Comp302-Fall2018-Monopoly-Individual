package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.data.UINameData;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;
import com.canerkorkmaz.monopoly.viewmodel.UserNamesViewModel;

import javax.swing.*;

public class UserNamesView extends CenteredNavigationView {
    private final UserNamesViewModel viewModel;

    private JTextField[] nameFields;

    @Injected
    public UserNamesView(UserNamesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final int playerCount = viewModel.getPlayerCount();
        final String[] names = viewModel.getUserNames();
        nameFields = new JTextField[playerCount];

        final Form.Builder formBuilder = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel("Monopoly - Local Player Setup", false))
                .addVerticalSpace(30);
        for (int i = 0; i < playerCount; i++) {
            nameFields[i] = new JTextField();
            nameFields[i].setText(names[i]);
            formBuilder.addLabeledComponent("User " + (i + 1) + " name: ", nameFields[i])
                    .addVerticalSpace(15);
        }
        final Form form = formBuilder.addVerticalSpace(15)
                .addButton("CONTINUE", this::validateNamesAndTriggerVM)
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> {
                    // Also save names when going back for persistence, however don't validate
                    this.triggerVMNoValidation();
                    this.getNavigator().navigatePop();
                })
                .build();

        this.setContentPane(form.getContent());

        viewModel.getSuccessfullySetNames().subscribe((unit) ->
                this.getNavigator().navigatePush(MenuView.class));
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

    private void triggerVMNoValidation() {
        String[] enteredNames = new String[nameFields.length];
        for (int i = 0; i < nameFields.length; i++) {
            enteredNames[i] = nameFields[i].getText();
        }
        viewModel.getOnBackClick().trigger(new UINameData(enteredNames));
    }
}
