package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

public class LobbyView extends CenteredNavigationView {
    @Override
    public void onEnter() {
        super.onEnter();

        this.setContentPane(new TitleLabel());
    }
}
