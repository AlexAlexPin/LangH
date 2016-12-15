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
import javax.swing.*;
import javax.swing.table.*;

/**
 * Allows to to use JButtons in the JTable's cells. Should be used as an cell renderer.
 */
public class ButtonCellRenderer  implements TableCellRenderer 
{
//
// Variables
//
	
	private JButton button;

//
// Constructors
//
	
	/**
	 * Constructor
	 */
	public ButtonCellRenderer() 
	{
		button = new JButton();
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
	}

//
//  Implementation TableCellRenderer
//
	
	@Override
	public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		if (isSelected) 
		{
			button.setForeground(table.getSelectionForeground());
	 		button.setBackground(table.getSelectionBackground());
		}
		else 
		{
			button.setForeground(table.getForeground());
			button.setBackground(UIManager.getColor("Button.background"));
		}
		
		if (value == null) 
		{
			button.setText("");
			button.setIcon(null);
		}
		else if (value instanceof Icon) 
		{
			button.setText("");
			button.setIcon((Icon) value);
		}
		else 
		{
			button.setText(value.toString());
			button.setIcon(null);
		}
		
		return button;
	}
	
} // the ButtonCellRenderer
