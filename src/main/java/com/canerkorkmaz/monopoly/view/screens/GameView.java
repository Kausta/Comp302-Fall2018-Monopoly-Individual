package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.data.model.PlayerModel;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.GridBagConstraintsBuilder;
import com.canerkorkmaz.monopoly.view.components.Form;
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

    private final Logger logger;
    private final GameViewModel viewModel;
    private final MonopolyImageLoader loader;

    private JPanel rootPane;
    private JPanel left;
    private JPanel right;
    private JPanel rollMenu;
    private JComponent[] monopolyBoxes;
    private JPanel playerList;
    private Map<String, JPanel> playerPanels;

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
        viewModel.getEndOrderTurn().subscribe(unit -> {
            populatePlayers();
            drawOrderRollMenu(viewModel.getCurrentPlayer());
        });
    }

    @Override
    public JComponent getRoot() {
        return this.rootPane;
    }

    private void drawUI() {
        size = this.getNavigator().getSize();
        leftWidth = (size.width * 4) / 7 - 6;
        rightWidth = (size.width * 3) / 7 - 6;
        height = size.height - 10;

        monopolyBoxes = new JComponent[20];

        left = new JPanel();
        left.setBackground(Colors.BACKGROUND_COLOR);
        left.setLayout(new BorderLayout());
        left.add(populateMonopoly(), BorderLayout.CENTER);
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
        JPanel monopolyPanel = new JPanel();
        monopolyPanel.setLayout(new GridBagLayout());
        monopolyPanel.setBackground(Colors.BACKGROUND_COLOR);
        int pos = 0;
        int arrayIndex = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? GRID_WIDE : GRID_NARROW;
            JComponent component = getMonopolySquare(i, 0, isCorner ? WIDE : NARROW, WIDE);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(pos, 0)
                    .setSize(width, GRID_WIDE)
                    .build());
            pos += width;
        }
        for (int i = 0; i < 4; i++) {
            JComponent component = getMonopolySquare(5, i + 1, WIDE, NARROW);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(GRID_WIDE + 4 * GRID_NARROW, GRID_WIDE + i * GRID_NARROW)
                    .setSize(GRID_WIDE, GRID_NARROW)
                    .build());
        }
        pos = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? GRID_WIDE : GRID_NARROW;
            pos += width;
            JComponent component = getMonopolySquare(5 - i, 5, isCorner ? WIDE : NARROW, WIDE);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(2 * GRID_WIDE + 4 * GRID_NARROW - pos, GRID_WIDE + 4 * GRID_NARROW)
                    .setSize(width, GRID_WIDE)
                    .build());
        }
        for (int i = 0; i < 4; i++) {
            JComponent component = getMonopolySquare(0, 5 - i - 1, WIDE, NARROW);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(0, GRID_WIDE + i * GRID_NARROW)
                    .setSize(GRID_WIDE, GRID_NARROW)
                    .build());
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
            logger.i("Panel row " + i);
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
                    viewModel.dispatchEndTurn();
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
        redrawComponent(rollMenu);
    }

    private void redrawComponent(JComponent component) {
        JComponent root = getRoot();
        if (root != null) {
            root.revalidate();
            root.repaint(component.getBounds());
        }
    }
}
