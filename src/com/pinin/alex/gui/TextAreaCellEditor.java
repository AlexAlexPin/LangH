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
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * Allows to to use JTextAreas in the JTable's cells. Should be used as an cell editor.
 */
public class TextAreaCellEditor implements TableCellEditor 
{
//
// Variables
//
	
	private JTextArea textArea;
	private JScrollPane scrollPane;
	protected int clickCountToStart = 1;
	protected EventListenerList listenerList = new EventListenerList();
	
//
// Constructors
//	
	
	/**
	 * Constructor
	 */
	public TextAreaCellEditor() 
	{
		clickCountToStart = 2;
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 2));
		textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
	}
 
//
// Implementation TableCellEditor
// 
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	{
		textArea.setFont(table.getFont());
		textArea.setText((value!=null)?value.toString():"");
		return scrollPane;
    }
	
	@Override
	public Object getCellEditorValue() 
	{
		return textArea.getText();
	}
 
	@Override
	public boolean isCellEditable(final EventObject e) 
	{
        if (e instanceof MouseEvent) {
            return ((MouseEvent)e).getClickCount() >= clickCountToStart;
        }
		return true;
	}
	
	@Override
	public boolean shouldSelectCell(EventObject e) 
	{
		return true;
    }
 
	@Override
	public boolean stopCellEditing() 
	{
		fireEditingStopped();
		return true;
    }
 
	@Override
	public void cancelCellEditing() 
	{
    	fireEditingCanceled();
    }
 
	@Override
	public void addCellEditorListener(CellEditorListener l) 
	{
    	listenerList.add(CellEditorListener.class, l);
    }
 
	@Override
	public void removeCellEditorListener(CellEditorListener l) 
	{
		listenerList.remove(CellEditorListener.class, l);
    }
 
	protected void fireEditingStopped() 
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0 ; i < listeners.length; i++) 
		{
			if (listeners[i] instanceof CellEditorListener) 
			{
				((CellEditorListener)listeners[i]).editingStopped(new ChangeEvent(this));
			}
		}
	}
 
	protected void fireEditingCanceled() 
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0 ; i < listeners.length; i++) 
		{
			if (listeners[i] instanceof CellEditorListener) 
			{
				((CellEditorListener)listeners[i]).editingCanceled(new ChangeEvent(this));
			}
		}
	}
	
} // end TextAreaCellEditor
