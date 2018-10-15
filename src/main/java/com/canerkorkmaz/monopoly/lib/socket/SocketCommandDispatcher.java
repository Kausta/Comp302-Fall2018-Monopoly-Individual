package com.canerkorkmaz.monopoly.lib.socket;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.command.RemoteCommand;
import com.canerkorkmaz.monopoly.lib.logger.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public final class SocketCommandDispatcher {
    private SocketCommandDispatcher() {
    }

    public static boolean receiveCommandFromSocket(Socket socket, CommandDispatcher dispatcher, Logger logger, ObjectInputStream inputStream) {
        try {
            Object obj = inputStream.readObject();
            if (obj == null) {
                logger.i("Closing connection!");
                dispatcher.sendCommand(new ClosedCommand());
                return true;
            }
            if (!(obj instanceof RemoteCommand)) {
                throw new RuntimeException("Received incorrect object");
            }
            RemoteCommand remoteCommand = (RemoteCommand) obj;
            logger.i("Received " + remoteCommand.toString());
            dispatcher.sendCommand(remoteCommand);
        } catch (Exception e) {
            logger.e("Received incorrect object: " + e.getMessage());
            if (e.getMessage().equalsIgnoreCase("Connection reset") || e.getMessage().equalsIgnoreCase("socket closed")) {
                dispatcher.sendCommand(new ClosedCommand());
                return true;
            }
        }
        return false;
    }

    public static void sendCommandToSocket(BaseCommand command, Socket socket, Logger logger, ObjectOutputStream outputStream) {
        try {
            if (command instanceof ClosedCommand) {
                socket.close();
                return;
            }
            // Don't send commands coming from network again
            if (command instanceof RemoteCommand) {
                return;
            }
            logger.i("Sending " + command.toString());
            outputStream.writeObject(new RemoteCommand(command));
        } catch (IOException e) {
            logger.e("Cannot send object: " + e.getMessage());
        }
    }
}
