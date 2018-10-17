package com.canerkorkmaz.monopoly.viewmodel;

import com.canerkorkmaz.monopoly.constants.BoardConfiguration;
import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.data.model.PropertyTileModel;
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
                player.setCanBuyProperty(false);
                player.setBuyableProperty(null);
                if(newRoll.getRoll1() == newRoll.getRoll2()) {
                    player.setShouldRollAgain(true, "You Rolled Double: " + newRoll.getRoll1() + ", " + newRoll.getRoll2());
                } else {
                	player.setShouldRollAgain(false);
                }
                playerMove.trigger((player.getNextTurnReverse() ? -1 : 1) * player.getRoll());
                break;
            case EndTurnCommand.IDENTIFIER:
                PlayerModel currentPlayer = getCurrentPlayer();
                currentPlayer.setRoll(0, 0);
                currentPlayer.setMessage(null);
                currentPlayer.setCanBuyProperty(false);
                currentPlayer.setBuyableProperty(null);
                this.currentPlayerIndex = (((EndTurnCommand) command).getPlayerIndex() + 1) % this.playerRepository.getPlayerCount();
                redrawPanel.trigger(currentPlayer);
                endTurn.trigger(Unit.INSTANCE);
                break;
            case PassCommand.IDENTIFIER:
                boardRepository.handleTilePass(getCurrentPlayer(), ((PassCommand) command).getLocation());
                redrawPanel.trigger(getCurrentPlayer());
                break;
            case EndMovementCommand.IDENTIFIER:
                currentPlayer = getCurrentPlayer();
                // Reset some of the state
                currentPlayer.setNextTurnReverse(false);

                boardRepository.handleTileLand(currentPlayer, currentPlayer.getLocation());
                redrawPanel.trigger(currentPlayer);
                break;
            case BuyPropertyCommand.IDENTIFIER:
                currentPlayer = getCurrentPlayer();
                PropertyTileModel propertyCopy = ((BuyPropertyCommand) command).getProperty();
                PropertyTileModel property = BoardConfiguration.getPropertyTileModel(propertyCopy);
                property.setBought(true);
                currentPlayer.setCanBuyProperty(false);
                currentPlayer.setBuyableProperty(null);
                currentPlayer.getTiles().add(property);
                currentPlayer.setMoney(currentPlayer.getMoney() - property.getPrice());
                redrawPanel.trigger(currentPlayer);
                break;
            case RollOnceCommand.IDENTIFIER:
                RollOnceCommand rollOnceCommand = (RollOnceCommand) command;
                if(rollOnceCommand.getCardRoll() == rollOnceCommand.getPlayerRoll()) {
                    getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + 100);
                    getCurrentPlayer().setMessage("" + rollOnceCommand.getPlayerRoll() + " is " + rollOnceCommand.getCardRoll() + ", you got 100$" );
                } else {
                    getCurrentPlayer().setMessage("" + rollOnceCommand.getPlayerRoll() + " is not " + rollOnceCommand.getCardRoll() + ", no money");
                }
                getCurrentPlayer().setShouldRollOnce(false);
                getCurrentPlayer().setRollOnceRoll(0);
                redrawPanel.trigger(getCurrentPlayer());
                break;
            case SqueezeCommand.IDENTIFIER:
                SqueezeCommand squeezeCommand = (SqueezeCommand) command;
                int sum = squeezeCommand.getRoll1() + squeezeCommand.getRoll2();
                int moneyToGet;
                if(sum == 3 || sum == 4) {
                    moneyToGet = 100;
                } else if(sum == 10 || sum == 11) {
                    moneyToGet = 100;
                } else if(sum == 2 || sum == 12) {
                    moneyToGet = 200;
                } else {
                    moneyToGet = 50;
                }
                getCurrentPlayer().setMessage("You squeezed " + squeezeCommand.getRoll1() + "+" + squeezeCommand.getRoll2() + "=" + sum + ", you collect " + moneyToGet + "$");
                playerRepository.transerMoneyFromEveryone(getCurrentPlayer(), moneyToGet);
                redrawPanel.trigger(getCurrentPlayer());
                getCurrentPlayer().setShouldSqueeze(false);
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
        return currentPlayerIndex == playerModel.getOrder();
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
        // Remote command so that we don't accidentally send local event
        dispatcher.sendCommand(new RemoteCommand(new PassCommand(location)));
    }

    public void dispatchEndMovement() {
        dispatcher.sendCommand(new RemoteCommand(new EndMovementCommand()));
    }

    public void dispatchBuyProperty() {
        dispatcher.sendCommand(new BuyPropertyCommand(getCurrentPlayer().getBuyableProperty()));
    }

    public void dispatchRollOnce() {
        dispatcher.sendCommand(new RollOnceCommand(1 + r.nextInt(6), getCurrentPlayer().getRollOnceRoll()));
    }

    public void dispatchSqueeze() {
        dispatcher.sendCommand(new SqueezeCommand(1 + r.nextInt(6), 1 + r.nextInt(6)));
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
