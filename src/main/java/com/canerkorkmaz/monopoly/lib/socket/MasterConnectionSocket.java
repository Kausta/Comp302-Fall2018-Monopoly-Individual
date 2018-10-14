package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
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

    public MasterConnectionSocket(ILoggerFactory loggerFactory, Socket socket, Event<BaseCommand> onReceiveCommand, Event<BaseCommand> sendCommand) {
        super("Master-Connection", onReceiveCommand, sendCommand);
        this.logger = loggerFactory.createLogger(MasterConnectionSocket.class);
        this.socket = socket;
        sendCommand.subscribe(this::send);
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
            if(!(obj instanceof BaseCommand)) {
                throw new RuntimeException("Received incorrect object");
            }
            logger.i("Received " + ((BaseCommand) obj).getIdentifier());
            getOnReceiveCommand().trigger((BaseCommand)obj);
        } catch (Exception e) {
            logger.e("Received incorrect object: " + e.getMessage());
            if(e.getMessage().equalsIgnoreCase("Connection reset")) {
                getOnReceiveCommand().trigger(new ClosedCommand());
                return;
            }
        }
        this.run();
    }

    private void send(BaseCommand command) {
        try {
            logger.i("Sending " + command.getIdentifier());
            outputStream.writeObject(command);
        } catch (IOException e) {
            logger.e("Cannot send object: " + e.getMessage());
        }
    }
}
