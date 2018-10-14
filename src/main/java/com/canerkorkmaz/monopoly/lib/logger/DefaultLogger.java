package com.canerkorkmaz.monopoly.lib.logger;

import com.canerkorkmaz.monopoly.constants.LogLevel;

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
