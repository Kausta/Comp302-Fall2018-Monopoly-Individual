package com.canerkorkmaz.monopoly.domain.command;

import com.canerkorkmaz.monopoly.data.model.PropertyTileModel;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;

public class BuyPropertyCommand extends BaseCommand {
    public static final String IDENTIFIER = "BUY_PROPERTY_COMMAND";
    private static final long serialVersionUID = 1L;

    private final PropertyTileModel property;

    public BuyPropertyCommand(PropertyTileModel property) {
        super(IDENTIFIER);
        this.property = property;
    }

    public PropertyTileModel getProperty() {
        return property;
    }
}
