package com.canerkorkmaz.monopoly.view.base;

import javax.swing.*;
import java.awt.event.WindowAdapter;

/**
 * Base class for views with access to window JFrame
 */
public abstract class WindowAwareView extends WindowAdapter implements IView {
    /**
     * Registers window to the window aware view
     *
     * @param frame The JFrame window the view is in
     */
    public abstract void registerWindow(JFrame frame);
}
