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

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. A panel that allow to execute exercises.
 */
public class Exercise extends AbstractControlledPanel 
{
//
// Variables
//
	
	// data objects
	
	/** An object that contains all exercises. */
	private Task tasks;
	
	/** A question to get an answer. */
	private String task;
	
	/** A number of phrases during one exercise. */
	private int numOfExer;
	
	// a current exercise option
	
	private int option;
	private final int BY_PHRASE       = 1;
	private final int BY_TRANSL       = 2;
	private final int BY_PHRASE_SOUND = 3;
	private final int BY_TRANSL_SOUND = 4;
	
	// elements of this panel
	
	private JTextPane taskField;
	private JTextPane answField;
	private JComboBox<String> exercises;
	
	// elements of this menu
	
	private JMenu menu;
	private JMenuItem runMit;
	private JMenuItem enterMit;
	private JMenuItem playMit;
	private JMenuItem helpMit;
	
	// tool bar buttons
	
	private JButton openCloseBut;
	
	// other
	
	/** Default serial version ID */
	private final static long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	private final Logger LOGGER = LangH.getLogger();

//
// Constructors
//
	
	/**
	 * Constructor.
	 * @param worklist - an object to exchange data.
	 */
	public Exercise(DictionaryTable worklist) 
	{
		// buttons
	
		JButton playBut  = getButton(Texts.PH_ICON_SOUND, TXT.TIP_EXER_PLAY,  event -> exerPlay(worklist));
		JButton enterBut = getButton(Texts.PH_ICON_ENTER, TXT.TIP_EXER_ENTER, event -> exerEnter(worklist));
		JButton helpBut  = getButton(Texts.PH_ICON_HELP,  TXT.TIP_EXER_HELP,  event -> exerHelp());
		JButton runBut   = getButton(Texts.PH_ICON_RUN,   TXT.TIP_EXER_RUN,   event -> exerRun(worklist));
		
		exercises = getComboBox(TXT.BT_BY_PHRASE_EXER_PL, TXT.BT_BY_TRANSL_EXER_PL, 
					TXT.BT_BY_PH_SND_EXER_PL, TXT.BT_BY_TR_SND_EXER_PL);
		
		exercises.setFont(LangH.getFonts().getFontPlate());
		exercises.setToolTipText(TXT.TIP_EXER_CHOOSE);
			
		JToolBar buttons = getToolBar(exercises, new JLabel("  "), runBut, enterBut, playBut, helpBut);
			
		// workspace
			
		taskField = getTextPane(false);
		taskField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_TASK));
		taskField.setBackground(Color.WHITE);
	
		answField = getTextPane(true);
		answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
		answField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
		answField.addCaretListener(new CaretListener() 
		{
			public void caretUpdate(CaretEvent arg0) 
			{
				GUI.sendMessage("");
				answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
			}
		});
			
		JPanel workspace = getPanel(new GridLayout(1,2), new JScrollPane(taskField), new JScrollPane(answField));
			
		// add elements
		
		this.setBorder(LangH.getBorders().getPanelBorder());
		this.setLayout(new BorderLayout());
			
		final JPanel headPanel = new JPanel(new BorderLayout());
		headPanel.add(buttons, BorderLayout.WEST);
			
		this.add(headPanel, BorderLayout.NORTH);
		this.add(workspace, BorderLayout.CENTER);
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(TXT.BT_SHOW_HIDE_PL, TXT.HK_EXER_PL, event -> openClose());
		openCloseMit.setIcon(LangH.getResource(Texts.PH_ICON_EXER));
		
		runMit   = getMenuItem(TXT.BT_RUN_EXER_PL,   TXT.HK_RUN_EXER_PL,   event -> exerRun(worklist));
		enterMit = getMenuItem(TXT.BT_ENTER_EXER_PL, TXT.HK_ENTER_EXER_PL, event -> exerEnter(worklist));
		playMit  = getMenuItem(TXT.BT_PLAY_EXER_PL,  TXT.HK_PLAY_EXER_PL,  event -> exerPlay(worklist));
		helpMit  = getMenuItem(TXT.BT_HELP_EXER_PL,  TXT.HK_HELP_EXER_PL,  event -> exerHelp());
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.MU_EXER);
		menu.setMnemonic(TXT.MN_EXER_PL);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(runMit);
		menu.add(enterMit);
		menu.add(playMit);
		menu.add(helpMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(LangH.getResource(Texts.PH_ICON_EXER), TXT.TIP_EXER_PL, event -> openClose());
	}
	
	/**
	 * Runs a chosen exercise.
	 * @param dic - an object to exchange data.
	 */
	public void exerRun(DictionaryTable dic) 
	{
		if (tasks != null)
		{
			int ok = GUI.showConfirmDialog(TXT.MG_NEW_TASK_QUESTON, TXT.TL_CONF_TASK);
			if (ok == JOptionPane.OK_OPTION) 
			{
				chooseExer(dic);
			}
		}
		else 
		{
			chooseExer(dic);
		}
	}
	
	/**
	 * Plays the sound of the current task if it exists.
	 */
	public void exerPlay(DictionaryTable dic) 
	{
		if (tasks == null) return;
		
		Sound sound = new Sound();
		int id = tasks.getPhrase().getId();
		
		boolean b = dic.getSound(sound, id);
		if (b) sound.play();
		
		answField.requestFocus();
	}
	
	/**
	 * Clears the answer field.
	 */
	public void exerClear() 
	{
		answField.setText("");
	}
	
	/**
	 * Runs the tasks help method.
	 */
	public void exerHelp() 
	{
		try 
		{
			if (tasks == null) return;
			String prompt = tasks.help();
			answField.setText(prompt);
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Enters an answer and checks a result.
	 * @param dic - an object to exchange data.
	 */
	public void exerEnter(DictionaryTable dic) 
	{
		if (tasks == null) return;	
		
		int check = tasks.enter(answField.getText());
			
		// do actions depending on result
			
		switch (check) 
		{
		case Task.WRONG_ANSW:	// incorrect answer
			answField.setBorder(LangH.getBorders().getWrongBorder());
			answField.requestFocus();
			GUI.sendMessage(TXT.MG_WRONG_ANSW + " (" + TXT.MG_ATTEMPT + tasks.getAttempt() + ")");
			break;	
			
		case Task.RIGHT_ANSW:	// correct answer and there are more questions
		
			switch (option) // do the next task
			{
			case BY_PHRASE:
				playSound(dic);
				exerByPhrase();
				break;
			case BY_TRANSL:
				playSound(dic);
				exerByTransl();
				break;
			case BY_PHRASE_SOUND:
				exerByPhraseSound(dic);
				break;
			case BY_TRANSL_SOUND:
				exerByTranslSound(dic);
				break;
			}
			answField.setBorder(LangH.getBorders().getCorrectBorder());
			GUI.sendMessage(TXT.MG_CORRECT_ANSW + " (" + TXT.MG_REST + " " + tasks.getRestOfPhrases() + ")");
			break;
				
		case Task.RIGHT_ANSW_FIN:	// correct answer and the last question	
			
			if (option != BY_PHRASE_SOUND && option != BY_TRANSL_SOUND) playSound(dic);
	
			taskField.setText(TXT.MG_DONE + " (" + TXT.MG_PHRASES + " " + numOfExer + ", "
					+ TXT.MG_MISTAKES + " " + tasks.getNumOfMistakes() + ")");
				
			answField.setText("");
			answField.setBorder(LangH.getBorders().getCorrectBorder());
			answField.requestFocus();

			tasks = null;
			break;
		}
	}
	
	/**
	 * Chooses a type of exercise to do.
	 * @param dic - an object to exchange data.
	 */
	private void chooseExer(DictionaryTable dic) 
	{
		String exer = exercises.getItemAt(exercises.getSelectedIndex());
			
		if (exer.equals(TXT.BT_BY_PHRASE_EXER_PL)) 
		{
			loadData(dic);
			exerByPhrase();
		}
		if (exer.equals(TXT.BT_BY_TRANSL_EXER_PL)) 
		{
			loadData(dic);
			exerByTransl();
		}
		if (exer.equals(TXT.BT_BY_PH_SND_EXER_PL)) 
		{
			loadData(dic);
			exerByPhraseSound(dic);
		}
		if (exer.equals(TXT.BT_BY_TR_SND_EXER_PL)) 
		{
			loadData(dic);
			exerByTranslSound(dic);
		}
	}
	
	/**
	 * Loads the current database.
	 * @param dic - an object to exchange data.
	 */
	private void loadData(DictionaryTable dic) 
	{
		try 
		{
			ArrayList<Phrase> data = new ArrayList<Phrase>();
			data.addAll(dic.getAll());
			tasks = new Task(data);
			numOfExer = data.size();
			option = 0;
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Runs the "By phrase" exercise
	 */
	private void exerByPhrase() 
	{
		try 
		{
			option = BY_PHRASE;
			
			task = tasks.doExercise(Task.OPTION_BY_PHRASE);
			answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
			taskField.setText(task);
			answField.setText("");
			answField.requestFocus();
			
			GUI.sendMessage(TXT.MG_BY_PHRASE_INFO);
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Runs the "By translation" exercise
	 */
	private void exerByTransl() 
	{
		try 
		{
			option = BY_TRANSL;
			
			task = tasks.doExercise(Task.OPTION_BY_TRANSL);
			answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
			taskField.setText(task);
			answField.setText("");
			answField.requestFocus();
			
			GUI.sendMessage(TXT.MG_BY_TRANSL_INFO);
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Runs the "By phrase" exercise using only sound without the text.
	 * @param dic - an object to exchange data.
	 */
	private void exerByPhraseSound(DictionaryTable dic) 
	{
		try 
		{
			option = BY_PHRASE_SOUND;
			
			task = tasks.doExercise(Task.OPTION_BY_PHRASE);
			if (task == "") return;
			
			if (!playSound(dic)) // play sound as a task
			{
				exerByPhraseSound(dic);
			}
			else 
			{
				answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
				taskField.setText(TXT.MG_SOUND_INFO);
				answField.setText("");
				answField.requestFocus();
				GUI.sendMessage(TXT.MG_BY_PH_SND_INFO);
			}
		}
		catch (RuntimeException e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Runs the "By translation" exercise using only sound without the text.
	 * @param dic - an object to exchange data.
	 */
	private void exerByTranslSound(DictionaryTable dic) 
	{
		try 
		{
			option = BY_TRANSL_SOUND;
			
			task = tasks.doExercise(Task.OPTION_BY_TRANSL);
			if (task == "") return;
			
			if (!playSound(dic)) // play sound as a task
			{	
				exerByPhraseSound(dic);
			}
			else 
			{
				answField.setBorder(LangH.getBorders().getInTextBorder(TXT.LB_HEADER_ASWER));
				taskField.setText(TXT.MG_SOUND_INFO);
				answField.setText("");
				answField.requestFocus();
				GUI.sendMessage(TXT.MG_BY_TR_SND_INFO);
			}
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Plays the sound of the current task if it exists.
	 * @param dic - an object to exchange data.
	 */
	private boolean playSound(DictionaryTable dic)
	{
		Sound sound = new Sound();
		int id = tasks.getPhrase().getId();
		boolean b = dic.getSound(sound, id);
		if (b) sound.play();
		return b;
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
		return new JButton[] {openCloseBut};
	}
	
	/**
	 * Opens and closes menu items of this menu.
	 */
	public void openClose() 
	{
		if (this.isVisible()) // close items
		{
			runMit.setEnabled(false);
			enterMit.setEnabled(false);
			playMit.setEnabled(false);
			helpMit.setEnabled(false);
			openCloseBut.setBackground(LangH.getColors().getBasicBackground());
				
			this.setVisible(false);
		}
		else // open items
		{
			runMit.setEnabled(true);
			enterMit.setEnabled(true);
			playMit.setEnabled(true);
			helpMit.setEnabled(true);
			openCloseBut.setBackground(LangH.getColors().getPushedButton());
				
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}
	
	
	/**
	 * Returns a new <code>JTextArea</code> object
	 * @param isEditable - is this object editable or not
	 * @return a new <code>JTextArea</code> object
	 */
	private JTextPane getTextPane(boolean isEditable) 
	{
		JTextPane textPane = new JTextPane();
		textPane.setFont(LangH.getFonts().getFontPlate());
		textPane.setEditable(isEditable);
			
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
			
		return textPane;
	}
	
	/**
	 * Returns a new <code>JPanel</code> object
	 * @param mgr - a layout manager for this object
	 * @param components - components for this object
	 * @return a new <code>JPanel</code> object
	 */
	private JPanel getPanel(LayoutManager mgr, JComponent... components) {
	
		JPanel panel = new JPanel();
		panel.setLayout(mgr);
		for (JComponent each : components)
		{
			panel.add(each);
		}
		return panel;
	}
	
	/**
	 * Returns a new <code>JButton</code> object with specified parameters
	 * @param labelPath - a path of label for this object
	 * @param tip - a tip text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(String labelPath, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(LangH.getResource(labelPath));
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
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
	
	/**
	 * Returns a new <code>JToolBar</code> object
	 * @param components - components for this object
	 * @return a new <code>JToolBar</code> object
	 */
	private JToolBar getToolBar(Component... components) 
	{
		JToolBar bar = new JToolBar();
		bar.setFloatable(false);	
		for (Component each : components)
		{
			bar.add(each);
		}
		return bar;
	}
	
	/**
	 * Returns a new <code>JComboBox</code> object
	 * @param components - components for this object
	 * @return a new <code>JComboBox</code> object
	 */
	private JComboBox<String> getComboBox(String... components) 
	{
		JComboBox<String> box = new JComboBox<String>();
		for (String each : components)
		{
			box.addItem(each);
		}
		return box;
	}
	
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
	
} // end Exercise
