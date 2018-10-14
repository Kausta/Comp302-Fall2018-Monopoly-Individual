package com.canerkorkmaz.monopoly.lib.threading;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class NamedThreadScheduler implements IThreadScheduler {
    private ScheduledExecutorService scheduler;

    public NamedThreadScheduler(String name) {
        this(1, name);
    }

    public NamedThreadScheduler(int poolSize, String name) {
        ThreadFactory factory = new NamedThreadFactory(name);
        this.scheduler = new ScheduledThreadPoolExecutor(poolSize, factory);
    }

    @Override
    public void scheduleOnce(Runnable runnable) {
        scheduler.schedule(runnable, 0, TimeUnit.MICROSECONDS);
    }
}
