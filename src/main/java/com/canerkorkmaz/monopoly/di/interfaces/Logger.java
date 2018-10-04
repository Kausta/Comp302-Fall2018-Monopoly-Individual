package com.canerkorkmaz.monopoly.di.interfaces;

import com.canerkorkmaz.monopoly.constants.LogLevel;

/**
 * Base logger class, abstracts away log level details and uses extender's write method
 */
public abstract class Logger {
    private LogLevel level;

    public Logger() {
        this(LogLevel.DEBUG);
    }

    public Logger(LogLevel level) {
        this.level = level;
    }

    /**
     * Method used to write log messages
     * @param level Level string
     * @param message Message Text
     */
    protected abstract void write(String level, String message);

    /**
     * Debug level log message
     * @param msg Message to log
     */
    public void d(String msg) {
        if (LogLevel.DEBUG.compareTo(level) >= 0) {
            write("DEBUG", msg);
        }
    }

    /**
     * Info level log message
     * @param msg Message to log
     */
    public void i(String msg) {
        if (LogLevel.INFO.compareTo(level) >= 0) {
            write("INFO", msg);
        }
    }

    /**
     * Warning level log message
     * @param msg Message to log
     */
    public void w(String msg) {
        if (LogLevel.WARNING.compareTo(level) >= 0) {
            write("WARNING", msg);
        }
    }

    /**
     * Error level log message
     * @param msg Message to log
     */
    public void e(String msg) {
        if (LogLevel.ERROR.compareTo(level) >= 0) {
            write("ERROR", msg);
        }
    }

    public LogLevel getLoggingLevel() {
        return level;
    }

    public void setLoggingLevel(LogLevel level) {
        this.level = level;
    }
}
