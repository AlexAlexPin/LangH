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

import java.util.*;
import javax.swing.table.*;

import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>AbstractTableModel</code> and allows to create a table model for <code>Term</code> object.
 */
public class ModelTerm extends AbstractTableModel 
{

//
// Variables
//

	/** The main database. */
	private ArrayList<String> data;
	
	/** The column with check boxes. */
	private ArrayList<Boolean> checkbox;
	
	/** Columns names. */
	private String[] columns;
	
	/** The column with check boxes. */
	public static final int CHECK_COL = 0;
	
	/** The column with tags. */
	public static final int TAG_COL = 1;
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	
//
// Constructors
//

	/**
	 * Constructor
	 */
	public ModelTerm() 
	{
		data = new ArrayList<String>();
		checkbox = new ArrayList<Boolean>();
		columns =  new String[] {TXT.LB_COL_CHECK, TXT.LB_COL_TAG};
	}

// Methods
	
	/**
	 * Loads the data.
	 * @param tags - an object to get the data.
	 */
	void loadData(Term term) 
	{
		data.clear();
		checkbox.clear();
		for (String each : term)
		{
			data.add(each);
			checkbox.add(false);
		}
	}

//
// Extending AbstractTableModel
//	
	
    @Override
	public int getRowCount() 
    { 
    	return data.size();
	}
	
    @Override
	public int getColumnCount()
    {
    	return columns.length;
	}
	
    @Override
	public Object getValueAt(int rowIndex, int columnIndex) 
    {
    	switch (columnIndex) 
    	{
			case CHECK_COL: return checkbox.get(rowIndex);
			case TAG_COL:   return data.get(rowIndex);
			default:        return "";
    	}
	}
    
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) 
	{
		switch (columnIndex) 
		{
			case CHECK_COL: checkbox.set(rowIndex, (Boolean) obj); break;
			case TAG_COL:   data.set(rowIndex, (String) obj);      break;
			default: break;
		}
	}
	
	@Override
	public String getColumnName(int columnIndex) 
	{
		return columns[columnIndex];
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int columnIndex) 
	{
		switch (columnIndex) 
		{
			case CHECK_COL: return Boolean.class;
			default:        return Object.class;
		}
	}
    
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		switch (columnIndex) 
		{
			case TAG_COL: return false;
			default:      return true;
		}
	}
	
} // end ModelTerm