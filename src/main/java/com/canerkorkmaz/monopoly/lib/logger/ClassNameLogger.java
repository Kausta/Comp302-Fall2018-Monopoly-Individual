package com.canerkorkmaz.monopoly.lib.logger;

import com.canerkorkmaz.monopoly.constants.LogLevel;

import java.time.Instant;

/**
 * Logger with time, thread name, class name and level
 */
public class ClassNameLogger extends Logger {
    private final String name;

    public ClassNameLogger(Class<?> clazz) {
        this.name = clazz.getSimpleName();
    }

    public ClassNameLogger(Class<?> clazz, LogLevel level) {
        super(level);
        this.name = clazz.getSimpleName();
    }

    @Override
    protected void write(String level, String message) {
        System.out.println(String.format("[%s] [%s] [%s] [%s] %s",
                Instant.now(),
                Thread.currentThread().getName(),
                name,
                level,
                message));
    }
}
