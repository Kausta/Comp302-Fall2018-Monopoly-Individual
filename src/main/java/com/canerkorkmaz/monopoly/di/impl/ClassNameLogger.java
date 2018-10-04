package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.constants.LogLevel;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

import java.time.Instant;

/**
 * Logger with time, thread name, class name and level
 */
public class ClassNameLogger extends Logger {
    private String name;

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
