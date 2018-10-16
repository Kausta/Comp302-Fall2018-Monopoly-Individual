package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.domain.command.ClosedCommand;
import com.canerkorkmaz.monopoly.domain.command.EndOrderCommand;
import com.canerkorkmaz.monopoly.domain.command.OrderRollCommand;
import com.canerkorkmaz.monopoly.domain.command.RemoteCommand;
import com.canerkorkmaz.monopoly.domain.service.PlayerRepository;
import com.canerkorkmaz.monopoly.lib.command.BaseCommand;
import com.canerkorkmaz.monopoly.lib.command.CommandDispatcher;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.event.EventFactory;
import com.canerkorkmaz.monopoly.lib.event.UIEvent;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.typing.Unit;

import java.util.List;
import java.util.Random;

public class GameViewModel {
    private final Random r = new Random();
    private final Logger logger;
    private final CommandDispatcher dispatcher;
    private final PlayerRepository playerRepository;
    private final UIEvent<Unit> closeApplication;
    private final UIEvent<PlayerModel> redrawPanel;
    private final UIEvent<Unit> endOrderTurn;

    private int currentPlayerIndex = 0;

    @Injected
    public GameViewModel(ILoggerFactory loggerFactory,
                         CommandDispatcher dispatcher,
                         PlayerRepository playerRepository,
                         EventFactory eventFactory) {
        this.logger = loggerFactory.createLogger(GameViewModel.class);
        this.dispatcher = dispatcher;
        this.playerRepository = playerRepository;
        this.closeApplication = eventFactory.createUIEvent();
        this.redrawPanel = eventFactory.createUIEvent();
        this.endOrderTurn = eventFactory.createUIEvent();

        dispatcher.subscribe(this::commandHandler);
    }

    private void commandHandler(BaseCommand incomingCommand) {
        logger.i("Got " + incomingCommand.toString() + " command");
        BaseCommand command = incomingCommand;
        if (command instanceof RemoteCommand) {
            command = ((RemoteCommand) command).getInnerCommand();
        }
        logger.i("Handling " + command.toString());
        switch (command.getIdentifier()) {
            case ClosedCommand.IDENTIFIER:
                logger.i("Closing application");
                this.closeApplication.trigger(Unit.INSTANCE);
                break;
            case OrderRollCommand.IDENTIFIER:
                OrderRollCommand roll = (OrderRollCommand) command;
                playerRepository.setInitialRoll(roll.getPlayerName(), roll.getInitialRoll1(), roll.getInitialRoll2());
                redrawPanel.trigger(getCurrentPlayer());
                break;
            case EndOrderCommand.IDENTIFIER:
                int index = ((EndOrderCommand) command).getPlayerIndex();
                this.currentPlayerIndex = index + 1;
                if (currentPlayerIndex == this.playerRepository.getPlayerCount()) {
                    // Start Game
                    this.playerRepository.sortAccordingToInitialRoll();
                    currentPlayerIndex = 0;
                }
                endOrderTurn.trigger(Unit.INSTANCE);
                break;
            default:
                logger.w("Didn't expect command: " + command.toString());
        }
    }

    public List<PlayerModel> getPlayers() {
        return playerRepository.getPlayerList();
    }

    public UIEvent<Unit> getCloseApplication() {
        return closeApplication;
    }

    public PlayerModel getCurrentPlayer() {
        logger.i("Getting player " + currentPlayerIndex);
        return this.playerRepository.getPlayerList().get(currentPlayerIndex);
    }

    public boolean isActivePlayer(PlayerModel playerModel) {
        return getCurrentPlayer().getPlayerName().equals(playerModel.getPlayerName());
    }

    public boolean isFromThisClient(PlayerModel playerModel) {
        return playerRepository.isFromThisClient(playerModel);
    }

    public boolean isRolled(PlayerModel playerModel) {
        return playerModel.getInitialRoll() != 0;
    }

    public void dispatchSetInitialRoll() {
        dispatcher.sendCommand(new OrderRollCommand(getCurrentPlayer().getPlayerName(), r.nextInt(6), r.nextInt(6)));
    }

    public void dispatchEndTurn() {
        dispatcher.sendCommand(new EndOrderCommand(currentPlayerIndex));
    }

    public UIEvent<PlayerModel> getRedrawPanel() {
        return redrawPanel;
    }

    public UIEvent<Unit> getEndOrderTurn() {
        return endOrderTurn;
    }
}
