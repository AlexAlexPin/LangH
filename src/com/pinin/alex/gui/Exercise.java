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
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.text.*;

import com.pinin.alex.data.*;

/**
 * Extends <code>JPanel</code>. A panel that allow to execute exercises.
 */
public class Exercise extends AbstractControlledPanel 
{
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
	
	private TextsRepo textsRepo;
	private Logger logger;
    private FontsRepo fontsRepo;
    private BordersRepo bordersRepo;
    private ColorsRepo colorsRepo;

	/**
	 * Constructor.
	 * @param workList - an object to exchange data.
	 * @param dataFactory - a common data factory.
	 */
	public Exercise(DictionaryTable workList, CommonDataFactory dataFactory)
	{
        textsRepo = dataFactory.getTextsRepo();
        logger = dataFactory.getLogger();
        fontsRepo = dataFactory.getFontsRepo();
        bordersRepo = dataFactory.getBordersRepo();
        colorsRepo = dataFactory.getColorsRepo();

		// buttons

		JButton playBut  = getButton(
		        dataFactory.getIconFromResource(TextsRepo.PH_ICON_SOUND), textsRepo.TIP_EXER_PLAY, event -> exerPlay(workList));
		JButton enterBut = getButton(
                dataFactory.getIconFromResource(TextsRepo.PH_ICON_ENTER), textsRepo.TIP_EXER_ENTER, event -> exerEnter(workList));
		JButton helpBut  = getButton(
                dataFactory.getIconFromResource(TextsRepo.PH_ICON_HELP),  textsRepo.TIP_EXER_HELP, event -> exerHelp());
		JButton runBut   = getButton(
                dataFactory.getIconFromResource(TextsRepo.PH_ICON_RUN),   textsRepo.TIP_EXER_RUN, event -> exerRun(workList));
		
		exercises = getComboBox(textsRepo.BT_BY_PHRASE_EXER_PL, textsRepo.BT_BY_TRANSL_EXER_PL,
					textsRepo.BT_BY_PH_SND_EXER_PL, textsRepo.BT_BY_TR_SND_EXER_PL);
		
		exercises.setFont(fontsRepo.getFontPlate());
		exercises.setToolTipText(textsRepo.TIP_EXER_CHOOSE);
			
		JToolBar buttons = getToolBar(exercises, new JLabel("  "), runBut, enterBut, playBut, helpBut);
			
		// workspace
			
		taskField = getTextPane(false);
		taskField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_TASK));
		taskField.setBackground(Color.WHITE);
	
		answField = getTextPane(true);
		answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
		answField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
		answField.addCaretListener(arg0 -> {
            GUI.sendMessage("");
            answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
        });
			
		JPanel workspace = getPanel(new GridLayout(1,2), new JScrollPane(taskField), new JScrollPane(answField));
			
		// add elements
		
		this.setBorder(bordersRepo.getPanelBorder());
		this.setLayout(new BorderLayout());
			
		final JPanel headPanel = new JPanel(new BorderLayout());
		headPanel.add(buttons, BorderLayout.WEST);
			
		this.add(headPanel, BorderLayout.NORTH);
		this.add(workspace, BorderLayout.CENTER);
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_EXER_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_EXER));
		
		runMit   = getMenuItem(textsRepo.BT_RUN_EXER_PL,   textsRepo.HK_RUN_EXER_PL, event -> exerRun(workList));
		enterMit = getMenuItem(textsRepo.BT_ENTER_EXER_PL, textsRepo.HK_ENTER_EXER_PL, event -> exerEnter(workList));
		playMit  = getMenuItem(textsRepo.BT_PLAY_EXER_PL,  textsRepo.HK_PLAY_EXER_PL, event -> exerPlay(workList));
		helpMit  = getMenuItem(textsRepo.BT_HELP_EXER_PL,  textsRepo.HK_HELP_EXER_PL, event -> exerHelp());
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_EXER);
		menu.setMnemonic(textsRepo.MN_EXER_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(runMit);
		menu.add(enterMit);
		menu.add(playMit);
		menu.add(helpMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_EXER), textsRepo.TIP_EXER_PL, event -> openClose());
	}
	
	/**
	 * Runs a chosen exercise.
	 * @param dic - an object to exchange data.
	 */
	private void exerRun(DictionaryTable dic)
	{
		if (tasks != null)
		{
			int ok = GUI.showConfirmDialog(textsRepo.MG_NEW_TASK_QUESTON, textsRepo.TL_CONF_TASK);
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
	private void exerPlay(DictionaryTable dic)
	{
		if (tasks == null) return;
		
		RecordedSound recordedSound = new RecordedSound();
		int id = tasks.getPhrase().getId();
		
		boolean b = dic.getSound(recordedSound, id);
		if (b) recordedSound.play();
		
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
	private void exerHelp()
	{
		try 
		{
			if (tasks == null) return;
			String prompt = tasks.help();
			answField.setText(prompt);
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Enters an answer and checks a result.
	 * @param dic - an object to exchange data.
	 */
    private void exerEnter(DictionaryTable dic)
	{
		if (tasks == null) return;

		TaskResultEnum check = tasks.enter(answField.getText());
			
		// do actions depending on result
			
		switch (check)
		{
		case wrongAnswer:	// incorrect answer
			answField.setBorder(bordersRepo.getWrongBorder());
			answField.requestFocus();
			GUI.sendMessage(textsRepo.MG_WRONG_ANSW + " (" + textsRepo.MG_ATTEMPT + (tasks.getAttempt() - 1) + ")");
			break;

		case correctAnswer:	// correct answer and there are more questions
		
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
			answField.setBorder(bordersRepo.getCorrectBorder());
			GUI.sendMessage(textsRepo.MG_CORRECT_ANSW + " (" + textsRepo.MG_REST + " " + tasks.getRestOfPhrases() + ")");
			break;
				
		case finalCorrectAnswer:	// correct answer and the last question
			
			if (option != BY_PHRASE_SOUND && option != BY_TRANSL_SOUND) playSound(dic);
	
			taskField.setText(textsRepo.MG_DONE + " (" + textsRepo.MG_PHRASES + " " + numOfExer + ", "
					+ textsRepo.MG_MISTAKES + " " + tasks.getNumOfMistakes() + ")");
				
			answField.setText("");
			answField.setBorder(bordersRepo.getCorrectBorder());
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
			
		if (exer.equals(textsRepo.BT_BY_PHRASE_EXER_PL))
		{
			loadData(dic);
			exerByPhrase();
		}
		if (exer.equals(textsRepo.BT_BY_TRANSL_EXER_PL))
		{
			loadData(dic);
			exerByTransl();
		}
		if (exer.equals(textsRepo.BT_BY_PH_SND_EXER_PL))
		{
			loadData(dic);
			exerByPhraseSound(dic);
		}
		if (exer.equals(textsRepo.BT_BY_TR_SND_EXER_PL))
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
			ArrayList<PhraseSet> data = new ArrayList<>();
			data.addAll(dic.getAll());
			tasks = new Task(data);
			numOfExer = data.size();
			option = 0;
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
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
			
			task = tasks.doExercise(TaskEnum.trainByPhrase);
			answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
			taskField.setText(task);
			answField.setText("");
			answField.requestFocus();
			
			GUI.sendMessage(textsRepo.MG_BY_PHRASE_INFO);
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
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
			
			task = tasks.doExercise(TaskEnum.trainByTranslation);
			answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
			taskField.setText(task);
			answField.setText("");
			answField.requestFocus();
			
			GUI.sendMessage(textsRepo.MG_BY_TRANSL_INFO);
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
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
			
			task = tasks.doExercise(TaskEnum.trainByPhrase);
			if ("".equals(task)) return;
			
			if (!playSound(dic)) // play sound as a task
			{
				exerByPhraseSound(dic);
			}
			else 
			{
				answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
				taskField.setText(textsRepo.MG_SOUND_INFO);
				answField.setText("");
				answField.requestFocus();
				GUI.sendMessage(textsRepo.MG_BY_PH_SND_INFO);
			}
		}
		catch (RuntimeException e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
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
			
			task = tasks.doExercise(TaskEnum.trainByTranslation);
			if ("".equals(task)) return;
			
			if (!playSound(dic)) // play sound as a task
			{	
				exerByPhraseSound(dic);
			}
			else 
			{
				answField.setBorder(bordersRepo.getInTextBorder(textsRepo.LB_HEADER_ASWER));
				taskField.setText(textsRepo.MG_SOUND_INFO);
				answField.setText("");
				answField.requestFocus();
				GUI.sendMessage(textsRepo.MG_BY_TR_SND_INFO);
			}
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Plays the sound of the current task if it exists.
	 * @param dic - an object to exchange data.
	 */
	private boolean playSound(DictionaryTable dic)
	{
		RecordedSound recordedSound = new RecordedSound();
		int id = tasks.getPhrase().getId();
		boolean b = dic.getSound(recordedSound, id);
		if (b) recordedSound.play();
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
	public JButton[] getButtons()
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
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
				
			this.setVisible(false);
		}
		else // open items
		{
			runMit.setEnabled(true);
			enterMit.setEnabled(true);
			playMit.setEnabled(true);
			helpMit.setEnabled(true);
			openCloseBut.setBackground(colorsRepo.getPushedButton());
				
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}

	private JTextPane getTextPane(boolean isEditable) 
	{
		JTextPane textPane = new JTextPane();
		textPane.setFont(fontsRepo.getFontPlate());
		textPane.setEditable(isEditable);
			
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
			
		return textPane;
	}

	private JPanel getPanel(LayoutManager mgr, JComponent... components) {
	
		JPanel panel = new JPanel();
		panel.setLayout(mgr);
		for (JComponent each : components)
		{
			panel.add(each);
		}
		return panel;
	}
//
//	private JButton getButton(String labelPath, String tip, ActionListener action)
//	{
//		JButton menuItem = new JButton();
//		menuItem.setIcon(CommonDataFactoryImpl.getIconFromResource(labelPath));
//		menuItem.setToolTipText(tip);
//		menuItem.addActionListener(action);
//		return menuItem;
//	}

	private JButton getButton(ImageIcon icon, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}

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

	private JComboBox<String> getComboBox(String... components) 
	{
		JComboBox<String> box = new JComboBox<>();
		for (String each : components)
		{
			box.addItem(each);
		}
		return box;
	}

	private JMenuItem getMenuItem(String text, String keyCombination, ActionListener action)
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(keyCombination));
		item.addActionListener(action);
		return item;
	}
}
