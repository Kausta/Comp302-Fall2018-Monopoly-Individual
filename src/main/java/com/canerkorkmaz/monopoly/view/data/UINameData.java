package com.canerkorkmaz.monopoly.view.data;

public class UINameData {
    private final String[] names;

    public UINameData(String[] names) {
        this.names = names.clone();
    }

    public String[] getNames() {
        return names.clone();
    }
}
