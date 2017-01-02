package com.pinin.alex.data;

import com.pinin.alex.LangH;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;
import java.util.prefs.Preferences;

public class CommonDataFactoryImpl implements CommonDataFactory {

    private Logger logger;
    private Preferences preferences;
    private PrefFacade prefFacade;
    private TextsRepo textsRepo;
    private FontsRepo fontsRepo;
    private ColorsRepo colorsRepo;
    private BordersRepo bordersRepo;

    public CommonDataFactoryImpl() throws IOException {
        logger = Logger.getLogger("com.pinin.alex.langh");
        colorsRepo = new ColorsRepo();

        Preferences root = Preferences.userRoot();
        preferences = root.node("/com/pinin/alex/langh");
        prefFacade = new PrefFacade(preferences);

        fontsRepo = new FontsRepo(prefFacade.getFontSize());

        CharSequence textsSource = getCharsFromResource(prefFacade.getLanguage());
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

    public PrefFacade getPrefFacade() {
        return prefFacade;
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
