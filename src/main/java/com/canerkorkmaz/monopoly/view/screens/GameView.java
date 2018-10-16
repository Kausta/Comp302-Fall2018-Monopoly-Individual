package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.GridBagConstraintsBuilder;
import com.canerkorkmaz.monopoly.view.components.Form;
import com.canerkorkmaz.monopoly.view.components.MonopolyBoxHolder;
import com.canerkorkmaz.monopoly.view.navigation.NavigationView;
import com.canerkorkmaz.monopoly.view.resources.MonopolyImageLoader;
import com.canerkorkmaz.monopoly.viewmodel.GameViewModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameView extends NavigationView {
    private static final int NARROW = 110;
    private static final int WIDE = 220;
    private static final int GRID_NARROW = 11;
    private static final int GRID_WIDE = 22;
    private static final int PLAYER_SIZE = 4;

    private final Logger logger;
    private final GameViewModel viewModel;
    private final MonopolyImageLoader loader;

    private JPanel overlayPane;
    private JPanel rootPane;
    private JPanel left;
    private JPanel right;
    private JPanel rollMenu;
    private JPanel monopolyPanel;
    private MonopolyBoxHolder[] monopolyBoxes;
    private JPanel playerList;
    private Map<String, JPanel> playerPanels;
    private Map<String, JPanel> playerImages;

    private Dimension size;
    private int leftWidth;
    private int rightWidth;
    private int height;

    @Injected
    public GameView(ILoggerFactory loggerFactory, GameViewModel viewModel, MonopolyImageLoader loader) {
        this.logger = loggerFactory.createLogger(GameView.class);
        this.viewModel = viewModel;
        this.loader = loader;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        this.drawUI();
        this.bindEvents();
    }

    private void bindEvents() {
        viewModel.getCloseApplication().subscribe(unit ->
                this.getNavigator().exitApplication());
        viewModel.getRedrawPanel().subscribe(this::populatePlayer);
        viewModel.getEndOrderTurn().subscribe(finished -> {
            if (finished) {
                populatePlayers();
                drawPlayers(viewModel.getPlayers());
                drawMenu(viewModel.getCurrentPlayer());
            } else {
                populatePlayers();
                drawOrderRollMenu(viewModel.getCurrentPlayer());
            }
        });
        viewModel.getPlayerMove().subscribe(change -> {
            PlayerModel model = viewModel.getCurrentPlayer();
            model.setLocation((model.getLocation() + change) % 20);
            drawMenu(model);
            populatePlayer(model);
            drawPlayer(model);
        });
        viewModel.getEndTurn().subscribe(unit -> {
            drawMenu(viewModel.getCurrentPlayer());
        });
    }

    @Override
    public JComponent getRoot() {
        return this.overlayPane;
    }

    private void drawUI() {
        size = this.getNavigator().getSize();
        leftWidth = (size.width * 4) / 7 - 6;
        rightWidth = (size.width * 3) / 7 - 6;
        height = size.height - 10;

        monopolyBoxes = new MonopolyBoxHolder[20];

        left = new JPanel();
        left.setBackground(Colors.BACKGROUND_COLOR);
        left.setLayout(new BorderLayout());
        populateMonopoly();
        drawPlayers(viewModel.getPlayers());
        left.add(monopolyPanel, BorderLayout.CENTER);
        left.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        right = new JPanel();
        right.setBackground(Colors.BACKGROUND_COLOR);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JPanel leftTop = new JPanel();
        leftTop.setBackground(Colors.BACKGROUND_COLOR);
        leftTop.setLayout(new BorderLayout());
        leftTop.setPreferredSize(new Dimension(rightWidth - 5, 2 * height / 3 - 2));

        playerList = new JPanel();
        playerList.setLayout(new BoxLayout(playerList, BoxLayout.Y_AXIS));
        playerList.setBackground(Colors.BACKGROUND_COLOR);

        playerPanels = new HashMap<>();
        populatePlayers();

        JScrollPane playerPane = new JScrollPane(playerList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        playerPane.setBackground(Colors.BACKGROUND_COLOR);

        leftTop.add(playerPane, BorderLayout.CENTER);
        right.add(leftTop);
        right.add(Box.createVerticalStrut(4));

        drawOrderRollMenu(viewModel.getCurrentPlayer());

        JScrollPane actionPane = new JScrollPane(rollMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        actionPane.setPreferredSize(new Dimension(rightWidth - 5, height / 3 - 2));
        actionPane.setBackground(Colors.BACKGROUND_COLOR);

        right.add(actionPane);

        rootPane = new JPanel();
        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.X_AXIS));
        rootPane.setBackground(Colors.BACKGROUND_COLOR);
        rootPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        left.setPreferredSize(new Dimension(leftWidth, height));
        right.setPreferredSize(new Dimension(rightWidth, height));

        rootPane.add(left);
        rootPane.add(Box.createHorizontalStrut(2));
        rootPane.add(right);

        overlayPane = new JPanel(null);
        overlayPane.add(rootPane);

        rootPane.setBounds(0, 0, size.width, size.height);
        overlayPane.setBounds(0, 0, size.width, size.height);

    }

    private JPanel getMonopolySquare(int x, int y, int width, int height) {
        JPanel square = new JPanel();
        square.setPreferredSize(new Dimension(width, height));
        square.setLayout(new GridBagLayout());
        try {
            BufferedImage image = loader.readMonopolyImageAt(x, y);
            if (image == null) {
                throw new IOException("Image not found");
            }
            JLabel picture = new JLabel(new ImageIcon(image));
            picture.setMinimumSize(new Dimension(width, height));
            square.add(picture, new GridBagConstraintsBuilder().setWeightX(1.).setWeightY(1.).build());
        } catch (IOException e) {
            throw new RuntimeException("Cannot load image: " + e.getMessage());
        }
        return square;
    }

    private JPanel populateMonopoly() {
        monopolyPanel = new JPanel();
        monopolyPanel.setLayout(new GridBagLayout());
        monopolyPanel.setBackground(Colors.BACKGROUND_COLOR);
        int pos = 0;
        int arrayIndex = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? GRID_WIDE : GRID_NARROW;
            JComponent component = getMonopolySquare(i, 0, isCorner ? WIDE : NARROW, WIDE);
            MonopolyBoxHolder holder = new MonopolyBoxHolder(component, pos, 0, width, GRID_WIDE);
            monopolyBoxes[arrayIndex++] = holder;
            monopolyPanel.add(component, holder.createConstraints());
            pos += width;
        }
        for (int i = 0; i < 4; i++) {
            JComponent component = getMonopolySquare(5, i + 1, WIDE, NARROW);
            MonopolyBoxHolder holder = new MonopolyBoxHolder(component, GRID_WIDE + 4 * GRID_NARROW, GRID_WIDE + i * GRID_NARROW, GRID_WIDE, GRID_NARROW);
            monopolyBoxes[arrayIndex++] = holder;
            monopolyPanel.add(component, holder.createConstraints());
        }
        pos = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? GRID_WIDE : GRID_NARROW;
            pos += width;
            JComponent component = getMonopolySquare(5 - i, 5, isCorner ? WIDE : NARROW, WIDE);
            MonopolyBoxHolder holder = new MonopolyBoxHolder(component, 2 * GRID_WIDE + 4 * GRID_NARROW - pos, GRID_WIDE + 4 * GRID_NARROW, width, GRID_WIDE);
            monopolyBoxes[arrayIndex++] = holder;
            monopolyPanel.add(component, holder.createConstraints());
        }
        for (int i = 3; i >= 0; i--) {
            JComponent component = getMonopolySquare(0, 5 - i - 1, WIDE, NARROW);
            MonopolyBoxHolder holder = new MonopolyBoxHolder(component, 0, GRID_WIDE + i * GRID_NARROW, GRID_WIDE, GRID_NARROW);
            monopolyBoxes[arrayIndex++] = holder;
            monopolyPanel.add(component, holder.createConstraints());
        }
        JPanel middle = new JPanel();
        middle.setBackground(Colors.BACKGROUND_COLOR);
        monopolyPanel.add(middle, new GridBagConstraintsBuilder()
                .setPosition(GRID_WIDE, GRID_WIDE)
                .setSize(GRID_WIDE + 4 * GRID_NARROW, GRID_WIDE + 4 * GRID_NARROW)
                .build());
        return monopolyPanel;
    }

    private void populatePlayers() {
        java.util.List<PlayerModel> players = viewModel.getPlayers();
        for (int i = 0; i < players.size(); i += 3) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            for (int j = 0; j < 3 && i + j < players.size(); j++) {
                PlayerModel player = players.get(i + j);
                JPanel playerPanel = populatePlayer(player);
                row.add(playerPanel);
            }
            playerList.add(row);
        }
    }

    private JPanel populatePlayer(PlayerModel player) {
        boolean currentPlayer = viewModel.isActivePlayer(player);

        String name = player.getPlayerName();
        JPanel outer = playerPanels.get(name);
        if (outer == null) {
            outer = new JPanel();
            outer.setPreferredSize(new Dimension(rightWidth / 4, height / 4));
            outer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
        outer.removeAll();
        Form.Builder builder = new Form.Builder()
                .addLabeledText("Player ", player.getPlayerName());
        if (!player.isActive()) {
            builder.addLabeledText("Status: ", "BANKRUPT :(");
        } else if (currentPlayer) {
            builder.addLabeledText("Status: ", "TURN");
        } else {
            builder.addLabeledText("Status: ", "Waiting...");
        }
        if (viewModel.isRolled(player)) {
            builder.addLabeledText("Initial Roll: ", "" + player.getInitialRollString());
        }
        JPanel panel = builder
                .addLabeledText("Money: ", "" + player.getMoney())
                .build()
                .getContent();
        Color bg = currentPlayer ? player.getBackgroundColor() : player.getPlayerColor();
        panel.setBackground(bg);
        outer.setBackground(bg);
        outer.add(panel);
        playerPanels.put(name, outer);
        redrawComponent(outer);
        return outer;
    }

    private JPanel getInitialMenu(PlayerModel model) {
        boolean isCurrentUser = viewModel.isFromThisClient(model);
        return new Form.Builder()
                .addLabeledText("Roll For Ordering: ", model.getPlayerName())
                .addButton("ROLL", () -> {
                    viewModel.dispatchSetInitialRoll();
                    drawOrderRollMenu(model);
                }, isCurrentUser && !viewModel.isRolled(model))
                .addButton("END TURN", () -> {
                    viewModel.dispatchEndInitialTurn();
                    drawOrderRollMenu(model);
                }, isCurrentUser && viewModel.isRolled(model))
                .build()
                .getContent();
    }

    private void drawOrderRollMenu(PlayerModel model) {
        if (rollMenu == null) {
            rollMenu = new JPanel();
        }
        rollMenu.removeAll();
        JPanel panel = getInitialMenu(model);
        rollMenu.add(panel);
        redrawComponent(right);
    }

    private JPanel getMenu(PlayerModel model) {
        boolean isCurrentUser = viewModel.isFromThisClient(model);
        if (!viewModel.rolledThisTurn(model)) {
            return new Form.Builder()
                    .addLabeledText("Turn of: ", model.getPlayerName())
                    .addButton("ROLL", viewModel::dispatchRoll, isCurrentUser && !viewModel.isRolled(model))
                    .build()
                    .getContent();
        } else {
            return new Form.Builder()
                    .addLabeledText("Turn of: ", model.getPlayerName())
                    .addLabeledText("Rolled: ", model.getRollString())
                    .addButton("END TURN", viewModel::dispatchEndTurn, isCurrentUser)
                    .build()
                    .getContent();
        }
    }

    private void drawMenu(PlayerModel model) {
        if (rollMenu == null) {
            rollMenu = new JPanel();
        }
        rollMenu.removeAll();
        JPanel panel = getMenu(model);
        rollMenu.add(panel);
        redrawComponent(right);
    }

    private void drawPlayers(java.util.List<PlayerModel> playerList) {
        if (playerImages == null) {
            playerImages = new HashMap<>();
        }

        for (PlayerModel playerModel : playerList) {
            drawPlayer(playerModel);
        }
    }

    private void redrawComponent(JComponent component) {
        JComponent root = getRoot();
        if (root != null) {
            root.revalidate();
            root.repaint(component.getBounds());
        }
    }

    private void drawPlayer(PlayerModel player) {
        int playerLocation = player.getLocation();
        MonopolyBoxHolder panel = monopolyBoxes[playerLocation];
        int x = panel.getGridX();
        int y = panel.getGridY();

        int location = player.getOrder();

        int xOffset;
        int yOffset;
        if ((playerLocation >= 0 && playerLocation <= 5) || (playerLocation >= 10 && playerLocation <= 15)) {
            yOffset = 1 + (location / 4) * (PLAYER_SIZE + 1);
            xOffset = 1 + (location % 4) * (PLAYER_SIZE + 1);
        } else {
            xOffset = 1 + (location / 4) * (PLAYER_SIZE + 1);
            yOffset = 1 + (location % 4) * (PLAYER_SIZE + 1);
        }

        JPanel playerBox = playerImages.get(player.getPlayerName());
        if (playerBox == null) {
            BufferedImage playerImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = playerImage.createGraphics();

            graphics.setPaint(player.getBackgroundColor());
            graphics.fillRect(0, 0, playerImage.getWidth(), playerImage.getHeight());

            playerBox = new JPanel();
            playerBox.setBackground(new Color(0, 0, 0, 0));
            playerBox.add(new JLabel(new ImageIcon(playerImage)));
        }

        monopolyPanel.remove(playerBox);
        monopolyPanel.validate();

        monopolyPanel.add(playerBox, new GridBagConstraintsBuilder()
                .setPosition(x + yOffset, y + xOffset)
                .setSize(PLAYER_SIZE, PLAYER_SIZE)
                .build());
        monopolyPanel.setComponentZOrder(playerBox, 0);

        logger.i("Placing player at " + playerLocation + " to " + (x + xOffset) + ", " + (y + yOffset));
        playerImages.put(player.getPlayerName(), playerBox);

        monopolyPanel.validate();
        monopolyPanel.repaint();
    }


}
