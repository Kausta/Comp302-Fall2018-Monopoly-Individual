package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.domain.data.ServerData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MasterSocket extends BaseSocket {
    private final Logger logger;
    private final ConnectionRepository repository;
    private final ArrayList<MasterConnectionSocket> connectionSockets = new ArrayList<>();
    private ServerSocket socket = null;

    @Injected
    public MasterSocket(ILoggerFactory factory, ConnectionRepository repository, EventFactory eventFactory) {
        super("Server-BaseSocket", eventFactory);
        this.logger = factory.createLogger(MasterSocket.class);
        this.repository = repository;

    }

    @Override
    public void connect() {
        if (!this.repository.isServer()) {
            throw new RuntimeException("Tried to start in server mode while settings are in client mode");
        }
        ServerData data = this.repository.getServerData();
        try {
            socket = new ServerSocket(data.getPort());
            logger.i("Started server on " + socket.getInetAddress().getHostName() + ":" + socket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to the specified port");
        }
    }

    @Override
    protected void onRun() {
        if (socket == null) {
            return;
        }
        try {
            Socket client = socket.accept();
            if(repository.isGameStarted()) {
                throw new IOException("Not accepting connections when started, rejected");
            }
            MasterConnectionSocket connectionSocket = new MasterConnectionSocket(DI.get(ILoggerFactory.class), client, getOnReceiveCommand(), getSendCommand());
            connectionSocket.connect();
            connectionSocket.run();
            connectionSockets.add(connectionSocket);
        } catch (IOException e) {
            logger.e("Cannot connect to client: " + e.getMessage());
        }
        this.run();
    }
}
