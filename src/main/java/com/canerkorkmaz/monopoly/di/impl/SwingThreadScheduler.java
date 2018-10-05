package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.di.interfaces.IThreadScheduler;

import javax.swing.*;

public class SwingThreadScheduler implements IThreadScheduler {
    @Override
    public void scheduleOnce(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
}
