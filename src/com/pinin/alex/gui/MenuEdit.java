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
import javax.swing.*;
import javax.swing.text.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

public class MenuEdit extends AbstractControlledPanel
{
//
// Variables
//	
	
	// data
	
	Replacer repl;
	
	// elements of this menu

	JMenu menu;
	private JMenuItem replace;
	private JMenuItem settings;
	
	// other
	
	/** Default serial version ID. */
	private final static long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();

//
// Constructors
//
	
	/**
	 * Constructor.
	 * @param - dic - an object to exchange data.
	 * @param worklist - an object to exchange data.
	 */
	public MenuEdit(DictionaryTable dic, DictionaryTable worklist)
	{
		
		repl = new Replacer();
		CharSequence csq = LangH.getResourceContent(Texts.PH_REPLACE);
		repl.parseSequence(csq);
		
		// menu items
		
		replace = getMenuItem(TXT.BT_REPL_EDIT, TXT.HK_REPL_EDIT, event -> replace());
		replace.setIcon(LangH.getResource(Texts.PH_ICON_REPL));
				
		settings = getMenuItem(TXT.BT_SET_EDIT, TXT.HK_SET_EDIT, event -> settings());
		settings.setIcon(LangH.getResource(Texts.PH_ICON_SETT));
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.MU_EDIT);
		menu.setMnemonic(TXT.MN_EDIT);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(replace);
		menu.addSeparator();
		menu.add(settings);
	}
	
//
// Methods
//
	
	/**
	 * Clears all data and allows to fill the new database
	 */
	public void replace() 
	{
		Component comp = GUI.getFocusOwnerSt();

		JTextComponent tComp = null;
		if (comp instanceof JTextComponent) 
		{			
			tComp = (JTextComponent) comp;
			new PopupReplacer().display(tComp, repl);
		}
	}
	
	/**
	 * Shows the open dialog and opens the chosen file
	 * @param frame - the main frame of this GUI 
	 */
	public void settings() 
	{
		GUI.showSettingDialog(TXT.BT_SET_EDIT);
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
	
} // end MenuEdit
