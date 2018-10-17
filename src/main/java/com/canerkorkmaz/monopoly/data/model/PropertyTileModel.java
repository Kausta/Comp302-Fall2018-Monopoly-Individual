package com.canerkorkmaz.monopoly.data.model;

import java.awt.*;
import java.util.Objects;

public class PropertyTileModel extends TileModel {
    private static final long serialVersionUID = 1L;

    private Color color;
    private String name;
    private double price;
    private boolean isBought;

    public PropertyTileModel() {
        super(TileType.PROPERTY);
    }

    public PropertyTileModel(Color color, String name, double price) {
        super(TileType.PROPERTY);
        this.color = color;
        this.name = name;
        this.price = price;
        this.isBought = false;
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

    @Override
    public void handleLand(PlayerModel model) {
        if(!isBought && model.getMoney() >= this.price) {
            model.setCanBuyProperty(true);
            model.setBuyableProperty(this);
        }
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyTileModel that = (PropertyTileModel) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(color, that.color) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name, price, isBought);
    }
}
