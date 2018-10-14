package com.canerkorkmaz.monopoly.lib.di;

import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

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
