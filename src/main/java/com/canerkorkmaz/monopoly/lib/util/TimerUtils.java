package com.canerkorkmaz.monopoly.lib.util;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Small timer based utilities
 */
public abstract class TimerUtils {
    private TimerUtils() {
        throw new AssertionError();
    }

    /**
     * Runs the given listener just once after the delay, immediately starts
     *
     * @param delayInMs Delay to run listener after that many milliseconds passed
     * @param listener  Listener to run
     * @return The created timer
     */
    public static Timer runDelayedOnce(int delayInMs, ActionListener listener) {
        final Timer timer = new Timer(delayInMs, null);
        // Add action listener after initializing so that timer is always defined and java doesn't complain
        timer.addActionListener(e -> {
            listener.actionPerformed(e);
            timer.stop();
        });
        timer.start();
        return timer;
    }
}
