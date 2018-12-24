package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
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
    private final Socket socket;

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
        if (SocketCommandDispatcher.receiveCommandFromSocket(socket, getDispatcher(), logger, inputStream)) {
            return;
        }
        this.run();
    }

    private void send(BaseCommand command) {
        SocketCommandDispatcher.sendCommandToSocket(command, socket, logger, outputStream);
    }


}
