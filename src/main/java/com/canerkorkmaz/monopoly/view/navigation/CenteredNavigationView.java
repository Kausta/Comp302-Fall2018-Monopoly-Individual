package com.canerkorkmaz.monopoly.view.navigation;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.view.components.JCenteredPanel;

import javax.swing.*;

public class CenteredNavigationView extends NavigationView {

    private JCenteredPanel root;

    @Override
    public void onEnter() {
        super.onEnter();

        root = new JCenteredPanel();
        root.setBackground(Colors.BACKGROUND_COLOR);
    }

    protected void setContentPane(JComponent component) {
        if (this.root != null) {
            this.root.setContentPane(component);
        }
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    @Override
    public JComponent getRoot() {
        return root;
    }
}
