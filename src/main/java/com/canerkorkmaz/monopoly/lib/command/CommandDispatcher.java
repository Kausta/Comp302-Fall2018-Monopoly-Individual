package com.canerkorkmaz.monopoly.lib.command;

import com.canerkorkmaz.monopoly.lib.event.EventInterface;
import com.canerkorkmaz.monopoly.lib.threading.IThreadScheduler;
import com.canerkorkmaz.monopoly.lib.threading.NamedThreadScheduler;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

public class CommandDispatcher {
    private Vector<Function<BaseCommand, Boolean>> commandFns = new Vector<>();
    private BlockingQueue<BaseCommand> commands = new ArrayBlockingQueue<>(1024);
    private IThreadScheduler scheduler = new NamedThreadScheduler("Command-Dispatcher");

    public CommandDispatcher() {
    }

    public void runDispatcher() {
        scheduler.scheduleOnce(this::run);
    }

    private void run() {
        try {
            BaseCommand command = commands.take();
            Iterator<Function<BaseCommand, Boolean>> it = commandFns.iterator();
            while (it.hasNext()) {
                Function<BaseCommand, Boolean> holder = it.next();
                Boolean b = holder.apply(command);
                if (b) {
                    it.remove();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        this.runDispatcher();
    }

    public void sendCommand(BaseCommand command) {
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
