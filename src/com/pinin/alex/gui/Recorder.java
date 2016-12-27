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
import java.util.logging.*;
import javax.swing.*;

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Allows to record sound.
 */
class Recorder extends AbstractControlledPanel
{
	// data
	private RecordedSound audio;
	
	// elements of this panel
	private JButton captureBut;
	private JButton stopBut;
	
	// elements of this menu
	private JMenu menu;
	private JMenuItem captureMit;
	private JMenuItem stopMit;
	private JMenuItem playMit;
	private JMenuItem saveMit;
	
	// tool bar buttons
	private JButton openCloseBut;
	
	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
	private TextsRepo textsRepo;
	private Logger logger;
    private FontsRepo fontsRepo;
    private ColorsRepo colorsRepo;
	
	/**
	 * Constructor.
	 * @param dic - an object to exchange data.
	 */
	Recorder(DictionaryTable dic, CommonDataFactory dataFactory)
	{
        textsRepo = dataFactory.getTextsRepo();
        logger = dataFactory.getLogger();
        fontsRepo = dataFactory.getFontsRepo();
        colorsRepo = dataFactory.getColorsRepo();

		// data

		audio = new RecordedSound();
			
		// buttons
			
		captureBut = getButton(dataFactory.getIconFromResource(
		        TextsRepo.PH_ICON_REC),  textsRepo.TIP_REC_SOUND, event -> capture(), false);
		stopBut = getButton(dataFactory.getIconFromResource(
		        TextsRepo.PH_ICON_STOP), textsRepo.TIP_STOP_SOUND, event -> stop(), false);
		stopBut.setEnabled(false);
			
		JButton playBut = getButton(dataFactory.getIconFromResource(
		        TextsRepo.PH_ICON_PLAY), textsRepo.TIP_PLAY_SOUND, event -> playSound(), false);
		JButton saveBut = getButton(dataFactory.getIconFromResource(
		        TextsRepo.PH_ICON_SAVE), textsRepo.TIP_SAVE_SOUND, event -> saveSound(dic), false);
			
		JPanel buttons = getPanel(new GridLayout(1,4), captureBut, stopBut, playBut, saveBut);
			
		// add elements
			
		this.setBorder(dataFactory.getBordersRepo().getPanelBorder());
		this.add(buttons);
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_SOUND_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SOUND));
		
		captureMit = getMenuItem(textsRepo.BT_REC_SOUND_PL,  textsRepo.HK_REC_SOUND_PL, event -> capture());
		stopMit    = getMenuItem(textsRepo.BT_STOP_SOUND_PL, textsRepo.HK_STOP_SOUND_PL, event -> stop());
		playMit    = getMenuItem(textsRepo.BT_PLAY_SOUND_PL, textsRepo.HK_PLAY_SOUND_PL, event -> playSound());
		saveMit    = getMenuItem(textsRepo.BT_SAVE_SOUND_PL, textsRepo.HK_SAVE_SOUND_PL, event -> saveSound(dic));
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_SOUND);
		menu.setMnemonic(textsRepo.MN_SOUND_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(captureMit);
		menu.add(stopMit);
		menu.add(playMit);
		menu.add(saveMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(dataFactory.getIconFromResource(
		        TextsRepo.PH_ICON_SOUND), textsRepo.TIP_SOUND_PL, event -> openClose(), true);
	}

	/**
	 * Runs sound recording. Unblocks and blocks related menu items and buttons.
	 */
	private void capture()
	{
		try 
		{
			GUI.sendMessage(textsRepo.MG_RECORDING);
			captureBut.setEnabled(false);
			captureMit.setEnabled(false);
			stopBut.setEnabled(true);
			stopMit.setEnabled(true);
			audio.capture();
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Finishes sound recording. Unblocks and blocks related menu items and buttons.
	 */
	private void stop()
	{
		try 
		{
			GUI.sendMessage(textsRepo.MG_RECORDED);
			captureBut.setEnabled(true);
			captureMit.setEnabled(true);
			stopBut.setEnabled(false);
			stopMit.setEnabled(false);
			audio.stopCapturing();
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

    private void playSound()
	{
		try
		{
			GUI.sendMessage("");
			audio.play();
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Saves the recorded sound to the file.
	 * @param dic - an object to exchange data.
	 */
	private void saveSound(DictionaryTable dic)
	{
		if (audio == null) return;
		dic.addSound(audio);
	}

	public JMenu getMenu()
	{
		return menu;
	}

	public JButton[] getToolBarButtons()
	{
		return new JButton[] {openCloseBut};
	}

	public void openClose()
	{
		if (this.isVisible()) // close items
		{
			captureMit.setEnabled(false);
			stopMit.setEnabled(false);
			playMit.setEnabled(false);
			saveMit.setEnabled(false);
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
				
			this.setVisible(false);
		}
		else // open items
		{
			captureMit.setEnabled(true);
			stopMit.setEnabled(true);
			playMit.setEnabled(true);
			saveMit.setEnabled(true);
			openCloseBut.setBackground(colorsRepo.getPushedButton());
				
			this.setVisible(true);
		}
	}

	private JButton getButton(ImageIcon icon, String tip, ActionListener action, boolean contentAreaFillen)
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
        button.setContentAreaFilled(contentAreaFillen);
		return button;
	}

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

	private JMenuItem getMenuItem(String text, String keyCombination, ActionListener action)
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(keyCombination));
		item.addActionListener(action);
		return item;
	}
}
