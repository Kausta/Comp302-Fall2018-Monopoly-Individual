package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.di.interfaces.Logger;

public class TestLogger extends Logger {
    private final String className;

    public TestLogger() {
        this.className = "UNKNOWN";
    }

    public TestLogger(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    @Override
    protected void write(String level, String message) {
        System.out.println(String.format("[TEST] [%s] %s", level, message));
    }
}
