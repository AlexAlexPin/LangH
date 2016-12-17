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

package com.pinin.alex.gui;

import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

class MenuFile extends AbstractControlledPanel
{
	// elements of this menu
	private JMenu menu;

	// tool bar buttons
	private JButton saveFileBut;
	
	// other
	private final static long serialVersionUID = 1L;
	
	// common mains
    private CommonDataFactory dataFactory; // TODO possible local
	private Logger logger;
	private Texts texts;
    private Fonts fonts;

	/**
	 * Constructor.
	 * @param dic - an object to exchange data.
	 * @param workList - an object to exchange data.
     * @param dataFactory - a common data factory
	 */
	MenuFile(DictionaryTable dic, DictionaryTable workList, CommonDataFactory dataFactory)
	{
        this.dataFactory = dataFactory;
		logger = dataFactory.getLogger();
		texts = dataFactory.getTexts();
        fonts = dataFactory.getFonts();

		// menu items

        JMenuItem newFile = getMenuItem(texts.BT_NEW_FILE, texts.HK_NEW_FILE, event -> newFile(dic, workList));
		newFile.setIcon(dataFactory.getResource(Texts.PH_ICON_NEW));

        JMenuItem openFile = getMenuItem(texts.BT_OPEN_FILE, texts.HK_OPEN_FILE, event -> openFile(dic));
		openFile.setIcon(dataFactory.getResource(Texts.PH_ICON_OPEN));

        JMenuItem saveFile = getMenuItem(texts.BT_SAVE_FILE, texts.HK_SAVE_FILE, event -> saveFile(dic, workList));
		saveFile.setIcon(dataFactory.getResource(Texts.PH_ICON_SAVE));

        JMenuItem saveFileAs = getMenuItem(texts.BT_SAVE_AS_FILE, texts.HK_SAVE_AS_FILE, event -> saveFileAs(dic, workList));
        JMenuItem exitProg = getMenuItem(texts.BT_EXIT_FILE, texts.HK_EXIT_FILE, event -> exitProgram());
		
		// menu
		
		menu = new JMenu();
		menu.setText(texts.MU_FILE);
		menu.setMnemonic(texts.MN_FILE);
		menu.setFont(dataFactory.getFonts().getFontPlate());
		
		menu.add(newFile);
		menu.add(openFile);
		menu.addSeparator();
		menu.add(saveFile);
		menu.add(saveFileAs);
		menu.addSeparator();
		menu.add(exitProg);
		
		// tool bar buttons
		
		saveFileBut = getButton(dataFactory.getResource(Texts.PH_ICON_SAVE),
				texts.TIP_SAVE_FILE, event -> saveFile(dic, workList));
	}

	/**
	 * Makes a new document.
	 * @param dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	private void newFile(DictionaryTable dic, DictionaryTable worklist)
	{
		GUI.sendMessage("");
		int ok = GUI.showConfirmDialog(texts.MG_NEW_QUESTION, texts.TL_CONF_NEW);
		if (ok == JOptionPane.OK_OPTION)
		{
			dic.loadData(null);
			worklist.loadData(null);
            dataFactory.getData().putDataPath("");
		}
	}
	
	/**
	 * Opens a new file.
	 * @param dic - an object to exchange data.
	 */
    private void openFile(DictionaryTable dic)
	{
		try
		{
			GUI.sendMessage("");
			File path = dic.getPath();
			
			String newPath = GUI.showOpenFileDialog(texts.MG_FILE_DESCRIPT, path, texts.PH_EXT_DB);
			GUI.loadData(newPath);
		}
		catch (RuntimeException e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Saves current data.
	 * @param dic - an object to exchange data.
	 * @param workList - an object to exchange data.
	 */
    private void saveFile(DictionaryTable dic, DictionaryTable workList)
	{
		try
		{
			GUI.sendMessage("");
			File path = dic.getPath();
			
			if (path.getPath().isEmpty())
			{
				saveFileAs(dic, workList);
				return;
			}
			dic.codeFile(path);
			GUI.sendMessage(texts.MG_SAVED_REPORT + " " + path);
			
			String pathTask = dataFactory.getData().getTaskPath(path.toString(), texts.PH_EXT_TSK);
			workList.codeFile(new File(pathTask));
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param dic - an object to exchange data.
	 * @param workList - an object to exchange data.
	 */
	private void saveFileAs(DictionaryTable dic, DictionaryTable workList)
	{
		try
		{
			GUI.sendMessage("");
			
			String newPath = GUI.showOpenFileDialog(texts.MG_FILE_DESCRIPT, new File(""), texts.PH_EXT_DB);
			if (newPath.isEmpty()) return;
			
			String extension = "." + texts.PH_EXT_DB;
			if (!newPath.endsWith(extension)) newPath += extension;
			
			File newPathFile = new File(newPath);
			if (newPathFile.exists())
			{
				String message = newPathFile + " " + texts.MG_FILE_EXISTS;
				int ok = GUI.showConfirmDialog(message, texts.TL_CONF_REPLACE);
				if (ok != JOptionPane.OK_OPTION) return;
			}
			dic.codeFile(new File(newPath));
			GUI.sendMessage(texts.MG_SAVED_REPORT + " " + newPath);
			
			Data data = dataFactory.getData();
			data.putDataPath(newPath);
			
			String newTaskData = data.getTaskPath(newPath, texts.PH_EXT_TSK);
			workList.codeFile(new File(newTaskData));
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Closes this program.
	 */
	private void exitProgram()
	{
		GUI.exit();
	}

	public JMenu getMenu()
	{
		return menu;
	}

	public JButton[] getToolBarButtons()
	{
		return new JButton[] {saveFileBut};
	}
	
	@Override
	public void openClose() {}

	private JMenuItem getMenuItem(String text, String keyCombination, ActionListener action)
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fonts.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(keyCombination));
		item.addActionListener(action);
		return item;
	}

	private JButton getButton(ImageIcon icon, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}
}
