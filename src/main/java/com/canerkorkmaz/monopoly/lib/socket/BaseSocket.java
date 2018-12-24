package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.threading.NamedThreadScheduler;

public abstract class BaseSocket {
    private final NamedThreadScheduler scheduler;
    private final CommandDispatcher dispatcher;
    private boolean gameStarted = false;

    @Injected
    public BaseSocket(String name, CommandDispatcher dispatcher) {
        this.scheduler = new NamedThreadScheduler(name);
        this.dispatcher = dispatcher;
    }

    public abstract void connect();

    public void run() {
        scheduler.scheduleOnce(this::onRun);
    }

    protected abstract void onRun();

    public CommandDispatcher getDispatcher() {
        return dispatcher;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
