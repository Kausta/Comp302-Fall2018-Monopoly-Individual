package com.canerkorkmaz.monopoly.view.components;

import com.canerkorkmaz.monopoly.lib.di.DI;
import com.canerkorkmaz.monopoly.lib.logger.ILoggerFactory;
import com.canerkorkmaz.monopoly.lib.logger.Logger;
import com.canerkorkmaz.monopoly.lib.util.GridBagConstraintsBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Form implements ActionListener {
    private Logger logger = DI.get(ILoggerFactory.class).createLogger(Form.class);
    private HashMap<String, Runnable> onClicks;
    private JPanel root;

    private Form() {
        root = new JPanel();
        BoxLayout layout = new BoxLayout(root, BoxLayout.Y_AXIS);
        onClicks = new HashMap<>();
        root.setLayout(layout);
    }

    public JPanel getContent() {
        return this.root;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == null) {
            return;
        }
        logger.i(String.format("Clicked %s button", command));
        Runnable r = onClicks.get(command);
        if (r == null) {
            return;
        }
        r.run();
    }

    public static class Builder {
        private List<Component> components = new ArrayList<>();
        private Color backgroundColor;
        private Form form;

        public Builder() {
            form = new Form();
        }

        public Builder setBackgroundColor(Color color) {
            this.backgroundColor = color;
            return this;
        }

        public Builder addComponent(Component component) {
            components.add(component);
            return this;
        }

        public Builder addLabeledComponent(String labelText, Component component) {
            JPanel container = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            container.setLayout(layout);
            container.setBackground(backgroundColor);

            JLabel label = new JLabel(labelText);
            label.setFont(new Font("Monospaced", Font.PLAIN, 20));
            container.add(label, new GridBagConstraintsBuilder()
                    .setPosition(0, 0)
                    .setSize(1, 1)
                    .setAnchor(GridBagConstraints.LINE_START)
                    .build());
            container.add(component, new GridBagConstraintsBuilder()
                    .setPosition(1, 0)
                    .setSize(1, 1)
                    .setAnchor(GridBagConstraints.LINE_END)
                    .setWeightX(1.)
                    .setFill(GridBagConstraints.HORIZONTAL)
                    .build());

            components.add(container);

            return this;
        }

        public Builder addLabeledText(String labelText, String text) {
            JPanel container = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            container.setLayout(layout);
            container.setBackground(backgroundColor);

            JLabel label = new JLabel(labelText);
            label.setFont(new Font("Monospaced", Font.PLAIN, 20));
            container.add(label, new GridBagConstraintsBuilder()
                    .setPosition(0, 0)
                    .setSize(1, 1)
                    .setAnchor(GridBagConstraints.LINE_START)
                    .build());
            JLabel component = new JLabel(text);
            component.setFont(new Font("Monospaced", Font.PLAIN, 20));
            container.add(component, new GridBagConstraintsBuilder()
                    .setPosition(1, 0)
                    .setSize(1, 1)
                    .setAnchor(GridBagConstraints.LINE_END)
                    .setWeightX(1.)
                    .setFill(GridBagConstraints.HORIZONTAL)
                    .build());

            components.add(container);

            return this;
        }

        public Builder addButton(String text, Runnable onClick) {
            return this.addButton(text, onClick, true);
        }

        public Builder addButton(String text, Runnable onClick, boolean clickable) {
            JButton button = new JButton(text);
            button.setFont(new Font("Monospaced", Font.BOLD, 24));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);

            String identifier = UUID.randomUUID() + " | " + text;
            button.setActionCommand(identifier);
            button.addActionListener(form);
            button.setEnabled(clickable);
            if (clickable) {
                form.onClicks.put(identifier, onClick);
            } else {
                form.onClicks.put(identifier, () -> {
                });
            }

            Dimension size = new Dimension(250, 50);
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);

            JPanel buttonContainer = new JPanel();
            buttonContainer.add(button);
            buttonContainer.setBackground(backgroundColor);

            components.add(buttonContainer);

            return this;
        }

        public Builder addVerticalSpace(int height) {
            components.add(Box.createVerticalStrut(height));

            return this;
        }

        public Form build() {
            form.root.setBackground(backgroundColor);
            for (Component comp : components) {
                form.root.add(comp);
            }
            return form;
        }
    }
}
