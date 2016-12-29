package com.pinin.alex.gui;

import com.pinin.alex.LangH;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class JButtonBuilder {

    private JButton button = new JButton();

    JButton build() {
        return button;
    }

    JButtonBuilder setText(String text) {
        button.setText(text);
        return this;
    }

    JButtonBuilder setToolTipText(String tip) {
        button.setToolTipText(tip);
        return this;
    }

    JButtonBuilder setFont(Font font) {
        button.setFont(font);
        return this;
    }

    JButtonBuilder setIcon(ImageIcon icon) {
        button.setIcon(icon);
        return this;
    }

    JButtonBuilder setIcon(String iconPath) {
        button.setIcon(new ImageIcon(LangH.class.getResource(iconPath)));
        return this;
    }

    JButtonBuilder addActionListener(ActionListener action) {
        button.addActionListener(action);
        return this;
    }
}
