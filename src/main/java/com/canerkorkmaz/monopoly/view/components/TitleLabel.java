package com.canerkorkmaz.monopoly.view.components;

import javax.swing.*;
import java.awt.*;

/**
 * A small component for title label
 */
public class TitleLabel extends JLabel {

    public TitleLabel(String text, boolean italic) {
        super(text);
        int fontType = Font.BOLD;
        if (italic) {
            fontType |= Font.ITALIC;
        }
        this.setFont(new Font("Monospaced", fontType, 50));
        this.setAlignmentX(CENTER_ALIGNMENT);
    }

    public TitleLabel(boolean italic) {
        this("Monopoly Individual", italic);
    }

    public TitleLabel() {
        this(true);
    }
}
