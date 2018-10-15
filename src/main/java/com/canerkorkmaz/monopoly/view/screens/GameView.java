package com.canerkorkmaz.monopoly.view.screens;

import com.canerkorkmaz.monopoly.constants.Colors;
import com.canerkorkmaz.monopoly.lib.di.Injected;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.GridBagConstraintsBuilder;
import com.canerkorkmaz.monopoly.view.navigation.NavigationView;
import com.canerkorkmaz.monopoly.view.resources.MonopolyImageLoader;
import com.canerkorkmaz.monopoly.viewmodel.GameViewModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameView extends NavigationView {
    private static final int WIDTH = 110;
    private static final int HEIGHT = 220;
    private static final int GRID_WIDTH = 11;
    private static final int GRID_HEIGHT = 22;
    private final Logger logger;
    private final GameViewModel viewModel;
    private final MonopolyImageLoader loader;
    private JPanel rootPane;
    private JPanel left;
    private JPanel right;
    private JComponent[] monopolyBoxes;

    @Injected
    public GameView(ILoggerFactory loggerFactory, GameViewModel viewModel, MonopolyImageLoader loader) {
        this.logger = loggerFactory.createLogger(GameView.class);
        this.viewModel = viewModel;
        this.loader = loader;
    }

    @Override
    public void onEnter() {
        super.onEnter();

        final Dimension size = this.getNavigator().getSize();

        monopolyBoxes = new JComponent[20];

        left = new JPanel();
        left.setBackground(Colors.BACKGROUND_COLOR);
        left.setLayout(new BorderLayout());
        JPanel monopolyPanel = new JPanel();
        monopolyPanel.setLayout(new GridBagLayout());
        monopolyPanel.setBackground(Colors.BACKGROUND_COLOR);
        int pos = 0;
        int arrayIndex = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? 22 : 11;
            JComponent component = getMonopolySquare(i, 0, isCorner ? HEIGHT : WIDTH, HEIGHT);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(pos, 0)
                    .setSize(width, 22)
                    .build());
            pos += width;
        }
        for (int i = 0; i < 4; i++) {
            JComponent component = getMonopolySquare(5, i+1, HEIGHT, WIDTH);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(22 + 4 * 11, 22 + i * 11)
                    .setSize(22, 11)
                    .build());
        }
        pos = 0;
        for (int i = 0; i < 6; i++) {
            boolean isCorner = (i % 5) == 0;
            int width = isCorner ? 22 : 11;
            pos += width;
            JComponent component = getMonopolySquare(5 - i, 5, isCorner ? HEIGHT : WIDTH, HEIGHT);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition( 2 * 22 + 4 * 11 - pos, 22 + 4 * 11)
                    .setSize(width, 22)
                    .build());
        }
        for (int i = 0; i < 4; i++) {
            JComponent component = getMonopolySquare(0, 5 - i - 1, HEIGHT, WIDTH);
            monopolyBoxes[arrayIndex++] = component;
            monopolyPanel.add(component, new GridBagConstraintsBuilder()
                    .setPosition(0, 22 + i * 11)
                    .setSize(22, 11)
                    .build());
        }
        JPanel middle = new JPanel();
        middle.setBackground(Colors.BACKGROUND_COLOR);
        monopolyPanel.add(middle, new GridBagConstraintsBuilder()
                .setPosition(22, 22)
                .setSize(22 + 4 * 11, 22 + 4 * 11)
                .build());
        left.add(monopolyPanel, BorderLayout.CENTER);

        left.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        right = new JPanel();
        right.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        right.setBackground(Colors.BACKGROUND_COLOR);

        rootPane = new JPanel();
        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.X_AXIS));
        rootPane.setBackground(Colors.BACKGROUND_COLOR);
        rootPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        left.setPreferredSize(new Dimension((size.width * 3) / 5 - 6, size.height - 10));
        right.setPreferredSize(new Dimension((size.width * 2) / 5 - 6, size.height - 10));

        rootPane.add(left);
        rootPane.add(Box.createHorizontalStrut(2));
        rootPane.add(right);

        this.bindEvents();
    }

    private void bindEvents() {
        viewModel.getCloseApplication().subscribe(unit ->
                this.getNavigator().exitApplication());
    }

    @Override
    public JComponent getRoot() {
        return this.rootPane;
    }

    private JPanel getMonopolySquare(int x, int y, int width, int height) {
        JPanel square = new JPanel();
        square.setPreferredSize(new Dimension(width, height));
        square.setLayout(new GridBagLayout());
        try {
            BufferedImage image = loader.readMonopolyImageAt(x, y);
            if(image == null) {
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
}
