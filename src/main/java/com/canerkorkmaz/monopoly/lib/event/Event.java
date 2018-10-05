package com.canerkorkmaz.monopoly.lib.event;

import com.canerkorkmaz.monopoly.di.Injected;
import com.canerkorkmaz.monopoly.di.interfaces.IThreadScheduler;

import java.util.Observable;

public class Event<T> extends Observable {
    private IThreadScheduler scheduler;

    @Injected
    public Event(IThreadScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void trigger(T data) {
        setChanged();
        notifyObservers(data);
        clearChanged();
    }

    @SuppressWarnings("unchecked")
    public void runIfNotHandled(EventInterface<T> fn) {
        addObserver((o, arg) -> scheduler.scheduleOnce(() -> fn.handle((T) arg)));
    }
}
