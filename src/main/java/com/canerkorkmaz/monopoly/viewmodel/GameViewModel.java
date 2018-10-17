package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.domain.command.*;
import com.canerkorkmaz.monopoly.domain.service.BoardRepository;
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
    private final BoardRepository boardRepository;
    private final UIEvent<Unit> closeApplication;
    private final UIEvent<PlayerModel> redrawPanel;
    private final UIEvent<Boolean> endOrderTurn;
    private final UIEvent<Integer> playerMove;
    private final UIEvent<Unit> endTurn;

    private int currentPlayerIndex = 0;

    @Injected
    public GameViewModel(ILoggerFactory loggerFactory,
                         CommandDispatcher dispatcher,
                         PlayerRepository playerRepository,
                         BoardRepository boardRepository, EventFactory eventFactory) {
        this.logger = loggerFactory.createLogger(GameViewModel.class);
        this.dispatcher = dispatcher;
        this.playerRepository = playerRepository;
        this.boardRepository = boardRepository;
        this.closeApplication = eventFactory.createUIEvent();
        this.redrawPanel = eventFactory.createUIEvent();
        this.endOrderTurn = eventFactory.createUIEvent();
        this.playerMove = eventFactory.createUIEvent();
        this.endTurn = eventFactory.createUIEvent();

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
                    endOrderTurn.trigger(true);
                } else {
                    endOrderTurn.trigger(false);
                }
                break;
            case RollCommand.IDENTIFIER:
                RollCommand newRoll = (RollCommand) command;
                PlayerModel player = getCurrentPlayer();
                player.setRoll(newRoll.getRoll1(), newRoll.getRoll2());
                if(player.shouldRollAgain()){
                    player.setShouldRollAgain(false);
                } else if(newRoll.getRoll1() == newRoll.getRoll2()) {
                    player.setShouldRollAgain(true);
                }
                playerMove.trigger(player.getRoll());
                break;
            case EndTurnCommand.IDENTIFIER:
                getCurrentPlayer().setRoll(0, 0);
                this.currentPlayerIndex = (((EndTurnCommand) command).getPlayerIndex() + 1) % this.playerRepository.getPlayerCount();
                endTurn.trigger(Unit.INSTANCE);
                break;
            case PassCommand.IDENTIFIER:
                boardRepository.handleTilePass(getCurrentPlayer(), ((PassCommand) command).getLocation());
                redrawPanel.trigger(getCurrentPlayer());
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

    public boolean rolledThisTurn(PlayerModel playerModel) {
        return playerModel.getRoll() != 0;
    }

    public void dispatchSetInitialRoll() {
        dispatcher.sendCommand(new OrderRollCommand(getCurrentPlayer().getPlayerName(), 1 + r.nextInt(6), 1 + r.nextInt(6)));
    }

    public void dispatchEndInitialTurn() {
        dispatcher.sendCommand(new EndOrderCommand(currentPlayerIndex));
    }

    public void dispatchRoll() {
        dispatcher.sendCommand(new RollCommand(getCurrentPlayer().getPlayerName(), 1 + r.nextInt(6), 1 + r.nextInt(6)));
    }

    public void dispatchEndTurn() {
        dispatcher.sendCommand(new EndTurnCommand(currentPlayerIndex));
    }

    public void dispatchHandlePass(int location) {
        dispatcher.sendCommand(new PassCommand(location));
    }

    public UIEvent<PlayerModel> getRedrawPanel() {
        return redrawPanel;
    }

    public UIEvent<Boolean> getEndOrderTurn() {
        return endOrderTurn;
    }

    public UIEvent<Integer> getPlayerMove() {
        return playerMove;
    }

    public UIEvent<Unit> getEndTurn() {
        return endTurn;
    }
}
