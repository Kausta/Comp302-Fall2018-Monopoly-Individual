package com.canerkorkmaz.monopoly.util;

import com.canerkorkmaz.monopoly.view.base.IView;
import com.canerkorkmaz.monopoly.view.base.WindowAwareView;

import javax.swing.*;

public final class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    /**
     * Creates a JFrame fullscreen window from given view
     *
     * @param view View to create window from
     * @return Created window
     */
    public static JFrame createWindowFromView(IView view) {
        return createWindowFromView(view, "Monopoly Application");
    }

    /**
     * Creates a JFrame fullscreen window from given view
     *
     * @param view  View to create window from
     * @param title Title of the window
     * @return Created window
     */
    public static JFrame createWindowFromView(IView view, String title) {
        JFrame frame = new JFrame(title);
        // Register view
        frame.setContentPane(view.getRoot());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // Set fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        return frame;
    }

    public static JFrame createWindowFromView(WindowAwareView view) {
        JFrame window = createWindowFromView((IView) view);
        view.registerWindow(window);
        return window;
    }

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {

        }
    }

    public static JFrame createWindowFromView(WindowAwareView view, String title) {
        JFrame window = createWindowFromView((IView) view, title);
        view.registerWindow(window);
        return window;
    }

    public static JFrame getWindow(JComponent view) {
        return (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, view);
    }
}
