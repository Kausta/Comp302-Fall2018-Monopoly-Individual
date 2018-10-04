package com.canerkorkmaz.monopoly.view.navigation;

import com.canerkorkmaz.monopoly.di.DI;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;
import com.canerkorkmaz.monopoly.view.base.WindowAwareView;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Stack;

/**
 * A Stack based Navigation Container that handles application wide container navigation inside a single JFrame
 * <p>
 * Works based on pushing/popping to the stack or replacing the entire stack
 */
public class NavigationContainer extends WindowAwareView {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(NavigationContainer.class);

    private Stack<NavigationView> navigationStack = new Stack<>();
    private JFrame window = new JFrame();

    public NavigationContainer(NavigationView initialView) {
        this.navigatePush(initialView);
    }

    public NavigationView getCurrentView() {
        return this.navigationStack.isEmpty() ? null : this.navigationStack.peek();
    }

    public void navigatePush(NavigationView view) {
        NavigationView current = this.navigationStack.push(view);
        current.setNavigator(this);
        current.onEnter();
        logger.i(String.format("Entering %s", current.getClass().getSimpleName()));

        if (window == null) {
            return;
        }
        window.setContentPane(current.getRoot());
        this.redraw();
    }

    public boolean navigatePop() {
        // Cannot remove first element, should be replaced
        if (this.navigationStack.size() <= 1) {
            return false;
        }
        return navigatePopUnchecked() != null;
    }

    public void navigateReplace(NavigationView to) {
        while (!navigationStack.isEmpty()) {
            this.navigatePopUnchecked();
        }

        this.navigatePush(to);
    }

    public void exitApplication() {
        while (!navigationStack.isEmpty()) {
            this.navigatePopUnchecked();
        }
        System.exit(0);
    }

    public JFrame getWindow() {
        return this.window;
    }

    public void redraw() {
        JFrame window = this.getWindow();
        if (window == null) {
            return;
        }
        window.validate();
        window.repaint();
    }

    @Override
    public JComponent getRoot() {
        assert !navigationStack.isEmpty();
        return getCurrentView().getRoot();
    }

    @Override
    public void registerWindow(JFrame frame) {
        this.window = frame;
        if (window != null) {
            window.addWindowListener(this);
        }
    }

    private NavigationView navigatePopUnchecked() {
        assert !navigationStack.isEmpty();
        NavigationView previous = this.getCurrentView();
        logger.i(String.format("Exiting %s", previous.getClass().getSimpleName()));
        previous.onExit();
        return navigationStack.pop();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        while (!navigationStack.isEmpty()) {
            this.navigatePopUnchecked();
        }
        e.getWindow().dispose();
    }
}
