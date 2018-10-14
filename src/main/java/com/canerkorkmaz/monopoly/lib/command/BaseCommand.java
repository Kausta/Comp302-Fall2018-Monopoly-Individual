package com.canerkorkmaz.monopoly.lib.command;

import java.io.Serializable;
import java.util.Objects;

public abstract class BaseCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String identifier;

    public BaseCommand(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isCommand(String identifier) {
        return Objects.equals(this.identifier, identifier);
    }

    @Override
    public String toString() {
        return "BaseCommand{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
