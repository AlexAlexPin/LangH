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
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JTable</code>. Displays the phrases list for exercises.
 */
class TablePhrasesFiltered extends JTable
{
	/** The model for this table */
	private ModelPhrases model;
	
	/** Filter to show only part of phrases of the main table. */
	private RowFilter<TableModel, Integer> filter;
	private HashSet<Integer> filteredIds;
	private TableRowSorter<TableModel> sorter;
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	private Logger logger;
	private TextsRepo textsRepo;
    private FontsRepo fontsRepo;

	/**
	 * Constructor.
	 * @param model - a model for this table.
	 * @param dataFactory - a common data factory
	 */
	TablePhrasesFiltered(TableModel model, CommonDataFactory dataFactory)
	{
        logger = dataFactory.getLogger();
        textsRepo = dataFactory.getTextsRepo();
        fontsRepo = dataFactory.getFontsRepo();

		// make the table
		
		this.model = (ModelPhrases) model;
		
		final Font fontP = fontsRepo.getFontPlate();
			
		this.setModel(model);
		this.setFont(fontP);
		this.getTableHeader().setFont(fontP);	
			
		this.setDefaultRenderer(JTextArea.class, new TextAreaCellRenderer());	
		this.setDefaultEditor  (JTextArea.class, new TextAreaCellEditor());
			
		this.setDefaultRenderer(JButton.class,   new ButtonCellRenderer());
		this.setDefaultEditor  (JButton.class,   new ButtonCellEditor(this, new AbstractAction() 
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event) 
			{
				playSound();
			}
		}));
			
		// set columns widths
			
		final int basicSize     = fontP.getSize();
		final int checkColWidth = basicSize*2;
		final int idColWidth    = basicSize*4;
		final int playColWidth  = basicSize*3;
			
		this.getColumn(this.getColumnName(ModelPhrases.CHECK_COL)).setMaxWidth(checkColWidth);
		this.getColumn(this.getColumnName(ModelPhrases.ID_COL))   .setMaxWidth(idColWidth);
		this.getColumn(this.getColumnName(ModelPhrases.PLAY_COL)) .setMaxWidth(playColWidth);
			
		// set the rows height
			
		final int rowHeigth = basicSize*48/10; // 3 rows; 62/10 - 4 rows
			
		this.setRowHeight(rowHeigth);
			
		// add the popup menu
			
		JMenuItem mark    = getMenuItem(textsRepo.BT_SELECT_TABLE_PL,  TextsRepo.PH_ICON_SELECT, event -> mark());
		JMenuItem markAll = getMenuItem(textsRepo.BT_SEL_ALL_TABLE_PL, TextsRepo.PH_ICON_SEL_ALL, event -> markAll());
		JMenuItem remove  = getMenuItem(textsRepo.BT_DELETE_TABLE_PL,  TextsRepo.PH_ICON_DELETE, event -> removeMarkedRows());
			
		JPopupMenu popup = new JPopupMenu();
		popup.add(mark);
		popup.add(markAll);
		popup.addSeparator();
		popup.add(remove);
			
		this.setComponentPopupMenu(popup);
			
		// add the sorter
			
		filteredIds = new HashSet<>();

		filter = new RowFilter<TableModel, Integer>() 
		{
			public boolean include(Entry<? extends TableModel, ? extends Integer> entry) 
			{
				int row = entry.getIdentifier();
				int id = (int) model.getValueAt(row, ModelPhrases.ID_COL);
				return filteredIds.contains(id);
			}
		};
			
		sorter = new TableRowSorter<>(model);
		this.setRowSorter(sorter);
	}

	/**
	 * Loads data from the specified file.
	 * @param file - a file with data.
	 */
	void loadData(File file)
	{
		filteredIds.clear();
		if (file == null) 
		{
			sorter.setRowFilter(filter);
			return;
		}
		
		LinkedList<Integer> ids = new LinkedList<>();
		Common.readIntFile(file, ids);
        filteredIds.addAll(ids);
		sorter.setRowFilter(filter);
	}
	
	/**
	 * Puts marks in check boxes in selected rows
	 */
	void mark() 
	{
		int[] selectedRows = getSelectedRows();
		if (selectedRows.length == 0) return;
		
		boolean firstSelection = (boolean) this.getValueAt(selectedRows[0], ModelPhrases.CHECK_COL);
		for (int each: selectedRows) 
		{
			this.setValueAt(!firstSelection, each, ModelPhrases.CHECK_COL);
		}
		this.repaint();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows
	 */
	void markAll() 
	{
		boolean firstSelection = (boolean) this.getValueAt(0, ModelPhrases.CHECK_COL);
		for (int row=0; row<this.getRowCount(); row++) 
		{
			this.setValueAt(!firstSelection, row, ModelPhrases.CHECK_COL);
		}
		this.repaint();
	}
	
	/**
	 * Adds a new <code>Collection</code> of elements to add to the filter.
	 * @param ids - elements to be added.
	 */
	void addAll(Collection<? extends Integer> ids) 
	{
		int ok = GUI.showConfirmDialog(textsRepo.MG_TO_TASK_QUESTION, textsRepo.TL_CONF_SELECT);
		if (ok == JOptionPane.OK_OPTION) 
		{
			filteredIds.addAll(ids);
			sorter.setRowFilter(filter);
		}
	}
	
	/**
	 * Removes marked rows.
	 */
	void removeMarkedRows() 
	{
		int ok = GUI.showConfirmDialog(textsRepo.MG_REMOVE_QUESTION, textsRepo.TL_CONF_REMOVE);
		if (ok == JOptionPane.OK_OPTION) 
		{
			LinkedList<Integer> markedRows = getMarkedIds();
			filteredIds.removeAll(markedRows);
			sorter.setRowFilter(filter);
			this.repaint();
		}
	}
	
	/**
	 * Returns a list of all phrases in this table.
	 * @return a list of all phrases in this table.
	 */
	LinkedList<Phrase> getAll()
	{
		LinkedList<Phrase> result = new LinkedList<>();
		for (int row=0; row<this.getRowCount(); row++)
		{
			int    id      = (int)    this.getValueAt(row, ModelPhrases.ID_COL);
			String phrase  = (String) this.getValueAt(row, ModelPhrases.PHRASE_COL);
			Term   transl  = (Term) this.getValueAt(row, ModelPhrases.TRANSL_COL);
			Term   comment = (Term) this.getValueAt(row, ModelPhrases.COMMENT_COL);
			Term   tag     = (Term) this.getValueAt(row, ModelPhrases.TAG_COL);
			
			result.add(new Phrase(id, phrase, transl, comment, tag));
		}
		return result;
	}
	
	/**
	 * Returns a list of marked rows.
	 * @return a list of marked rows.
	 */
	private LinkedList<Integer> getMarkedIds()
	{
		LinkedList<Integer> result = new LinkedList<>();
			
		for (int row=0; row<getRowCount(); row++) 
		{
			if ((boolean) this.getValueAt(row, ModelPhrases.CHECK_COL)) 
			{
				this.setValueAt(false, row, ModelPhrases.CHECK_COL);
				result.add((int) this.getValueAt(row, ModelPhrases.ID_COL));
			}
		}
		return result;
	}

	private void playSound()
	{
		try 
		{
			int pushedButtonRow = this.getSelectedRow();
			if (pushedButtonRow == -1) return;
			
			int id = (int) this.getValueAt(pushedButtonRow, ModelPhrases.ID_COL);	
			Sound sound = new Sound();
			
			boolean b = model.getSound(sound, id);
			if (b) sound.play();
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Fill the specified <code>AudioContainer</code> with sound with the specified id.
	 * @param toGet - an object to get the sound.
	 * @param id - an id to be used to get the sound.
	 * @return <code>true</code> if the sound has been got successfully.
	 */
	boolean getSound(AudioContainer toGet, int id)
	{
		return model.getSound(toGet, id);
	}
	
	/**
	 * Writes this data to the specified file.
	 * @param file - a file ti be written.
	 */
	void codeFile(File file)
	{
		try
		{
			Common.writeIntFile(file, filteredIds);
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
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
