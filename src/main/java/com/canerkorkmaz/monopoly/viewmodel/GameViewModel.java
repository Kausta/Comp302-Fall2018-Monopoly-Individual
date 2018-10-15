package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.ClosedCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.command.RemoteCommand;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;

public class GameViewModel {
    private final Logger logger;
    private final CommandDispatcher dispatcher;
    private final UIEvent<Unit> closeApplication;

    @Injected
    public GameViewModel(ILoggerFactory loggerFactory, CommandDispatcher dispatcher, EventFactory eventFactory) {
        this.logger = loggerFactory.createLogger(GameViewModel.class);
        this.dispatcher = dispatcher;
        this.closeApplication = eventFactory.createUIEvent();

        dispatcher.subscribe(this::commandHandler);
    }

    private void commandHandler(BaseCommand incomingCommand) {
        BaseCommand command = incomingCommand;
        if (command instanceof RemoteCommand) {
            command = ((RemoteCommand) command).getInnerCommand();
        }
        switch (command.getIdentifier()) {
            case ClosedCommand.IDENTIFIER:
                logger.i("Closing application");
                this.closeApplication.trigger(Unit.INSTANCE);
                break;
            default:
                logger.w("Didn't expect command: " + command.toString());
        }
    }

    public UIEvent<Unit> getCloseApplication() {
        return closeApplication;
    }
}
