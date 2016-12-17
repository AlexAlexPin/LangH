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

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Contains all components of GUI. All components are available for each other.
 * All components can exchange data to each other.
 * This class is a connector between components inside this package.
 */
public class GUI extends JFrame 
{
	// elements of this frame
	private static AbstractDictionaryTable dictionary;
	private static AbstractFilteredTable<Integer> worklist;
	private static AbstractControlledPanel filler;
	private static AbstractControlledPanel exercise;
	private static AbstractControlledPanel recorder;
	private static JTextField message;

	/**
	 * A panel that provides an opportunity to hide all main panels
	 * and lets other elements to stay on their positions.
	 * When all main panels are not visible this panel is turned on
	 * it takes a place on the frame and prevent moving of other elements.
	 */
	private static JPanel FIX;	
	
	/** This frame. */
	private static JFrame thisFrame;
	
	// other
	
	/** Default serial version ID. */
	private final static long serialVersionUID = 1L;
	
	// common mains
	private static CommonDataFactory dataFactory; // TODO possibly not global
	private static Logger logger;
	private static TextsRepo textsRepo;

	/**
	 * Constructor
	 * @param dataFactory - a common data factory.
	 */
	public GUI(CommonDataFactory dataFactory)
	{
        GUI.dataFactory = dataFactory;
        logger = dataFactory.getLogger();
        textsRepo = dataFactory.getTextsRepo();

		thisFrame = this;
			
		PrefFacade prefFacade = dataFactory.getPrefFacade();
		
		this.setSize(prefFacade.getMainFrameSize());
		this.setExtendedState(prefFacade.getExtendedState());
		this.setLocationRelativeTo(null);
		this.setIconImage(dataFactory.getIconFromResource(TextsRepo.PH_ICON_TITLE).getImage());
		this.setTitle(textsRepo.TL_TITLE);
		this.setLayout(new GridBagLayout());
			
		// catch the extended state changing
			
		this.addWindowStateListener(event -> 
		{ 
			if (getExtendedState() == 0) 
			{
				setSize(prefFacade.getDefaultMainFrameSize());
				setLocationRelativeTo(null); 
			}
		});
			
		// set the action on close
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				exit();
			}
		});
			
		// panels
		
		dictionary = new Dictionary(dataFactory);
		worklist   = new Worklist(dictionary, dataFactory);
		filler     = new Filler(dictionary, dataFactory);
		exercise   = new Exercise(worklist, dataFactory);
		recorder   = new Recorder(dictionary, dataFactory);
			
		AbstractControlledPanel menuFile = new MenuFile(dictionary, worklist, dataFactory);
		AbstractControlledPanel menuEdit = new MenuEdit(dataFactory);
		AbstractControlledPanel menuHelp = new MenuHelp(dataFactory);
			
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile.getMenu());
		menuBar.add(menuEdit.getMenu());
		menuBar.add(exercise.getMenu());
		menuBar.add(worklist.getMenu());
		menuBar.add(filler.getMenu());
		menuBar.add(dictionary.getMenu());
		menuBar.add(recorder.getMenu());			
		menuBar.add(menuHelp.getMenu());	
			
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		addSet(toolBar, menuFile.getToolBarButtons());
		addSet(toolBar, exercise.getToolBarButtons());
		addSet(toolBar, worklist.getToolBarButtons());
		addSet(toolBar, filler.getToolBarButtons());
		addSet(toolBar, dictionary.getToolBarButtons());
		addSet(toolBar, recorder.getToolBarButtons());
		addSet(toolBar, menuHelp.getToolBarButtons());
			
		FIX = new JPanel();
		
		message = new JTextField("");
		message.setEditable(false);
		message.setFont(dataFactory.getFontsRepo().getFontPlate());
		message.setHorizontalAlignment(JTextField.LEFT);
		message.setBorder(BorderFactory.createEtchedBorder());
		
		// add components
			
		this.setJMenuBar(menuBar);
		this.add(toolBar,    new GBC(0, 0, 1, 1).setWeight(100, 0).setAnchor(GBC.WEST));
		this.add(exercise,   new GBC(0, 1, 1, 1).setWeight(100, 100).setFill(GBC.BOTH));
		this.add(worklist,   new GBC(0, 2, 1, 1).setWeight(100, 100).setFill(GBC.BOTH));
		this.add(filler,     new GBC(0, 3, 1, 1).setWeight(100, 100).setFill(GBC.BOTH));
		this.add(dictionary, new GBC(0, 4, 1, 1).setWeight(100, 100).setFill(GBC.BOTH));
		this.add(FIX,        new GBC(0, 5, 1, 1).setWeight(100, 100));
		this.add(recorder,   new GBC(0, 6, 1, 1).setWeight(100, 0));
		this.add(message,    new GBC(0, 7, 1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
		
		// open or close panels
		
		dictionary.openClose();
		worklist.openClose();
		filler.openClose();
		exercise.openClose();
		recorder.openClose();
		
		if (prefFacade.getDictionaryPanelShowState()) dictionary.openClose();
		if (prefFacade.getWorkListPanelShowState())   worklist.openClose();
		if (prefFacade.getFillerPanelShowState())     filler.openClose();
		if (prefFacade.getExercisePanelShowState())   exercise.openClose();
		if (prefFacade.getRecorderPanelShowState())   recorder.openClose();
		
		// loads the database
		
		loadData(prefFacade.getDataPath());
	}

	/**
	 * Returns the current worklist.
	 * @return the current worklist.
	 */
	static AbstractFilteredTable<Integer> getWorklist()
	{
		return worklist;
	}
	
	/**
	 * Informs this panel about changing a visible status of one of the main panels
	 */
	static void infoPanelVisibility() 
	{
		boolean isVisiblePanel = exercise.isVisible() || filler.isVisible() 
				|| dictionary.isVisible() || worklist.isVisible();
		
		if (!isVisiblePanel)
		{
			FIX.setVisible(true);
		}
		else 
		{
			FIX.setVisible(false);
		}
	}
	
	/**
	 * Shows a message.
	 * @param text - a new message.
	 */
	static void sendMessage(String text)
	{
		message.setText(text);
	}
	
	/**
	 * Closes this program.
	 */
	static void exit()
	{
		GUI.sendMessage("");
		int ok = GUI.showConfirmDialog(textsRepo.MG_EXIT_QUESTION, textsRepo.TL_CONF_EXIT);
		if (ok == JOptionPane.OK_OPTION)
		{
			PrefFacade prefFacade = dataFactory.getPrefFacade();
			prefFacade.putMainFrameSize(thisFrame.getSize());
			prefFacade.putExtendedState(thisFrame.getExtendedState());
			prefFacade.putDictionaryPanelShowState(dictionary.isVisible());
			prefFacade.putWorkListPanelShowState(worklist.isVisible());
			prefFacade.putFillerPanelShowState(filler.isVisible());
			prefFacade.putExercisePanelShowState(exercise.isVisible());
			prefFacade.putRecorderPanelShowState(recorder.isVisible());
			logger.log(Level.CONFIG, "Program has been finished");
			System.exit(0);
		}
	}
	
	/**
	 * Loads the database.
	 * @param dataPath - a path of the dictionary.
	 */
	static void loadData(String dataPath)
	{
		if (dataPath.isEmpty()) return;
		
		File dataPathFile = new File(dataPath);
		if (!dataPathFile.exists())
		{
			sendMessage(textsRepo.MG_NO_DB);
			return;
		}
		
		PrefFacade prefFacade = dataFactory.getPrefFacade();
		String taskPath = prefFacade.getTaskPath(dataPath, textsRepo.PH_EXT_TSK);
		dictionary.loadData(dataPathFile);
		worklist.loadData(new File(taskPath));
		prefFacade.putDataPath(dataPath);
		sendMessage(textsRepo.MG_DB_LOADED + " " + dataPath);
	}
	
	/**
	 * Shows the confirm dialog.
	 * @param message - a message of the dialog.
	 * @param title - a title of the dialog.
	 * @return an answer of the dialog.
	 */
	static int showConfirmDialog(String message, String title)
	{
		return JOptionPane.showConfirmDialog(thisFrame, message, title, 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	/**
	 * Shows the error dialog.
	 * @param message - a message of the dialog.
	 * @param title - a title of the dialog.
	 */
	static void showErrorDialog(String message, String title)
	{
		JOptionPane.showMessageDialog(thisFrame, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Shows the information dialog.
	 * @param message - a message of the dialog.
	 * @param title - a title of the dialog.
	 */
	static void showInformDialog(String message, String title)
	{
		JOptionPane.showMessageDialog(thisFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Shows the file opening dialog.
	 * @param description - a description of files.
	 * @param path - a path to open the dialog.
	 * @param extensions - a set of extensions on files.
	 * @return a path of the selected file..
	 */
	static String showOpenFileDialog(String description, File path, String... extensions)
	{
		return Common.showOpenDialog(thisFrame, description, path, extensions);
	}
	
	/**
	 * Shows the file saving dialog.
	 * @param description - a description of files.
	 * @param path - a path to open the dialog.
	 * @param extensions - a set of extensions on files.
	 * @return a path of the saved file.
	 */
	static String showSaveFileDialog(String description, File path, String... extensions)
	{
		return Common.showSaveDialog(thisFrame, description, path, extensions);
	}
	
	/**
	 * Shows the settings dialog.
	 * @param title - a title for the dialog.
	 */
	static void showSettingDialog(String title)
	{
		JDialog d = new DialogSetting(thisFrame, title, dataFactory);
		d.setVisible(true);
	}
	
	/**
	 * Shows the help dialog.
	 * @param title - a title for the dialog.
	 */
	static void showHelpDialog(String title)
	{
		JDialog d = new DialogHelp (thisFrame, title, dataFactory);
		d.setVisible(true);
	}
	
	
	/**
	 * Shows the about dialog.
	 * @param title - a title for the dialog.
	 */
	static void showAboutDialog(String title)
	{
		JDialog d = new DialogAbout (thisFrame, title, dataFactory);
		d.setVisible(true);
	}
	
	/**
	 * Returns a focus owner of this frame.
	 * @return a focus owner of this frame.
	 */
	static Component getFocusOwnerSt()
	{
		return thisFrame.getFocusOwner();
	}
	
	/**
	 * Adds the specified <code>JButton...</code> to the specified <code>JToolBar</code>.
	 * @param toolBar - <code>JToolBar</code> to get elements.
	 * @param buttons - <code>JButton...</code> to be added.
	 */
	private void addSet(JToolBar toolBar, JButton... buttons)
	{
		for (JButton each : buttons)
		{
			toolBar.add(each);
		}
	}
}
