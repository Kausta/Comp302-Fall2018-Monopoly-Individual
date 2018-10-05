package com.canerkorkmaz.monopoly.di.impl;

import com.canerkorkmaz.monopoly.di.interfaces.IThreadScheduler;
import com.canerkorkmaz.monopoly.lib.NamedThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ViewModelThreadScheduler implements IThreadScheduler {
    private final ThreadFactory factory = new NamedThreadFactory("ViewModel");
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1, factory);

    @Override
    public void scheduleOnce(Runnable runnable) {
        scheduler.schedule(runnable, 0, TimeUnit.MICROSECONDS);
    }
}
