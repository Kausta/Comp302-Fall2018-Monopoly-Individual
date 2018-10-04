package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.di.LoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.view.components.JCenteredPanel;
import com.canerkorkmaz.monopoly.view.components.TitleLabel;
import com.canerkorkmaz.monopoly.view.navigation.NavigationView;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuView extends NavigationView implements ActionListener {

    private final static String CREATE_COMMAND = "MENU/CREATE";
    private final static String JOIN_COMMAND = "MENU/JOIN";
    private final static String EXIT_COMMAND = "MENU/EXIT";

    private Logger logger = new LoggerFactory().createLogger(MenuView.class);

    private JCenteredPanel root;

    private JPanel menu;
    private BoxLayout menuLayout;

    private ArrayList<JButton> buttonList = new ArrayList<>();

    @Override
    public void onEnter() {
        root = new JCenteredPanel();

        menu = new JPanel();
        menuLayout = new BoxLayout(menu, BoxLayout.Y_AXIS);

        root.setBackground(Colors.BACKGROUND_COLOR);
        menu.setBackground(Colors.BACKGROUND_COLOR);

        menu.add(new TitleLabel(false));
        menu.add(Box.createVerticalStrut(40));

        buttonList.add(createMenuButton("CREATE GAME", CREATE_COMMAND));
        buttonList.add(createMenuButton("JOIN GAME", JOIN_COMMAND));
        buttonList.add(createMenuButton("EXIT", EXIT_COMMAND));

        for (int i = 0; i < buttonList.size(); i++) {
            JButton button = buttonList.get(i);
            JPanel buttonContainer = new JPanel();
            buttonContainer.add(button);
            buttonContainer.setBackground(Colors.BACKGROUND_COLOR);
            menu.add(buttonContainer);
            if (i != buttonList.size()) {
                menu.add(Box.createVerticalStrut(15));
            }
        }

        menu.setLayout(menuLayout);
        root.setContentPane(menu);
    }

    @Override
    public JComponent getRoot() {
        return root;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == null) {
            return;
        }
        logger.i(String.format("Clicked %s button", command));
        switch (command) {
            case CREATE_COMMAND:
                break;
            case JOIN_COMMAND:
                break;
            case EXIT_COMMAND:
                this.getNavigator().exitApplication();
                break;
        }
    }

    private JButton createMenuButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setFont(new Font("Monospaced", Font.BOLD, 24));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        Dimension size = new Dimension(250, 50);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        return button;
    }
}
