package com.canerkorkmaz.monopoly.di;

import com.canerkorkmaz.monopoly.di.impl.ClassNameLogger;
import com.canerkorkmaz.monopoly.di.impl.DefaultLogger;
import com.canerkorkmaz.monopoly.di.interfaces.Logger;

public class LoggerFactory {
    private static LoggerFactory instance = null;

    private LoggerFactory() {

    }

    public static LoggerFactory getInstance() {
        if(instance == null) {
            synchronized (LoggerFactory.class) {
                if(instance == null) {
                    instance = new LoggerFactory();
                }
            }
        }
        return instance;
    }

    public Logger createLogger() {
        return new DefaultLogger();
    }

    public Logger createLogger(Class<?> clazz) {
        return new ClassNameLogger(clazz);
    }
}
