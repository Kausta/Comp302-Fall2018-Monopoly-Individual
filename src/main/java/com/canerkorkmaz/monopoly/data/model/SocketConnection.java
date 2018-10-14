package com.canerkorkmaz.monopoly.data.model;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.socket.BaseSocket;

import java.net.Socket;

public class SocketConnection {
    private final EventFactory eventFactory;
    private BaseSocket socket = null;
    private Event<BaseCommand> onReceive;
    private Event<BaseCommand> onSend;

    @Injected
    public SocketConnection(EventFactory factory) {
        this.eventFactory = factory;
    }

    public BaseSocket getSocket() {
        return socket;
    }

    public void setSocket(BaseSocket socket) {
        this.socket = socket;
        this.onReceive = eventFactory.createVMEvent();
        this.socket.getOnReceiveCommand().subscribe(x -> this.onReceive.trigger(x));
        this.onSend = eventFactory.createSocketSendEvent();
        this.onSend.subscribe(x -> this.socket.getSendCommand().trigger(x));
    }

    public Event<BaseCommand> getOnReceive() {
        return onReceive;
    }

    public Event<BaseCommand> getOnSend() {
        return onSend;
    }
}
