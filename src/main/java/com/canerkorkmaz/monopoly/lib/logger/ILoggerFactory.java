package com.canerkorkmaz.monopoly.lib.logger;

public interface ILoggerFactory {
    /**
     * Creates a default logger
     *
     * @return Creates a default logger
     */
    Logger createLogger();

    /**
     * Creates a logger with given class' name
     *
     * @param clazz Class to get class name from
     * @return Class aware logger
     */
    Logger createLogger(Class<?> clazz);

}
