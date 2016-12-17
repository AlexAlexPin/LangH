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
import javax.swing.table.*;

/**
 * Allows to to use JButtons in the JTable's cells. Should be used as an cell editor.
 */
public class ButtonCellEditor extends AbstractCellEditor
		implements TableCellEditor, ActionListener, MouseListener 
{
//	
// Variables
//
	
	private JTable 	table;
	private Action 	action;
	private JButton button;
	private Object 	value;
	private boolean mousePressed;
	
	private static final long serialVersionUID = 1L;
	
//
// Constructors
//	
	
	/**
	 * Constructor
	 * @param table - the table with buttons
	 * @param action - the action to be invoked when the button is pressed
	 */
	public ButtonCellEditor(JTable table, Action action) 
	{
		this.table = table;
		this.action = action;
		
		button = new JButton();
		button.setFocusPainted(false);
		button.addActionListener(this);
		
		table.addMouseListener(this);
	}

//
// Implementation TableCellEditor
// 
	
	@Override
	public Component getTableCellEditorComponent(
		JTable table, Object value, boolean isSelected, int row, int column) {
		
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
		
		this.value = value;
		return button;
	}

//
// Extending AbstractCellEditor
//
	
	@Override
	public Object getCellEditorValue() 
	{
		return value;
	}

//
//  Implementation ActionListener
//
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// the button has been pressed. stopCapture editing and invoke the action
		int row = table.convertRowIndexToModel(table.getEditingRow());
		fireEditingStopped();

		// invoke the action
		ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, row + "");
		action.actionPerformed(event);
	}

//
//  Implementation MouseListener
//
	
	@Override
    public void mousePressed(MouseEvent e)  
	{
    	if (table.isEditing() && table.getCellEditor() == this)
    		mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
    	if (mousePressed &&  table.isEditing())
    		table.getCellEditor().stopCellEditing();
    	mousePressed = false;
    }

    public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
} // end ButtonCellEditor
