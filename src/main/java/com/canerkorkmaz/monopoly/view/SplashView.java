package com.canerkorkmaz.monopoly.view;

import com.canerkorkmaz.monopoly.di.LoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

import javax.swing.*;
import java.awt.*;

public class SplashView implements IView {
    private Logger logger = LoggerFactory.getInstance().createLogger(SplashView.class);

    private JPanel root = new JPanel();

    public SplashView() {
        root.setLayout(new BorderLayout());

        JLabel helloLabel = new JLabel("Hello Swing");
        helloLabel.setHorizontalAlignment(JLabel.CENTER);
        root.add(helloLabel, BorderLayout.CENTER);

        logger.i("Created Splash Frame");
    }

    @Override
    public JComponent getRoot() {
        return root;
    }
}
