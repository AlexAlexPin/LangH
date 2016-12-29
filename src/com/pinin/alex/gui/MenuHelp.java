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
import javax.swing.*;

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.main.*;

/**
 * Extends <code>JMenu</code>. One menu to operate help actions.
 */
class MenuHelp extends AbstractControlledPanel
{
	// elements of this menu
	private JMenu menu;
	
	// other
	private final static long serialVersionUID = 1L;
	
	// common mains
	private TextsRepo textsRepo;
    private FontsRepo fontsRepo;

	/**
	 * Constructor
	 * @param dataFactory - a common data factory
	 */
	MenuHelp(CommonDataFactory dataFactory)
	{
        textsRepo = dataFactory.getTextsRepo();
        fontsRepo = dataFactory.getFontsRepo();

		// menu items
			
		JMenuItem help = this.getMenuItem(textsRepo.BT_HELP_HELP, textsRepo.HK_HELP_HELP,
				event -> GUI.showHelpDialog(textsRepo.BT_HELP_HELP));
					
		JMenuItem about = this.getMenuItem(textsRepo.BT_ABOUT_HELP, textsRepo.HK_ABOUT_HELP,
				event -> GUI.showAboutDialog(textsRepo.BT_ABOUT_HELP));

		// add elements
		
		menu = new JMenu();
		menu.add(help);
		menu.addSeparator();
		menu.add(about);
			
		menu.setText(textsRepo.MU_HELP);
		menu.setMnemonic(textsRepo.MN_HELP);
		menu.setFont(fontsRepo.getFontPlate());
	}

	public JMenu getMenu()
	{
		return menu;
	}

	public JButton[] getButtons()
	{
		return new JButton[] {};
	}
	
	@Override
	public void openClose() {}

	private JMenuItem getMenuItem(String text, String key_Komb, ActionListener action) 
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(key_Komb));
		item.addActionListener(action);
		return item;
	}
}
