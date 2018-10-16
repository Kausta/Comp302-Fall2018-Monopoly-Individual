package com.canerkorkmaz.monopoly.view.components;

import com.canerkorkmaz.monopoly.lib.util.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;

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

    public GridBagConstraints createConstraints() {
        return new GridBagConstraintsBuilder()
                .setPosition(gridX, gridY)
                .setSize(gridWidth, gridHeight)
                .build();
    }
}
