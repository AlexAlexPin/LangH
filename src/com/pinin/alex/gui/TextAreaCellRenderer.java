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
 * Allows to to use JTextAreas in the JTable's cells. Should be used as an cell renderer.
 */
public class TextAreaCellRenderer extends DefaultTableCellRenderer 
{
//
// Variables
//

	private JScrollPane scrollPane;
	private JTextArea textArea;
	private static final long serialVersionUID = 1L;
	 
//
// Constructors
//
	
	/**
	 * Constructor
	 */
	public TextAreaCellRenderer() 
	{
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
	}
	 
//
// Extending DefaultTableCellRenderer
//
	
	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		textArea.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
		textArea.setText(value.toString());
		textArea.setFont(c.getFont());
		
		textArea.setForeground(c.getForeground());
		textArea.setBackground(c.getBackground());
		
		return scrollPane;
	}
	
} // end TextAreaCellRenderer
