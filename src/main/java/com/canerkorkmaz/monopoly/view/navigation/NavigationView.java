package com.canerkorkmaz.monopoly.view.navigation;

import com.canerkorkmaz.monopoly.view.base.IView;

import javax.swing.*;

/**
 * View that can be registered in a NavigationContainer
 */
public abstract class NavigationView implements IView {
    private NavigationContainer navigator;

    public void onEnter() {
    }

    public void onExit() {
    }

    public NavigationContainer getNavigator() {
        return navigator;
    }

    public void setNavigator(NavigationContainer navigator) {
        this.navigator = navigator;
    }

    public JFrame getWindow() {
        return this.navigator != null ? this.navigator.getWindow() : null;
    }
}
