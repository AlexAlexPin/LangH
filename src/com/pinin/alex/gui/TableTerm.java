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

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JTable</code>. Displays the tag list.
 */
class TableTerm extends JTable
{
	/** The model for this table */
	private ModelTerm model;

	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
    private FontsRepo fontsRepo;

	/**
	 * Constructor.
	 * @param dictionary - an object to exchange data.
	 * @param dataFactory - a common data factory.
	 */
	TableTerm(DictionaryTable dictionary, CommonDataFactory dataFactory)
	{
        fontsRepo = dataFactory.getFontsRepo();
        TextsRepo textsRepo = dataFactory.getTextsRepo();

		// make the table
			
		model = new ModelTerm(dataFactory);
		this.setModel(model);
			
		final Font fontP = fontsRepo.getFontPlate();
			
		this.setFont(fontP);	
		this.getTableHeader().setFont(fontP);
			
		// set columns widths
			
		final int basicSize     = fontP.getSize();
		final int checkColWidth = basicSize*2;
		this.getColumn(this.getColumnName(ModelTerm.CHECK_COL)).setMaxWidth(checkColWidth);
			
		// set the rows height
			
		final int rowHeigth = basicSize*2;
		this.setRowHeight(rowHeigth);
			
		// add the popup menu
			
		JMenuItem mark     = getMenuItem(textsRepo.BT_SELECT_TAG_PL,  TextsRepo.PH_ICON_SELECT, event -> mark());
		JMenuItem markAll  = getMenuItem(textsRepo.BT_SEL_ALL_TAG_PL, TextsRepo.PH_ICON_SEL_ALL, event -> markAll());
		JMenuItem find     = getMenuItem(textsRepo.BT_FIND_TAG_PL,    TextsRepo.PH_ICON_SEARCH, event -> find(dictionary));
		JMenuItem toPhrase = getMenuItem(textsRepo.BT_TO_PHRASE_TAG_PL, TextsRepo.PH_ICON_TOPHR, event -> toPhrase(dictionary));
			
		JPopupMenu popup = new JPopupMenu();
		popup.add(mark);
		popup.add(markAll);
		popup.addSeparator();
		popup.add(find);
		popup.addSeparator();
		popup.add(toPhrase);
			
		this.setComponentPopupMenu(popup);
	}

	/**
	 * Loads the data.
	 * @param term - an object to get the data.
	 */
	void loadData(Term term) 
	{
		model.loadData(term);
		this.updateUI();
	}
	
	/**
	 * Puts marks in check boxes in selected rows.
	 */
	void mark() 
	{
		int[] selectedRows = this.getSelectedRows();
		if (selectedRows.length == 0) return;
			
		boolean firstSelection = (boolean) this.getValueAt(selectedRows[0], ModelTerm.CHECK_COL);
		for (int each: selectedRows)
		{
			this.setValueAt(!firstSelection, each, ModelTerm.CHECK_COL);
		}
		this.repaint();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows.
	 */
	void markAll() {
		
		boolean firstSelection = (boolean) this.getValueAt(0, ModelTerm.CHECK_COL);
		for (int row=0; row<this.getRowCount(); row++) 
		{
			this.setValueAt(!firstSelection, row, ModelTerm.CHECK_COL);
		}	
		this.repaint();
	}
	
	/**
	 * Gets selected tags. Shows only found phrases in the dictionary table.
	 * @param component - an object to exchange data.
	 */
	void find(DictionaryTable component) 
	{
		Term tags = getSelectedTags();
		component.showOnly(tags);
	}
	
	/**
	 * Adds selected tags to selected phrases.
	 * @param component - an object to exchange data.
	 */
	void toPhrase(DictionaryTable component) 
	{
		Term tags = getSelectedTags();
		component.addTags(tags);
	}

	private Term getSelectedTags() 
	{	
		Term result = new Term();
		for (int row=0; row<this.getRowCount(); row++) 
		{
			if ((Boolean) this.getValueAt(row, ModelTerm.CHECK_COL)) 
			{					
				this.setValueAt(false, row, ModelTerm.CHECK_COL);
				result.add((String) this.getValueAt(row, ModelTerm.TAG_COL));
			}
		}
		this.clearSelection();
		this.repaint();
		return result;
	}

	private JMenuItem getMenuItem(String text, String iconPath, ActionListener action) 
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setIcon(new ImageIcon(LangH.class.getResource(iconPath)));
		item.addActionListener(action);
		return item;
	}
}
