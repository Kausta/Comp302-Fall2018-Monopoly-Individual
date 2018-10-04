package com.canerkorkmaz.monopoly.view.base;

import javax.swing.*;

/**
 * Base view interface for all views
 */
public interface IView {
    /**
     * Get root component of the view to register into a JFrame
     * @return Root Component
     */
    JComponent getRoot();
}
