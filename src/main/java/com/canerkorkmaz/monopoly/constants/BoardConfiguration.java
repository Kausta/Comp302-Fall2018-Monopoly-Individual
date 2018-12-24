package com.canerkorkmaz.monopoly.constants;

import com.canerkorkmaz.monopoly.data.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class BoardConfiguration {
    private BoardConfiguration() {
    }

    public static final Color BLUE = new Color(0x87, 0xA5, 0xD7);
    public static final Color PINK = new Color(0xEF, 0x38, 0x78);
    public static final Color ORANGE = new Color(0xF5, 0x80, 0x23);
    public static final Color GREEN = new Color(0x09, 0x87, 0x33);

    public final static List<TileModel> tileTypes = Collections.unmodifiableList(Arrays.asList(
            new FreeParkingModel(),
            new PropertyTileModel(ORANGE, "St. James Place", 180),
            new KocSquareModel(),
            new PropertyTileModel(ORANGE, "Tennessee Ave", 180),
            new PropertyTileModel(ORANGE, "New York Ave", 200),
            new SqueezePlayModel(),
            new PropertyTileModel(GREEN, "Pacific Ave", 300),
            new PropertyTileModel(GREEN, "North Carolina Ave", 300),
            new ReverseSquareModel(),
            new PropertyTileModel(GREEN, "Pennsylvania Ave", 320),
            new GoTileModel(),
            new PropertyTileModel(BLUE, "Oriental Ave", 100),
            new FreeParkingModel(),
            new PropertyTileModel(BLUE, "Vermont Ave", 100),
            new PropertyTileModel(BLUE, "Connecticut Ave", 120),
            new RollOnceModel(),
            new PropertyTileModel(PINK, "St. Charles Place", 140),
            new BonusModel(),
            new PropertyTileModel(PINK, "States Ave", 140),
            new PropertyTileModel(PINK, "Virginia Ave", 160)));

    public static PropertyTileModel getPropertyTileModel(PropertyTileModel p) {
        for (TileModel model : tileTypes) {
            if (model instanceof PropertyTileModel) {
                PropertyTileModel propertyTileModel = (PropertyTileModel) model;
                if(propertyTileModel.equals(p)) {
                    return propertyTileModel;
                }
            }
        }
        return p;
    }
}
