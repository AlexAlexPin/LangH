package com.pinin.alex;

import com.pinin.alex.main.*;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;
import java.util.prefs.Preferences;

class CommonDataFactoryImpl implements CommonDataFactory {

    private Logger logger;
    private Preferences preferences;
    private Data data;
    private Texts texts;
    private Fonts fonts;
    private Colors colors;
    private Borders borders;

    CommonDataFactoryImpl() throws IOException
    {
        logger = Logger.getLogger("com.pinin.alex.langh");
        colors = new Colors();

        Preferences root = Preferences.userRoot();
        preferences = root.node("/com/pinin/alex/langh");
        data = new Data(preferences);

        fonts = new Fonts(data.getFontSize());

        CharSequence textsSource = getResourceContent(data.getLanguage());
        texts = new Texts(textsSource);

        borders = new Borders(fonts, texts);

        String pattern = Common.getLogFolder("LangH");
        final int fileSizeLimit = 50000;
        final int logRotationCount = 1;

        Handler handler = new FileHandler(pattern, fileSizeLimit, logRotationCount, true);
        handler.setFormatter(Common.getFormatter());

        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }

    public Preferences getPreferences()
    {
        return preferences;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public Texts getTexts()
    {
        return texts;
    }

    public Data getData()
    {
        return data;
    }

    public Fonts getFonts()
    {
        return fonts;
    }

    public Colors getColors()
    {
        return colors;
    }

    public Borders getBorders()
    {
        return borders;
    }

    /**
     * Returns the specified resource as an <code>ImageIcon</code>.
     * @param resource - a path to get the resource.
     * @return the specified resource as an <code>ImageIcon</code>.
     */
    public ImageIcon getResource(String resource)
    {
        return new ImageIcon(LangH.class.getResource(resource));
    }

    /**
     * Returns the specified resource as an <code>InputStream</code>.
     * @param resource - a path to get the resource.
     * @return the specified resource as an <code>InputStream</code>.
     */
    public CharSequence getResourceContent(String resource)
    {
        return Common.getResourceContent(LangH.class, resource);
    }
}
