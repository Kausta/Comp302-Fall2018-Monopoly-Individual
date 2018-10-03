package com.canerkorkmaz.monopoly.di.interfaces;

import com.canerkorkmaz.monopoly.constants.LogLevel;

public abstract class Logger {
    private LogLevel level;

    public Logger() {
        this(LogLevel.DEBUG);
    }

    public Logger(LogLevel level) {
        this.level = level;
    }

    protected abstract void write(String level, String message);

    public void d(String msg) {
        if (LogLevel.DEBUG.compareTo(level) >= 0) {
            write("DEBUG", msg);
        }
    }

    public void i(String msg) {
        if (LogLevel.INFO.compareTo(level) >= 0) {
            write("INFO", msg);
        }
    }

    public void w(String msg) {
        if (LogLevel.WARNING.compareTo(level) >= 0) {
            write("WARNING", msg);
        }
    }

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
