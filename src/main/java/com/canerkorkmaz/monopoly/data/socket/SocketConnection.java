package com.canerkorkmaz.monopoly.data.socket;

import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.socket.BaseSocket;

public class SocketConnection {
    private BaseSocket socket = null;
    // One client
    private boolean clientConnected = false;

    @Injected
    public SocketConnection() {

    }

    public BaseSocket getSocket() {
        return socket;
    }

    public void setSocket(BaseSocket socket) {
        this.socket = socket;
    }

    public boolean isClientConnected() {
        return clientConnected;
    }

    public void setClientConnected(boolean clientConnected) {
        this.clientConnected = clientConnected;
        if(socket != null) {
            socket.setGameStarted(clientConnected);
        }
    }
}
