package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.domain.data.ClientData;
import com.canerkorkmaz.monopoly.domain.service.ConnectionRepository;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.command.RemoteCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FollowerSocket extends BaseSocket {
    private final Logger logger;
    private final ConnectionRepository repository;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket = null;

    @Injected
    public FollowerSocket(ILoggerFactory loggerFactory, ConnectionRepository repository, CommandDispatcher dispatcher) {
        super("Client-BaseSocket", dispatcher);
        this.logger = loggerFactory.createLogger(FollowerSocket.class);
        this.repository = repository;
        getDispatcher().subscribe(this::send);
    }

    @Override
    public void connect() {
        if (!this.repository.isClient()) {
            throw new RuntimeException("Tried to start in client mode while settings are in server mode");
        }
        ClientData data = this.repository.getClientData();
        try {
            socket = new Socket(data.getIp(), data.getPort());
            logger.i("Connected to the server on " + socket.getInetAddress().getHostName() + ":" + socket.getPort());
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to the specified port");
        }
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create input/output streams: " + e.getMessage());
        }
    }

    @Override
    protected void onRun() {
        try {
            Object obj = inputStream.readObject();
            if(obj == null) {
                logger.i("Closing connection!");
                getDispatcher().sendCommand(new ClosedCommand());
                return;
            }
            if(!(obj instanceof RemoteCommand)) {
                throw new RuntimeException("Received incorrect object");
            }
            RemoteCommand remoteCommand = (RemoteCommand) obj;
            logger.i("Received " + remoteCommand.toString());
            getDispatcher().sendCommand(remoteCommand.getInnerCommand());
        } catch (Exception e) {
            logger.e("Received incorrect object: " + e.getMessage());
            if(e.getMessage().equalsIgnoreCase("Connection reset")) {
                getDispatcher().sendCommand(new ClosedCommand());
                return;
            }
        }
        this.run();
    }

    private void send(BaseCommand command) {
        try {
            if(command instanceof ClosedCommand) {
                socket.close();
                return;
            }
            if(!(command instanceof RemoteCommand)) {
                return;
            }
            logger.i("Sending " + command.toString());
            outputStream.writeObject(command);
        } catch (IOException e) {
            logger.e("Cannot send object: " + e.getMessage());
        }
    }
}
