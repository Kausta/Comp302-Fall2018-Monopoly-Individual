package com.canerkorkmaz.monopoly.lib.event;

import com.canerkorkmaz.monopoly.lib.threading.IThreadScheduler;

import java.util.Observable;
import java.util.Observer;

public class Event<T> extends Observable {
    private IThreadScheduler scheduler;

    public Event(IThreadScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void trigger(T data) {
        setChanged();
        notifyObservers(data);
        clearChanged();
    }

    @SuppressWarnings("unchecked")
    public void subscribe(EventInterface<T> fn) {
        addObserver((o, arg) -> {
            scheduler.scheduleOnce(() -> fn.handle((T) arg));
        });
    }

    @SuppressWarnings("unchecked")
    public void subscribeOnce(EventInterface<T> fn) {
        addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                fn.handle((T) arg);
                Event.this.deleteObserver(this);
            }
        });
    }
}
