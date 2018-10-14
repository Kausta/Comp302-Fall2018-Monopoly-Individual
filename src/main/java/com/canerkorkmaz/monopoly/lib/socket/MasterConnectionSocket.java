package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.command.RemoteCommand;
import com.canerkorkmaz.monopoly.lib.event.Event;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MasterConnectionSocket extends BaseSocket {
    private final Logger logger;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    public MasterConnectionSocket(ILoggerFactory loggerFactory, Socket socket, CommandDispatcher dispatcher) {
        super("Master-Connection", dispatcher);
        this.logger = loggerFactory.createLogger(MasterConnectionSocket.class);
        this.socket = socket;
        dispatcher.subscribe(this::send);
    }

    @Override
    public void connect() {
        logger.i("Connected to the client " + socket.getInetAddress().getHostName() + ":" + socket.getPort());
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
                getDispatcher().sendCommand((new ClosedCommand()));
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
                getDispatcher().sendCommand((new ClosedCommand()));
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
