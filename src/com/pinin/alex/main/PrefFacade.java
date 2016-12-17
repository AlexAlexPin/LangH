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

/**
 * Loads and operates the main data.
 */
public class PrefFacade
{
	// data
	private Dimension screenSize;
	
	// default ratios between the screen size and the main frame
	private final static int DEFAULT_WIDTH_COEFFICIENT = 2;
	private final static int DEFAULT_HEIGHT_COEFFICIENT = 2;
	
	// preferences links
	private final static String MAIN_FRAME_WIDTH_PREF     = "main_frame_width";
	private final static String MAIN_FRAME_HEIGHT_PREF    = "main_frame_height";
	private final static String MAIN_FRAME_EXT_STATE_PREF = "main_frame_ext_state";
	private final static String FONT_SIZE_PREF   		  = "font_size_height";
	private final static String LANGUAGE_PREF   		  = "lang";
	
	private final static String DATA_PATH_PREF   		  = "data_path";
	
	private final static String SHOW_EXERCISE             = "show_exercise";
	private final static String SHOW_WORK_LIST            = "show_work_list";
	private final static String SHOW_FILLER               = "show_filler";
	private final static String SHOW_DICTIONARY           = "show_dictionary";
	private final static String SHOW_RECORDER             = "show_recorder";

	// common mains
	private Preferences preferences;

	public PrefFacade(Preferences preferences) {
        this.preferences = preferences;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	public Dimension getDefaultMainFrameSize() {
		int frameWidth  = screenSize.width/ DEFAULT_WIDTH_COEFFICIENT;
		int frameHeight = screenSize.height/ DEFAULT_HEIGHT_COEFFICIENT;
		return new Dimension(frameWidth, frameHeight);
	}

    public Dimension getMainFrameSize() {
        int frameWidth  = preferences.getInt(MAIN_FRAME_WIDTH_PREF,  0);
        int frameHeight = preferences.getInt(MAIN_FRAME_HEIGHT_PREF, 0);
        if (frameWidth  == 0 || frameHeight == 0) return getDefaultMainFrameSize();
        return new Dimension(frameWidth, frameHeight);
    }

	public void putMainFrameSize(Dimension size) throws NullPointerException {
		if (size == null) throw new NullPointerException("null value in PrefFacade.putMainFrameSize()");
		preferences.putInt(MAIN_FRAME_HEIGHT_PREF, size.height);
		preferences.putInt(MAIN_FRAME_WIDTH_PREF,  size.width);
	}

	public int getExtendedState() {
		return preferences.getInt(MAIN_FRAME_EXT_STATE_PREF, 0);
	}

	public void putExtendedState(int extendedState) throws IllegalArgumentException {
		if (extendedState != Frame.NORMAL && extendedState != Frame.ICONIFIED 
				&& extendedState != Frame.MAXIMIZED_HORIZ && extendedState != Frame.MAXIMIZED_VERT 
				&& extendedState != Frame.MAXIMIZED_BOTH)
			throw new IllegalArgumentException("Illegal extended state");
		preferences.putInt(MAIN_FRAME_EXT_STATE_PREF, extendedState);
	}

	public Dimension getScreenSize() {
		return screenSize;
	}

	public int getFontSize() {
		return preferences.getInt(FONT_SIZE_PREF, FontsRepo.FONT_NORMAL);
	}

	public void putFontSize(int size) throws IllegalArgumentException {
		if (size < 0) throw new IllegalArgumentException("Font size is less then 0");
		preferences.putInt(FONT_SIZE_PREF, size);
	}

	public String getLanguage() {
		return preferences.get(LANGUAGE_PREF, TextsRepo.PH_TEXT);
	}

	public void putLanguage(String lang) throws NullPointerException {
		if (lang == null) throw new NullPointerException("Language value is null");
		preferences.put(LANGUAGE_PREF, lang);
	}

	public String getDataPath() {
		return preferences.get(DATA_PATH_PREF, "");
	}

	public void putDataPath(String path) throws NullPointerException {
		if (path == null) throw new NullPointerException("PrefFacade path is null");
		preferences.put(DATA_PATH_PREF, path);
	}

	public boolean getExercisePanelShowState() {
		return preferences.getBoolean(SHOW_EXERCISE, false);
	}

	public void putExercisePanelShowState(boolean b) {
		preferences.putBoolean(SHOW_EXERCISE, b);
	}

	public boolean getWorkListPanelShowState() {
		return preferences.getBoolean(SHOW_WORK_LIST, false);
	}

	public void putWorkListPanelShowState(boolean b) {
		preferences.putBoolean(SHOW_WORK_LIST, b);
	}

	public boolean getFillerPanelShowState() {
		return preferences.getBoolean(SHOW_FILLER, false);
	}

	public void putFillerPanelShowState(boolean b) {
        preferences.putBoolean(SHOW_FILLER, b);
	}

	public boolean getDictionaryPanelShowState() {
		return preferences.getBoolean(SHOW_DICTIONARY, false);
	}

	public void putDictionaryPanelShowState(boolean b) {
		preferences.putBoolean(SHOW_DICTIONARY, b);
	}

	public boolean getRecorderPanelShowState() {
		return preferences.getBoolean(SHOW_RECORDER, false);
	}

	public void putRecorderPanelShowState(boolean b) {
		preferences.putBoolean(SHOW_RECORDER, b);
	}
	
	/**
	 * Constructs and returns the path of the task list.
	 * @param dataPath - data path.
	 * @param newExt - a extension of a task list file.
	 * @return the path of the task list.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public String getTaskPath(String dataPath, String newExt) throws NullPointerException {
		if (dataPath == null || newExt == null) 
			throw new NullPointerException("PrefFacade path or extension is null");
			
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
	public String getSoundFolderPath(File path, String toAdd) throws NullPointerException {
		if (path == null || toAdd == null)
            throw new NullPointerException("File path or additional part is null");
			
		String dataPath = path.getAbsolutePath();
			
		int extPoint = dataPath.lastIndexOf(".");
		String result = "";
			
		if (extPoint == -1 || extPoint == 0 || extPoint == dataPath.length()-1) {
			result = dataPath + toAdd;
		}
		else {
			result = dataPath.substring(0, extPoint) + toAdd;
		}
			
		File resultDir = new File(result);
		if (!resultDir.exists()) resultDir.mkdirs();
			
		return result;
	}
}
