package com.canerkorkmaz.monopoly.domain.service;

import com.canerkorkmaz.monopoly.data.model.ConnectionData;
import com.canerkorkmaz.monopoly.data.socket.SocketConnection;
import com.canerkorkmaz.monopoly.domain.data.ClientData;
import com.canerkorkmaz.monopoly.domain.data.ServerData;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.command.RemoteCommand;
import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.socket.BaseSocket;
import com.canerkorkmaz.monopoly.lib.socket.FollowerSocket;
import com.canerkorkmaz.monopoly.lib.socket.MasterSocket;

import java.util.function.Function;

public class ConnectionRepository {
    private final Logger logger;
    private final ConnectionData data;
    private final SocketConnection socket;
    private final CommandDispatcher dispatcher;
    private boolean gameStarted = false;

    @Injected
    public ConnectionRepository(ILoggerFactory logger, ConnectionData data, SocketConnection socket, CommandDispatcher dispatcher) {
        this.logger = logger.createLogger(ConnectionRepository.class);
        this.data = data;
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    public boolean isServer() {
        return data.getMode() == ConnectionData.Mode.SERVER;
    }

    public boolean isClient() {
        return data.getMode() == ConnectionData.Mode.CLIENT;
    }

    public ServerData getServerData() {
        if (!this.isServer()) {
            return null;
        }
        return new ServerData(data.getPort());
    }

    public void setServerData(ServerData data) {
        this.data.setMode(ConnectionData.Mode.SERVER);
        this.data.setIp(null);
        this.data.setPort(data.getPort());
    }

    public ClientData getClientData() {
        if (!this.isClient()) {
            return null;
        }
        return new ClientData(data.getIp(), data.getPort());
    }

    public void setClientData(ClientData data) {
        this.data.setMode(ConnectionData.Mode.CLIENT);
        this.data.setIp(data.getIp());
        this.data.setPort(data.getPort());
    }

    public boolean isStarted() {
        return socket != null;
    }

    public void start() {
        BaseSocket baseSocket;
        if (isServer()) {
            baseSocket = DI.get(MasterSocket.class);
        } else if (isClient()) {
            baseSocket = DI.get(FollowerSocket.class);
        } else {
            throw new RuntimeException("Undetermined socket mode");
        }
        baseSocket.connect();
        baseSocket.run();
        socket.setSocket(baseSocket);
        logger.i("Started socket");
    }

    public void sendRemote(BaseCommand command) {
        dispatcher.sendCommand(new RemoteCommand(command));
    }

    public void receiveLocalOnce(Function<BaseCommand, Boolean> fn) {
        dispatcher.subscribeOnce((command) -> {
            logger.d(command.toString());
            if (command instanceof RemoteCommand) {
                return false;
            }
            return fn.apply(command);
        });
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
