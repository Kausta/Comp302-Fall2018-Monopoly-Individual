package com.canerkorkmaz.monopoly.di.interfaces;

public interface IThreadScheduler {
    void scheduleOnce(Runnable runnable);
}
