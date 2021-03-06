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
import java.util.function.Function;
import java.util.logging.*;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.*;
import com.pinin.alex.*;
import com.pinin.alex.data.*;

/**
 * Extends <code>JTable</code>. Displays <code>ModelPhrases</code>.
 */
class TablePhrases extends JTable
{
	/** The model for this table. */
	private ModelPhrases model;
	
	/** Filter to show only part of phrases of the data table. */
	private RowFilter<AbstractTableModel, Integer> filter;
	private HashSet<Integer> filteredIds;
	private TableRowSorter<AbstractTableModel> sorter;
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	private Logger logger;
    private FontsRepo fontsRepo;

	/**
	 * Constructor.
	 * @param tagList - an object to exchange data.
	 * @param workList - an object to exchange data.
	 * @param dataFactory - a common data factory.
	 */ 
	TablePhrases(TagContainer tagList, AbstractFilteredTable<Integer> workList, CommonDataFactory dataFactory)
	{
        logger = dataFactory.getLogger();
        fontsRepo = dataFactory.getFontsRepo();
        TextsRepo textsRepo = dataFactory.getTextsRepo();

		// make the table
			
		model = new ModelPhrases(dataFactory);
		model.addTableModelListener(event -> tagList.loadData(model.getTags())); // Updates the tags list when
		                                                                         // the tag column has been changed
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
		JMenuItem toTask  = getMenuItem(textsRepo.BT_TO_TASK_TABLE_PL, TextsRepo.PH_ICON_TOTASK, event -> putToTask(workList));
			
		JPopupMenu popup = new JPopupMenu();
		popup.add(mark);
		popup.add(markAll);
		popup.addSeparator();
		popup.add(remove);
		popup.addSeparator();
		popup.add(toTask);
			
		this.setComponentPopupMenu(popup);
			
		// add the sorter
			
		filteredIds = new HashSet<>();

		filter = new RowFilter<AbstractTableModel, Integer>() 
		{
			public boolean include(Entry<? extends AbstractTableModel, ? extends Integer> entry) 
			{
				int row = entry.getIdentifier();
				int id = (int) model.getValueAt(row, ModelPhrases.ID_COL);
				return filteredIds.contains(id);
			}
		};
			
		sorter = new TableRowSorter<>(model);
		sorter.setRowFilter(filter);
		this.setRowSorter(sorter);
		this.clearFilter();
	}

	/**
	 * Loads data from the specified file.
	 * @param file - a file with data.
	 */
	void loadData(File file)
	{
		model.loadData(file);
		this.clearSelection();
		this.repaint();
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
	 * Adds new elements.
	 * @param c - new elements.
	 */
	void addAll(Collection <? extends PhraseSet> c)
	{
		model.addAll(c);
        filteredIds.addAll(c.stream().map((Function<PhraseSet, Integer>) PhraseSet::getId).collect(Collectors.toList()));
		sorter.setRowFilter(filter);
	}
	
	/**
	 * Removes marked rows.
	 */
	void removeMarkedRows() 
	{
		model.removeMarkedRows();
		this.clearSelection();
		this.repaint();
	}
	
	/**
	 * Puts marked rows to the task list.
	 * @param worklist - an object to exchange data.
	 */
	void putToTask(AbstractFilteredTable<Integer> worklist)
	{
		model.toTask(worklist);
		this.clearSelection();
		this.repaint();
	}
	
	/**
	 * Adds the specified tags to marked rows.
	 * @param tags - tags to be added
	 */
	void addTags(Term tags) 
	{
		model.addTags(tags);
		this.clearSelection();
		this.repaint();
	}
	
	/**
	 * Returns a sound with the specified id is a <code>File</code>.
	 * @param toGet - an object to get the sound.
	 * @param id - an id to be used to get the sound.
	 * @return <code>if the sound has been got successfully.
	 */
	boolean getSound(AudioContainer toGet, int id)
	{
		return model.getSound(toGet, id);
	}
	
	/**
	 * Adds the specified sound to the first marked row.
	 * @param sound - a sound to be added.
	 */
	void addSound(AudioContainer sound)
	{
		model.addSound(sound);
		this.clearSelection();
		this.repaint();
	}
	
	/**
	 * Plays a sound of the current row.
	 */
	private void playSound()
	{
		try 
		{
			int pushedButtonRow = this.getSelectedRow();
			if (pushedButtonRow == -1) return;
			
			int id = (int) this.getValueAt(pushedButtonRow, ModelPhrases.ID_COL);	
			RecordedSound recordedSound = new RecordedSound();
			
			boolean b = getSound(recordedSound, id);
			if (b) recordedSound.play();
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Apples the filter for this table.
	 * Dictionary that contain the specified values will be displayed.
	 * @param phrase - a value to find among <code>phrase</code> values.
	 * @param transl - a value to find among <code>transl</code> values.
	 * @param comment - a value to find among <code>comment</code> values.
	 * @param tag - a value to find among <code>tag</code> values.
	 */
	void applyFilter(String phrase, String transl, String comment, String tag) 
	{
		int[] rows = model.indexOfPart(phrase, transl, comment, tag);
		filteredIds.clear();
		for (int row : rows)
		{
			int id = (int) model.getValueAt(row, ModelPhrases.ID_COL);
			filteredIds.add(id);
		}
		sorter.setRowFilter(filter);
	}
	
	/**
	 * Apples the filter for this table.
	 * Dictionary that contain the specified tags will be displayed.
	 * @param tags - a set of tags. 
	 */
	void applyFilter(Term tags) 
	{
		int[] rows = model.indexOfTags(tags);
		filteredIds.clear();
		for (int row : rows)
		{
			int id = (int) model.getValueAt(row, ModelPhrases.ID_COL);
			filteredIds.add(id);
		}
		sorter.setRowFilter(filter);
	}
	
	/**
	 * Removes all filters and shows all data.
	 */
	void clearFilter() 
	{
		for (int row=0; row<model.getRowCount(); row++)
		{
			int id = (int) model.getValueAt(row, ModelPhrases.ID_COL);
			filteredIds.add(id);
		}
		sorter.setRowFilter(filter);
	}
	
	/**
	 * Returns the path of this database.
	 * @return the path of this database.
	 */
	File getPath()
	{
		return model.getPath();
	}
	
	/**
	 * Writes this object to the file.
	 * @param file - a file to be written.
	 */
	void codeFile(File file)
	{
		model.codeFile(file);
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
