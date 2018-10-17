package com.canerkorkmaz.monopoly.lib.logger;

import com.canerkorkmaz.monopoly.constants.Configuration;
import com.canerkorkmaz.monopoly.constants.LogLevel;

public class DefaultLoggerFactory implements ILoggerFactory {
    private LogLevel logLevel = Configuration.DEFAULT_LOG_LEVEL;

    public DefaultLoggerFactory() {

    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
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
