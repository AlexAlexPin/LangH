package com.pinin.alex.gui;

import com.pinin.alex.LangH;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class JMenuItemBuilder {

    JMenuItem menuItem = new JMenuItem();

    JMenuItem build() {
        return menuItem;
    }

    JMenuItemBuilder setText(String text) {
        menuItem.setText(text);
        return this;
    }

    JMenuItemBuilder setToolTipText(String tip) {
        menuItem.setToolTipText(tip);
        return this;
    }

    JMenuItemBuilder setFont(Font font) {
        menuItem.setFont(font);
        return this;
    }

    JMenuItemBuilder setIcon(ImageIcon icon) {
        menuItem.setIcon(icon);
        return this;
    }

    JMenuItemBuilder setIcon(String iconPath) {
        menuItem.setIcon(new ImageIcon(LangH.class.getResource(iconPath)));
        return this;
    }

    JMenuItemBuilder addActionListener(ActionListener action) {
        menuItem.addActionListener(action);
        return this;
    }

    JMenuItemBuilder setAccelerator(String keyCombination) {
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyCombination));
        return this;
    }
}
