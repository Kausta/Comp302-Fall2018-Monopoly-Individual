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

    public NavigationContainer(Class<? extends NavigationView> initialViewClazz) {
        this.navigatePush(initialViewClazz);
    }

    public NavigationView getCurrentView() {
        return this.navigationStack.isEmpty() ? null : this.navigationStack.peek();
    }

    public <T extends NavigationView> T navigatePush(Class<? extends T> clazz) {
        NavigationView previous = getCurrentView();
        if (previous != null) {
            previous.navigateOut();
        }

        T view = DI.get(clazz);
        NavigationView current = this.navigationStack.push(view);
        current.setNavigator(this);
        current.onEnter();
        logger.i(String.format("Entering %s", current.getClass().getSimpleName()));
        current.navigateIn();

        this.redraw(current.getRoot());

        return view;
    }

    public boolean navigatePop() {
        // Cannot remove first element, should be replaced
        if (this.navigationStack.size() <= 1) {
            return false;
        }
        boolean successful = navigatePopUnchecked() != null;
        getCurrentView().navigateIn();
        this.redraw(getCurrentView().getRoot());
        return successful;
    }

    public <T extends NavigationView> T navigateReplace(Class<? extends T> clazz) {
        while (!navigationStack.isEmpty()) {
            this.navigatePopUnchecked();
        }

        return this.navigatePush(clazz);
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
        if (window != null) {
            window.validate();
            window.repaint();
        }
    }

    public void redraw(JComponent contentPane) {
        if (window != null) {
            window.setContentPane(contentPane);
            window.validate();
            window.repaint();
        }
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
        previous.navigateOut();
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
