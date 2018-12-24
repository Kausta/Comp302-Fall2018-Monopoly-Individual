package com.canerkorkmaz.monopoly.view.components;

import javax.swing.*;

public class MonopolyBoxHolder {
    private final JComponent monopolyComponent;
    private final int gridX;
    private final int gridY;
    private final int gridWidth;
    private final int gridHeight;

    public MonopolyBoxHolder(JComponent monopolyComponent, int gridX, int gridY, int gridWidth, int gridHeight) {
        this.monopolyComponent = monopolyComponent;
        this.gridX = gridX;
        this.gridY = gridY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.setBounds();
    }

    public JComponent getMonopolyComponent() {
        return monopolyComponent;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    private void setBounds() {
        monopolyComponent.setBounds(gridX, gridY, gridWidth, gridHeight);
    }
}
