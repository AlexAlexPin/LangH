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
import javax.swing.*;
import javax.swing.text.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

class MenuEdit extends AbstractControlledPanel
{
	// data
	private Replacer replacer;
	
	// elements of this menu
	private JMenu menu;
	
	// other
	private final static long serialVersionUID = 1L;
	
	// common mains
    private CommonDataFactory dataFactory;
	private TextsRepo textsRepo;
    private FontsRepo fontsRepo;

	/**
	 * Constructor.
     * @param dataFactory - a common data factory
	 */
	MenuEdit(CommonDataFactory dataFactory)
	{
        this.dataFactory = dataFactory;
		textsRepo = dataFactory.getTextsRepo();
        fontsRepo = dataFactory.getFontsRepo();

		replacer = new Replacer();
		CharSequence replaces = dataFactory.getCharsFromResource(TextsRepo.PH_REPLACE);
		replacer.parseSequence(replaces);
		
		// menu items

		JMenuItem replace = getMenuItem(textsRepo.BT_REPL_EDIT, textsRepo.HK_REPL_EDIT, event -> clearData());
		replace.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_REPL));

		JMenuItem settings = getMenuItem(textsRepo.BT_SET_EDIT, textsRepo.HK_SET_EDIT, event -> showOpenDialog());
		settings.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SETT));
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_EDIT);
		menu.setMnemonic(textsRepo.MN_EDIT);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(replace);
		menu.addSeparator();
		menu.add(settings);
	}

	/**
	 * Clears all data and allows to fill the new database
	 */
	private void clearData()
	{
		Component comp = GUI.getFocusOwnerSt();

		JTextComponent tComp;
		if (comp instanceof JTextComponent) 
		{			
			tComp = (JTextComponent) comp;
			new PopupReplacer().display(tComp, replacer, dataFactory);
		}
	}

	private void showOpenDialog()
	{
		GUI.showSettingDialog(textsRepo.BT_SET_EDIT);
	}

	public JMenu getMenu()
	{
		return menu;
	}

	public JButton[] getToolBarButtons()
	{
		return new JButton[] {};
	}
	
	@Override
	public void openClose() {}
    
	private JMenuItem getMenuItem(String text, String keyCombination, ActionListener action) 
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(keyCombination));
		item.addActionListener(action);
		return item;
	}
} 
