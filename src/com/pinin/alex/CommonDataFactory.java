package com.pinin.alex;

import com.pinin.alex.main.*;

import javax.swing.*;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public interface CommonDataFactory
{
    Preferences getPreferences();

    Logger getLogger();

    Texts getTexts();

    Data getData();

    Fonts getFonts();

    Colors getColors();

    Borders getBorders();

    /**
     * Returns the specified resource as an <code>ImageIcon</code>.
     * @param resource - a path to get the resource.
     * @return the specified resource as an <code>ImageIcon</code>.
     */
    ImageIcon getResource(String resource);

    /**
     * Returns the specified resource as an <code>InputStream</code>.
     * @param resource - a path to get the resource.
     * @return the specified resource as an <code>InputStream</code>.
     */
    CharSequence getResourceContent(String resource);
}
