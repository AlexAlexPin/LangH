//
//	This file is part of LangH.
//
//	LangH is a program that allows to keep foreign phrases and test yourself.
//	Copyright � 2015 Aleksandr Pinin. e-mail: <alex.pinin@gmail.com>
//
//	LangH is free software: you can redistribute it and/or modify
//	it under the terms of the GNU General Public License as published by
//	the Free Software Foundation, either version 3 of the License, or
//	(at your option) any later version.
//
//	LangH is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with LangH.  If not, see <http://www.gnu.org/licenses/>.
//

package com.pinin.alex.main;

import java.awt.*;
import java.io.*;
import java.util.prefs.*;
import com.pinin.alex.*;

/**
 * Loads and operates the main data.
 */
@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection", "unused"})
public class Data
{
//
// Variables
//
	// data
	
	private Dimension screenSize;
	
	// default ratios between the screen size and the main frame
	
	private final static int DEFAULT_WIDTH_COEF  = 2;
	private final static int DEFAULT_HEIGTH_COEF = 2;
	
	// preferences links
	
	private final static String MAIN_FRAME_WIDTH_PREF     = "main_frame_width";
	private final static String MAIN_FRAME_HEIGTH_PREF    = "main_frame_heigth";
	private final static String MAIN_FRAME_EXT_STATE_PREF = "main_frame_ext_state";
	private final static String FONT_SIZE_PREF   		  = "font_size_heigth";
	private final static String LAST_PATH_PREF   		  = "last_path";
	private final static String LANGUAGE_PREF   		  = "lang";
	
	private final static String DATA_PATH_PREF   		  = "data_path";
	
	private final static String SHOW_EXERCISE             = "show_exercise";
	private final static String SHOW_WORKLIST             = "show_worklist";
	private final static String SHOW_FILLER               = "show_filler";
	private final static String SHOW_DICTIONARY           = "show_dictionary";
	private final static String SHOW_TAGS                 = "show_tags";
	private final static String SHOW_SEARCH               = "show_search";
	private final static String SHOW_RECORDER             = "show_recorder";
	
	// font sizes
	public final static int FONT_NORMAL = 12;
	public final static int FONT_BIG    = 18;
	public final static int FONT_HUGE   = 24;
	
	// common mains
	private Preferences preferences;

