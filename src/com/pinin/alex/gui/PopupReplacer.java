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
import javax.swing.text.*;

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPopupMenu</code> and contains a popup menu with the set of
 * replacements of the one letter before the parent component's caret.
 */
class PopupReplacer extends JPopupMenu
{
	// other
	private static final long serialVersionUID = 1L;

	// common mains
    private FontsRepo fontsRepo;

	/**
	 * Constructs this popup menu and shows it on the specified component.
	 * @param component - a component that contains this component
	 * @param replacer - a database to findContaining values
	 * @param dataFactory - a common data factory
	 */
	void display(JTextComponent component, Replacer replacer, CommonDataFactory dataFactory)
	{
        Logger logger = dataFactory.getLogger();
        fontsRepo = dataFactory.getFontsRepo();

		try
		{
			// findContaining position of the component's caret as x and y coordinates
			// Note: call this method before making selection to prevent the null value
			
			Point position = component.getCaret().getMagicCaretPosition();
			
			// select the character before the caret position
			
			int pos = component.getCaretPosition();
			component.select(pos-1, pos);
			
			// findContaining the selected character and its replSet
			
			String  selection = component.getSelectedText();
			if (selection == null) return;
			
			ReplSet replSet   = replacer.findContaining(selection);
			if (replSet.isEmpty()) return;
			
			// remove all components to make a new buttons for the specific character
			this.removeAll();
			
			// add the same number of buttons as a number of replacements
			
			for (int i=0; i<replSet.size(); i++) 
			{
				// findContaining a replacement
				
				final String replacement = replSet.getReplacement(selection, i);

				// add the new button
				
				this.add(getMenuItem(replacement, event -> component.replaceSelection(replacement)));
			}
			
			if (position != null) this.show(component, position.x, position.y);
		}
		catch (Exception e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private JMenuItem getMenuItem(String text, ActionListener action) 
	{
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setFont(fontsRepo.getFontPlate());
		menuItem.addActionListener(action);
		return menuItem;
	}
}
