package com.canerkorkmaz.monopoly.data.socket;

import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.socket.BaseSocket;

public class SocketConnection {
    private BaseSocket socket = null;

    @Injected
    public SocketConnection() {

    }

    public BaseSocket getSocket() {
        return socket;
    }

    public void setSocket(BaseSocket socket) {
        this.socket = socket;
    }
}