	/**
	 * Constructor.
	 * @param preferences - program preferences.
	 */
	public Data(Preferences preferences)
	{
        this.preferences = preferences;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * Gets from preferences and returns the size of the frame
	 * @return the size of the frame
	 */
	public Dimension getSize() 
	{
		int frameWidth  = preferences.getInt(MAIN_FRAME_WIDTH_PREF,  0);
		int frameHeight = preferences.getInt(MAIN_FRAME_HEIGTH_PREF, 0);

		if (frameWidth  == 0 || frameHeight == 0) 
		{
			return getDefaultSize();
		}
		return new Dimension(frameWidth, frameHeight);
	}
	
	/**
	 * Returns the default frame size
	 * @return the default frame size
	 */
	public Dimension getDefaultSize() 
	{
		final int frameWidth  = screenSize.width/DEFAULT_WIDTH_COEF;
		final int frameHeight = screenSize.height/DEFAULT_HEIGTH_COEF;
		return new Dimension(frameWidth, frameHeight);
	}
	
	/**
	 * Puts the specified frame size to preferences
	 * @param size - a new size
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public void putSize(Dimension size) throws NullPointerException
	{
		if (size == null) throw new NullPointerException("null value in Data.putSize()");
		preferences.putInt(MAIN_FRAME_HEIGTH_PREF, size.height);
		preferences.putInt(MAIN_FRAME_WIDTH_PREF,  size.width);
	}
	
	/**
	 * Gets from preferences and returns the extended state of the frame
	 * @return the extended state of the frame
	 */
	public int getExtendedState()
	{
		return preferences.getInt(MAIN_FRAME_EXT_STATE_PREF, 0);
	}
	
	/**
	 * Puts the specified frame extended state to preferences
	 * @param extendedState - a new extended state
	 * @throws IllegalArgumentException in case of illegal value
	 */
	public void putExtendedState(int extendedState) throws IllegalArgumentException
	{
		if (extendedState != Frame.NORMAL && extendedState != Frame.ICONIFIED 
				&& extendedState != Frame.MAXIMIZED_HORIZ && extendedState != Frame.MAXIMIZED_VERT 
				&& extendedState != Frame.MAXIMIZED_BOTH)
			throw new IllegalArgumentException("illegal argument in Data.putExtendedState()");
		preferences.putInt(MAIN_FRAME_EXT_STATE_PREF, extendedState);
	}
	
	/**
	 * Returns the screen size
	 * @return the screen size
	 */
	public Dimension getScreenSize() 
	{
		return screenSize;
	}
	
	/**
	 * Returns the font size from preferences
	 * @return the font size from preferences
	 */
	public int getFontSize() 
	{
		return preferences.getInt(FONT_SIZE_PREF, getDefaultFontSize());
	}
	
	/**
	 * Returns the default font size
	 * @return the default font size
	 */
	private int getDefaultFontSize()
	{
		return FONT_NORMAL;
	}
	
	/**
	 * Puts the specified value as the font size
	 * @param size - a new font size
	 * @throws IllegalArgumentException in case of illegal value
	 */
	public void putFontSize(int size) throws IllegalArgumentException
	{
		if (size < 0) throw new IllegalArgumentException("illegal argument in Data.putFontSize()");
		preferences.putInt(FONT_SIZE_PREF, size);
	}
	
	/**
	 * Returns the latest opened folder path that is got from preferences
	 * @return the latest opened folder path that is got from preferences
	 */
	public String getLastPath()
	{
		return preferences.get(LAST_PATH_PREF, "");
	}
	
	/**
	 * Puts the latest opened folder path to preferences
	 * @param path - a new path
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public void putLastPath(String path) throws NullPointerException
	{
		if (path == null) throw new NullPointerException("null value in Data.putLastPath()");
		preferences.put(LAST_PATH_PREF, path);
	}
	
	/**
	 * Returns language value from the preferences
	 * @return language value from the preferences
	 */
	public String getLanguage() 
	{
		return preferences.get(LANGUAGE_PREF, Texts.PH_TEXT);
	}
	
	/**
	 * Puts a new language value to the preferences
	 * @param lang - a new language
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public void putLanguage(String lang) 
	{
		if (lang == null) throw new NullPointerException("null value in Data.putLanguage()");
		preferences.put(LANGUAGE_PREF, lang);
	}
	
	/**
	 * Returns the path of the data object that is got from preferences
	 * @return the path of the data object that is got from preferences
	 */
	public String getDataPath() 
	{
		return preferences.get(DATA_PATH_PREF, "");
	}
	
	/**
	 * Puts the path of the data object to preferences
	 * @param path - a new path
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public void putDataPath(String path) throws NullPointerException
	{
		if (path == null) throw new NullPointerException("null value in Data.putDataPath()");
		preferences.put(DATA_PATH_PREF, path);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Exercise</code> panel must be shown.
	 * @return <code>true</code> if the <code>Exercise</code> panel must be shown.
	 */
	public boolean getExerciseState()
	{
		return preferences.getBoolean(SHOW_EXERCISE, false);
	}
	
	/**
	 * Sets must the <code>Exercise</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putExerciseState(boolean b)
	{
		preferences.putBoolean(SHOW_EXERCISE, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Worklist</code> panel must be shown.
	 * @return <code>true</code> if the <code>Worklist</code> panel must be shown.
	 */
	public boolean getWorklistState()
	{
		return preferences.getBoolean(SHOW_WORKLIST, false);
	}
	
	/**
	 * Sets must the <code>Worklist</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putWorklistState(boolean b)
	{
		preferences.putBoolean(SHOW_WORKLIST, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Filler</code> panel must be shown.
	 * @return <code>true</code> if the <code>Filler</code> panel must be shown.
	 */
	public boolean getFillerState()
	{
		return preferences.getBoolean(SHOW_FILLER, false);
	}
	
	/**
	 * Sets must the <code>Filler</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putFillerState(boolean b)
	{
		preferences.putBoolean(SHOW_FILLER, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Dictionary</code> panel must be shown.
	 * @return <code>true</code> if the <code>Dictionary</code> panel must be shown.
	 */
	public boolean getDictionaryState()
	{
		return preferences.getBoolean(SHOW_DICTIONARY, false);
	}
	
	/**
	 * Sets must the <code>Dictionary</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putDictionaryState(boolean b)
	{
		preferences.putBoolean(SHOW_DICTIONARY, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Tags</code> panel must be shown.
	 * @return <code>true</code> if the <code>Tags</code> panel must be shown.
	 */
	public boolean getTagsState()
	{
		return preferences.getBoolean(SHOW_TAGS, false);
	}
	
	/**
	 * Sets must the <code>Tags</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putTagsState(boolean b)
	{
		preferences.putBoolean(SHOW_TAGS, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Search</code> panel must be shown.
	 * @return <code>true</code> if the <code>Search</code> panel must be shown.
	 */
	public boolean getSearchState()
	{
		return preferences.getBoolean(SHOW_SEARCH, false);
	}
	
	/**
	 * Sets must the <code>Search</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putSearchState(boolean b)
	{
		preferences.putBoolean(SHOW_SEARCH, b);
	}
	
	/**
	 * Returns <code>true</code> if the <code>Recorder</code> panel must be shown.
	 * @return <code>true</code> if the <code>Recorder</code> panel must be shown.
	 */
	public boolean getRecorderState()
	{
		return preferences.getBoolean(SHOW_RECORDER, false);
	}
	
	/**
	 * Sets must the <code>Recorder</code> panel be shown or not.
	 * @param b - a new value.
	 */
	public void putRecorderState(boolean b)
	{
		preferences.putBoolean(SHOW_RECORDER, b);
	}
	
	/**
	 * Constructs and returns the path of the task list.
	 * @param dataPath - data path.
	 * @param newExt - a extension of a task list file.
	 * @return the path of the task list.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public String getTaskPath(String dataPath, String newExt) throws NullPointerException
	{
		if (dataPath == null || newExt == null) 
			throw new NullPointerException("null value in Data.getTaskPath()");
			
		if (dataPath.isEmpty()) return "";
			
		int extPoint = dataPath.lastIndexOf(".");
		if (extPoint == -1 || extPoint == 0) return dataPath + "." + newExt;
		if (extPoint == dataPath.length()-1) return dataPath + newExt;
			
		String oldExt = dataPath.substring(extPoint+1);
		return dataPath.replace("." + oldExt, "." + newExt);
	}
	
	/**
	 * Constructs and returns the path of the folder with sounds
	 * @param path - data path.
	 * @param toAdd - a <code>String</code> to add to get a new path
	 * @return the path of the folder with sounds
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public String getSoundFolder(File path, String toAdd) 
	{
		if (path == null || toAdd == null) 
				throw new NullPointerException("null value in Data.getSoundFolder()");
			
		String dataPath = path.getAbsolutePath();
			
		int extPoint = dataPath.lastIndexOf(".");
		String result = "";
			
		if (extPoint == -1 || extPoint == 0 || extPoint == dataPath.length()-1) 
		{
			result = dataPath + toAdd;
		}
		else
		{
			result = dataPath.substring(0, extPoint) + toAdd;
		}
			
		File resultDir = new File(result);
		if (!resultDir.exists()) resultDir.mkdirs();
			
		return result;
	}
}
