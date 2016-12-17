package com.pinin.alex;

import com.pinin.alex.main.*;

import javax.swing.*;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public interface CommonDataFactory
{
    Preferences getPreferences();

    Logger getLogger();

    TextsRepo getTextsRepo();

    PrefFacade getPrefFacade();

    FontsRepo getFontsRepo();

    ColorsRepo getColorsRepo();

    BordersRepo getBordersRepo();

    ImageIcon getIconFromResource(String resourcePath);

    CharSequence getCharsFromResource(String resource);
}
