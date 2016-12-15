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
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Allows to record sound.
 */
public class Recorder extends AbstractControlledPanel
{
//
// Variables
//
	
	// data
	
	/** An object that allows to work with sounds. */
	private SoundWav audio;
	
	// elements of this panel
	
	private JButton captureBut;
	private JButton stopBut;
	
	// elements of this menu
	
	JMenu menu;
	private JMenuItem captureMit;
	private JMenuItem stopMit;
	private JMenuItem playMit;
	private JMenuItem saveMit;
	
	// tool bar buttons
	
	private JButton openCloseBut;
	
	// other
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	private final Logger LOGGER = LangH.getLogger();
	
//
// Constructors
//
	
	/**
	 * Constructor.
	 * @param dic - an object to exchange data.
	 */
	public Recorder(DictionaryTable dic) 
	{
		// data
		
		audio = new SoundWav();
		String tempFolder = System.getProperty("user.home") + File.separator + ".langH";
		audio.setTempFolder(new File(tempFolder));
			
		// buttons
			
		captureBut = getButton(Texts.PH_ICON_REC,  TXT.TIP_REC_SOUND,  event -> capture());
		stopBut    = getButton(Texts.PH_ICON_STOP, TXT.TIP_STOP_SOUND, event -> stop());
		stopBut.setEnabled(false);
			
		JButton playBut = getButton(Texts.PH_ICON_PLAY, TXT.TIP_PLAY_SOUND, event -> play());
		JButton saveBut = getButton(Texts.PH_ICON_SAVE, TXT.TIP_SAVE_SOUND, event -> save(dic));
			
		JPanel buttons = getPanel(new GridLayout(1,4), captureBut, stopBut, playBut, saveBut);
			
		// add elements
			
		this.setBorder(LangH.getBorders().getPanelBorder());
		this.add(buttons);
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(TXT.BT_SHOW_HIDE_PL, TXT.HK_SOUND_PL, event -> openClose());
		openCloseMit.setIcon(LangH.getResource(Texts.PH_ICON_SOUND));
		
		captureMit = getMenuItem(TXT.BT_REC_SOUND_PL,  TXT.HK_REC_SOUND_PL,  event -> capture());
		stopMit    = getMenuItem(TXT.BT_STOP_SOUND_PL, TXT.HK_STOP_SOUND_PL, event -> stop());
		playMit    = getMenuItem(TXT.BT_PLAY_SOUND_PL, TXT.HK_PLAY_SOUND_PL, event -> play());	
		saveMit    = getMenuItem(TXT.BT_SAVE_SOUND_PL, TXT.HK_SAVE_SOUND_PL, event -> save(dic));
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.MU_SOUND);
		menu.setMnemonic(TXT.MN_SOUND_PL);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(captureMit);
		menu.add(stopMit);
		menu.add(playMit);
		menu.add(saveMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(LangH.getResource(Texts.PH_ICON_SOUND), TXT.TIP_SOUND_PL, event -> openClose());
	}
	
//
// Methods
//
	
	/**
	 * Runs sound recording. Unblocks and blocks related menu items and buttons.
	 */
	public void capture() 
	{
		try 
		{
			GUI.sendMessage(TXT.MG_RECORDING);
			captureBut.setEnabled(false);
			captureMit.setEnabled(false);
			stopBut.setEnabled(true);
			stopMit.setEnabled(true);
			audio.capture();
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Finishes sound recording. Unblocks and blocks related menu items and buttons.
	 */
	public void stop() 
	{
		try 
		{
			GUI.sendMessage(TXT.MG_RECORDED);
			captureBut.setEnabled(true);
			captureMit.setEnabled(true);
			stopBut.setEnabled(false);
			stopMit.setEnabled(false);
			audio.stop();
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Plays a recorded sound.
	 */
	public void play() 
	{
		try
		{
			GUI.sendMessage("");
			audio.play();
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Saves the recorded sound to the file.
	 * @param dic - an object to exchange data.
	 */
	public void save(DictionaryTable dic) 
	{
		if (audio == null) return;
		dic.addSound(audio);
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
			captureMit.setEnabled(false);
			stopMit.setEnabled(false);
			playMit.setEnabled(false);
			saveMit.setEnabled(false);
			openCloseBut.setBackground(LangH.getColors().getBasicBackground());
				
			this.setVisible(false);
		}
		else // open items
		{
			captureMit.setEnabled(true);
			stopMit.setEnabled(true);
			playMit.setEnabled(true);
			saveMit.setEnabled(true);
			openCloseBut.setBackground(LangH.getColors().getPushedButton());
				
			this.setVisible(true);
		}
	}
	
	/**
	 * Returns a new <code>JButton</code> object
	 * @param iconPath - a path of icon for this object
	 * @param tip - a tip text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(String iconPath, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(LangH.getResource(iconPath));
		button.setToolTipText(tip);
		button.addActionListener(action);
		button.setContentAreaFilled(false);
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
	 * Returns a new <code>JPanel</code> object
	 * @param mgr - a layout manager for this object
	 * @param components - components for this object
	 * @return a new <code>JPanel</code> object
	 */
	private JPanel getPanel(LayoutManager mgr, JComponent... components) 
	{
		JPanel panel = new JPanel();
		panel.setLayout(mgr);
		for (JComponent each : components)
		{
				panel.add(each);
		}
		return panel;
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
	
} // end Recorder
