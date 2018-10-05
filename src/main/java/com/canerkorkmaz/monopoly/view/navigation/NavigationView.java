package com.canerkorkmaz.monopoly.view.navigation;

import com.canerkorkmaz.monopoly.view.base.BaseView;

import javax.swing.*;

/**
 * View that can be registered in a NavigationContainer
 */
public abstract class NavigationView extends BaseView {
    private NavigationContainer navigator;

    public void onEnter() {
    }

    public void onExit() {
    }

    public void navigateIn() {
    }

    public void navigateOut() {
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
