package com.canerkorkmaz.monopoly.lib.command;

import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.EventInterface;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.threading.IThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.NamedThreadScheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class CommandDispatcher {
    private final Logger logger;
    private final LinkedList<Function<BaseCommand, Boolean>> commandFns = new LinkedList<>();
    private final BlockingQueue<BaseCommand> commands = new ArrayBlockingQueue<>(1024);
    private final IThreadScheduler scheduler = new NamedThreadScheduler("Command-Dispatcher");

    @Injected
    public CommandDispatcher(ILoggerFactory logger) {
        this.logger = logger.createLogger(CommandDispatcher.class);
    }

    public void runDispatcher() {
        scheduler.scheduleOnce(this::run);
    }

    private void run() {
        while (true) {
            try {
                BaseCommand command = commands.take();
                logger.i("Got command " + command.toString());
                logger.d("Starting handling with " + commandFns.size());
                Iterator<Function<BaseCommand, Boolean>> it = commandFns.iterator();
                while (it.hasNext()) {
                    Function<BaseCommand, Boolean> holder = it.next();
                    Boolean b = holder.apply(command);
                    if (b) {
                        it.remove();
                    }
                }
                logger.d("Finished with " + commandFns.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void sendCommand(BaseCommand command) {
        logger.i("Sending command " + command.toString());
        commands.add(command);
    }

    public void subscribe(EventInterface<BaseCommand> eventFn) {
        commandFns.add((command) -> {
            eventFn.handle(command);
            return false;
        });
    }

    public void subscribeOnce(Function<BaseCommand, Boolean> eventFn) {
        commandFns.add(eventFn);
    }
}
