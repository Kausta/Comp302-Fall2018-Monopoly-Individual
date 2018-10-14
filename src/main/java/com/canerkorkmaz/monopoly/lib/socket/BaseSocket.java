package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.threading.NamedThreadScheduler;

public abstract class BaseSocket {
    private final NamedThreadScheduler scheduler;
    private final Event<BaseCommand> onReceiveCommand;
    private final Event<BaseCommand> sendCommand;

    @Injected
    public BaseSocket(String name, EventFactory factory) {
        this.scheduler = new NamedThreadScheduler(name);
        this.onReceiveCommand = factory.createSocketReceiveEvent();
        this.sendCommand = factory.createSocketSendEvent();
    }

    public BaseSocket(String name, Event<BaseCommand> onReceiveCommand, Event<BaseCommand> sendCommand) {
        this.scheduler = new NamedThreadScheduler(name);
        this.onReceiveCommand = onReceiveCommand;
        this.sendCommand = sendCommand;
    }

    public abstract void connect();

    public void run() {
        scheduler.scheduleOnce(this::onRun);
    }

    protected abstract void onRun();

    public Event<BaseCommand> getOnReceiveCommand() {
        return onReceiveCommand;
    }

    public Event<BaseCommand> getSendCommand() {
        return sendCommand;
    }
}
