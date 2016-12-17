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
    private TextsRepo textsRepo;
    private FontsRepo fontsRepo;
    private ColorsRepo colorsRepo;
    private BordersRepo bordersRepo;

    CommonDataFactoryImpl() throws IOException {
        logger = Logger.getLogger("com.pinin.alex.langh");
        colorsRepo = new ColorsRepo();

        Preferences root = Preferences.userRoot();
        preferences = root.node("/com/pinin/alex/langh");
        data = new Data(preferences);

        fontsRepo = new FontsRepo(data.getFontSize());

        CharSequence textsSource = getCharsFromResource(data.getLanguage());
        textsRepo = new TextsRepo(textsSource);

        bordersRepo = new BordersRepo(fontsRepo, textsRepo, colorsRepo);

        String pattern = Common.getLogFolder("LangH");
        final int fileSizeLimit = 50000;
        final int logRotationCount = 1;

        Handler handler = new FileHandler(pattern, fileSizeLimit, logRotationCount, true);
        handler.setFormatter(Common.getFormatter());

        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Logger getLogger() {
        return logger;
    }

    public TextsRepo getTextsRepo() {
        return textsRepo;
    }

    public Data getData() {
        return data;
    }

    public FontsRepo getFontsRepo() {
        return fontsRepo;
    }

    public ColorsRepo getColorsRepo() {
        return colorsRepo;
    }

    public BordersRepo getBordersRepo() {
        return bordersRepo;
    }

    public ImageIcon getIconFromResource(String resourcePath) {
        return new ImageIcon(LangH.class.getResource(resourcePath));
    }

    public CharSequence getCharsFromResource(String resourcePath) {
        return Common.getResourceContent(LangH.class, resourcePath);
    }
}
