package com.canerkorkmaz.monopoly.utils;

import com.canerkorkmaz.monopoly.view.IView;

import javax.swing.*;

public class ViewUtils {
    public static JFrame createWindowFromView(IView view) {
        JFrame frame = new JFrame("Monopoly Application");
        // Register view
        frame.setContentPane(view.getRoot());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Size window accordingly to the view
        frame.pack();
        // Set fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        return frame;
    }
}
