package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.constants.LogLevel;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

import java.time.Instant;

/**
 * Default logger with time, thread name and log level logging
 */
public class DefaultLogger extends Logger {
    public DefaultLogger() {
    }

    public DefaultLogger(LogLevel level) {
        super(level);
    }

    @Override
    protected void write(String level, String message) {
        System.out.println(String.format("[%s] [%s] [%s] %s",
                Instant.now(),
                Thread.currentThread().getName(),
                level,
                message));
    }
}
