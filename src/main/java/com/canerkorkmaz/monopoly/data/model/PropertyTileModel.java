package com.canerkorkmaz.monopoly.data.model;

import java.awt.*;

public class PropertyTileModel extends TileModel {
    private static final long serialVersionUID = 1L;

    private Color color;
    private String name;
    private double price;

    public PropertyTileModel() {
        super(TileType.PROPERTY);
    }

    public PropertyTileModel(Color color, String name, double price) {
        super(TileType.PROPERTY);
        this.color = color;
        this.name = name;
        this.price = price;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
