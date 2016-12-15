//
//	This file is part of LangH.
//
//	LangH is a program that allows to keep foreign phrases and test yourself.
//	Copyright © 2015 Aleksandr Pinin. e-mail: <alex.pinin@gmail.com>
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

package com.pinin.alex.gui;

import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

public class MenuFile extends AbstractControlledPanel
{
//
// Variables
//	
	// elements of this menu

	JMenu menu;
	private JMenuItem newFile;
	private JMenuItem openFile;
	private JMenuItem saveFile;
	private JMenuItem saveFileAs;
	private JMenuItem exitProg;
	
	// tool bar buttons
	
	private JButton saveFileBut;
	
	// other
	
	/** Default serial version ID. */
	private final static long serialVersionUID = 1L;
	
	// common mains
	
	private final Logger LOGGER = LangH.getLogger();
	private final Texts TXT = LangH.getTexts();

//
// Constructors
//
	
	/**
	 * Constructor.
	 * @param - dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public MenuFile(DictionaryTable dic, DictionaryTable worklist)
	{
		// menu items
		
		newFile = getMenuItem(TXT.BT_NEW_FILE, TXT.HK_NEW_FILE, event -> newFile(dic, worklist));
		newFile.setIcon(LangH.getResource(Texts.PH_ICON_NEW));
		
		openFile = getMenuItem(TXT.BT_OPEN_FILE, TXT.HK_OPEN_FILE, event -> openFile(dic, worklist));
		openFile.setIcon(LangH.getResource(Texts.PH_ICON_OPEN));
		
		saveFile = getMenuItem(TXT.BT_SAVE_FILE, TXT.HK_SAVE_FILE, event -> saveFile(dic, worklist));
		saveFile.setIcon(LangH.getResource(Texts.PH_ICON_SAVE));
		
		saveFileAs = getMenuItem(TXT.BT_SAVE_AS_FILE, TXT.HK_SAVE_AS_FILE, event -> saveFileAs(dic, worklist));
		exitProg   = getMenuItem(TXT.BT_EXIT_FILE,    TXT.HK_EXIT_FILE,    event -> exitProg());
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.MU_FILE);
		menu.setMnemonic(TXT.MN_FILE);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(newFile);
		menu.add(openFile);
		menu.addSeparator();
		menu.add(saveFile);
		menu.add(saveFileAs);
		menu.addSeparator();
		menu.add(exitProg);
		
		// tool bar buttons
		
		saveFileBut = getButton(LangH.getResource(Texts.PH_ICON_SAVE), 
				TXT.TIP_SAVE_FILE, event -> saveFile(dic, worklist));
	}
	
//
// Methods
//
	
	/**
	 * Makes a new document.
	 * @param dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public void newFile(DictionaryTable dic, DictionaryTable worklist)
	{
		GUI.sendMessage("");
		int ok = GUI.showConfirmDialog(TXT.MG_NEW_QUESTION, TXT.TL_CONF_NEW);
		if (ok == JOptionPane.OK_OPTION)
		{
			dic.loadData(null);
			worklist.loadData(null);
			LangH.getData().putDataPath("");
		}
	}
	
	/**
	 * Opens a new file.
	 * @param dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public void openFile(DictionaryTable dic, DictionaryTable worklist)
	{
		try
		{
			GUI.sendMessage("");
			File path = dic.getPath();
			
			String newPath = GUI.showOpenFileDialog(TXT.MG_FILE_DESCRIPT, path, TXT.PH_EXT_DB);
			GUI.loadData(newPath);
		}
		catch (RuntimeException e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Saves current data.
	 * @param dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public void saveFile(DictionaryTable dic, DictionaryTable worklist)
	{
		try
		{
			GUI.sendMessage("");
			File path = dic.getPath();
			
			if (path.getPath().isEmpty())
			{
				saveFileAs(dic, worklist);
				return;
			}
			dic.codeFile(path);
			GUI.sendMessage(TXT.MG_SAVED_REPORT + " " + path);
			
			String pathTask = LangH.getData().getTaskPath(path.toString(), TXT.PH_EXT_TSK);
			worklist.codeFile(new File(pathTask));
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public void saveFileAs(DictionaryTable dic, DictionaryTable worklist)
	{
		try
		{
			GUI.sendMessage("");
			
			String newPath = GUI.showOpenFileDialog(TXT.MG_FILE_DESCRIPT, new File(""), TXT.PH_EXT_DB);
			if (newPath.isEmpty()) return;
			
			String extension = "." + TXT.PH_EXT_DB;
			if (!newPath.endsWith(extension)) newPath += extension;
			
			File newPathFile = new File(newPath);
			if (newPathFile.exists())
			{
				String message = newPathFile + " " + TXT.MG_FILE_EXISTS;
				int ok = GUI.showConfirmDialog(message, TXT.TL_CONF_REPLACE);
				if (ok != JOptionPane.OK_OPTION) return;
			}
			dic.codeFile(new File(newPath));
			GUI.sendMessage(TXT.MG_SAVED_REPORT + " " + newPath);
			
			Data data = LangH.getData();
			data.putDataPath(newPath);
			
			String newTaskData = data.getTaskPath(newPath, TXT.PH_EXT_TSK);
			worklist.codeFile(new File(newTaskData));
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Closes this program.
	 */
	public void exitProg()
	{
		GUI.exit();
	}
	
	/**
	 * Returns the menu to operate this panel.
	 * @return the menu to operate this panel.
	 */
	public JMenu getMenu()
	{
		return menu;
	}
	
	/**
	 * Returns tool buttons of this panel.
	 * @return tool buttons of this panel.
	 */
	public JButton[] getToolBarButtons()
	{
		return new JButton[] {saveFileBut};
	}
	
	@Override
	public void openClose() {}
	
	/**
	 * Returns a new <code>JMenuItem</code> object
	 * @param text - a text for this object
	 * @param key_Komb - a key combination for this object
	 * @param action - an action for this object
	 * @return a new <code>JMenuItem</code> object
	 */
	private JMenuItem getMenuItem(String text, String key_Komb, ActionListener action) 
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(LangH.getFonts().getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(key_Komb));
		item.addActionListener(action);
		return item;
	}
	
	/**
	 * Returns a new <code>JButton</code> object with specified parameters
	 * @param icon - an icon for this object
	 * @param tip - a tip text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(ImageIcon icon, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}
	
} // end MenuFile
