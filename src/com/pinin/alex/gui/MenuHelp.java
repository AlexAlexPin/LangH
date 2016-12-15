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
import javax.swing.*;

import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JMenu</code>. One menu to operate help actions.
 */
public class MenuHelp extends AbstractControlledPanel 
{
//
// Variables
//
	
	// elements of this menu
	
	private JMenu menu;
	
	// other
	
	/** Default serial version ID. */
	private final static long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	
//
// Constructors
//
	
	/**
	 * Constructor
	 */
	public MenuHelp() 
	{
		// menu items
			
		JMenuItem help = this.getMenuItem(TXT.BT_HELP_HELP, TXT.HK_HELP_HELP, 
				event -> GUI.showHelpDialog(TXT.BT_HELP_HELP));
					
		JMenuItem about = this.getMenuItem(TXT.BT_ABOUT_HELP, TXT.HK_ABOUT_HELP, 
				event -> GUI.showAboutDialog(TXT.BT_ABOUT_HELP));

		// add elements
		
		menu = new JMenu();
		menu.add(help);
		menu.addSeparator();
		menu.add(about);
			
		menu.setText(TXT.MU_HELP);
		menu.setMnemonic(TXT.MN_HELP);
		menu.setFont(LangH.getFonts().getFontPlate());
	}
	
//
// Methods
//
	
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
		return new JButton[] {};
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
	
} // end MenuHelp
