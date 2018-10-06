package com.canerkorkmaz.monopoly.lib.threading;

public interface IThreadScheduler {
    void scheduleOnce(Runnable runnable);
}
