package com.canerkorkmaz.monopoly.view.components;

import javax.swing.*;

/**
 * A JPanel like Component that automatically centers the content in the window
 */
public class JCenteredPanel extends JPanel {
    private JComponent rootComponent;

    public JCenteredPanel() {
        super();
    }

    public JCenteredPanel(JComponent rootComponent) {
        super();
        this.setContentPane(rootComponent);
    }

    public JComponent getContentPane() {
        return this.rootComponent;
    }

    /**
     * Sets the content pane and centers it
     *
     * @param contentPane Actual content to center
     */
    public void setContentPane(JComponent contentPane) {
        if (this.rootComponent != null) {
            this.remove(this.rootComponent);
        }

        this.rootComponent = contentPane;
        SpringLayout layout = new SpringLayout();
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, rootComponent, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, rootComponent, 0, SpringLayout.VERTICAL_CENTER, this);
        this.setLayout(layout);
        this.add(contentPane);
    }
}
