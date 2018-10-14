package com.canerkorkmaz.monopoly.lib.threading;

import javax.swing.*;

public class SwingThreadScheduler implements IThreadScheduler {
    @Override
    public void scheduleOnce(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
}
