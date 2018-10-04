package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

public class TestLoggerFactory implements ILoggerFactory {
    @Override
    public Logger createLogger() {
        return new TestLogger();
    }

    @Override
    public Logger createLogger(Class<?> clazz) {
        return new TestLogger(clazz);
    }
}
