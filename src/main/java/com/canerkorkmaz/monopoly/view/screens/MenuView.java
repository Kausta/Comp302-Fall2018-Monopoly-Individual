package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.CenteredNavigationView;

public class MenuView extends CenteredNavigationView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(MenuView.class);

    @Override
    public void onEnter() {
        super.onEnter();

        Form menu = new Form.Builder()
                .setBackgroundColor(Colors.BACKGROUND_COLOR)
                .addComponent(new TitleLabel(false))
                .addVerticalSpace(40)
                .addButton("CREATE GAME", () -> this.getNavigator().navigatePush(CreateGameView.class))
                .addVerticalSpace(15)
                .addButton("JOIN PEER", () -> this.getNavigator().navigatePush(JoinPeerView.class))
                .addVerticalSpace(15)
                .addButton("GO BACK", () -> this.getNavigator().navigatePop())
                .addVerticalSpace(15)
                .addButton("EXIT", () -> this.getNavigator().exitApplication())
                .addVerticalSpace(15)
                .build();

        this.setContentPane(menu.getContent());
    }

}
