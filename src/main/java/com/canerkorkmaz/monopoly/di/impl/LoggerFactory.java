package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.constants.LogLevel;
import com.canerkorkmaz.monopoly.di.interfaces.ILoggerFactory;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

public class LoggerFactory implements ILoggerFactory {
    private LogLevel logLevel = LogLevel.DEBUG;

    public LoggerFactory() {

    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LoggerFactory setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * Creates a default logger
     *
     * @return Creates a default logger
     */
    @Override
    public Logger createLogger() {
        return new DefaultLogger(logLevel);
    }

    /**
     * Creates a logger with given class' name
     *
     * @param clazz Class to get class name from
     * @return Class aware logger
     */
    @Override
    public Logger createLogger(Class<?> clazz) {
        return new ClassNameLogger(clazz, logLevel);
    }

}
