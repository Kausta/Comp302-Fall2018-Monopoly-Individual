package com.canerkorkmaz.monopoly.lib.util;

import java.awt.*;

public class GridBagConstraintsBuilder {
    private GridBagConstraints constraints;

    public GridBagConstraintsBuilder() {
        constraints = new GridBagConstraints();
    }

    public GridBagConstraintsBuilder setPosition(int gridX, int gridY) {
        constraints.gridx = gridX;
        constraints.gridy = gridY;

        return this;
    }

    public GridBagConstraintsBuilder setSize(int gridWidth, int gridHeight) {
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;

        return this;
    }

    public GridBagConstraintsBuilder setAnchor(int anchor) {
        constraints.anchor = anchor;

        return this;
    }

    public GridBagConstraintsBuilder setWeightX(double weightX) {
        constraints.weightx = weightX;

        return this;
    }

    public GridBagConstraintsBuilder setWeightY(double weightY) {
        constraints.weighty = weightY;

        return this;
    }

    public GridBagConstraintsBuilder setFill(int fill) {
        constraints.fill = fill;

        return this;
    }

    public GridBagConstraints build() {
        return constraints;
    }
}

