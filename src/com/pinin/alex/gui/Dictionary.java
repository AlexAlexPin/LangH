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
import java.io.*;
import java.util.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Displays the whole phrases list.
 */
public class Dictionary extends AbstractDictionaryTable
{
//
// Variables
//
	// elements of this panel
	
	private Search search;
	private TablePhrases table;
	private JButton filterBut;
	private JButton findBut;
	
	// elements of this menu
	
	private JMenu menu;
	private JMenu menuTags;
	private JMenu menuSearch;
	private JMenuItem markMit;
	private JMenuItem markAllMit;
	private JMenuItem removeMit;
	private JMenuItem toTaskMit;
	private JMenuItem filterMit;
	private JMenuItem findMit;
	
	// tool bar buttons
	
	private JButton openCloseBut;
	private JButton openCloseTagBut;
	private JButton openCloseSearchBut;
	
	// other
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	
//
// Constructor
//
	
	/**
	 * Constructor.
	 */ 
	public Dictionary() 
	{	
		Tags tags = new Tags(this);
		search = new Search();
		
		// header
			
		JLabel header = this.getLabel(TXT.LB_HEADER_TABLE_PL);
			
		// buttons
			
		JButton mark    = getButton(Texts.PH_ICON_SELECT,  TXT.TIP_SELECT,       event -> mark());
		JButton markAll = getButton(Texts.PH_ICON_SEL_ALL, TXT.TIP_SEL_ALL,      event -> markAll());
		JButton remove  = getButton(Texts.PH_ICON_DELETE,  TXT.TIP_DELETE,       event -> removeMarkedRows());
		JButton toTask  = getButton(Texts.PH_ICON_TOTASK,  TXT.TIP_TOTASK,       event -> toTask(GUI.getWorklist()));
		filterBut       = getButton(Texts.PH_ICON_FILTER,  TXT.TIP_CLEAR_FILTER, event -> clearFilter());
		findBut         = getButton(Texts.PH_ICON_SEARCH,  TXT.TIP_FIND,         event -> showOnly());
			
		JToolBar buttons = getToolBar(mark, markAll, remove, toTask, filterBut, findBut);
			
		// table
			
		table = new TablePhrases(tags, GUI.getWorklist());
		
		// add elements
			
		this.setBorder(LangH.getBorders().getPanelBorder());
		this.setLayout(new GridBagLayout());
					
		this.add(buttons,                new GBC(0, 0, 1, 1).setWeight(0,   0  ).setAnchor(GBC.WEST));
		this.add(header,                 new GBC(1, 0, 1, 1).setWeight(100, 0  ).setFill(GBC.HORIZONTAL));
		this.add(tags,                   new GBC(2, 0, 1, 3).setWeight(30,  100).setFill(GBC.BOTH));
		this.add(search,                 new GBC(0, 1, 2, 1).setWeight(100, 10 ).setFill(GBC.BOTH));
		this.add(new JScrollPane(table), new GBC(0, 2, 2, 1).setWeight(100, 100).setFill(GBC.BOTH));
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(TXT.BT_SHOW_HIDE_PL, TXT.HK_ADD_PL, event -> openClose());
		openCloseMit.setIcon(LangH.getResource(Texts.PH_ICON_BOOK));
		
		markMit    = getMenuItem(TXT.BT_SELECT_TABLE_PL,  TXT.HK_SELECT_TABLE_PL,  event -> mark());
		markAllMit = getMenuItem(TXT.BT_SEL_ALL_TABLE_PL, TXT.HK_SEL_ALL_TABLE_PL, event -> markAll());
		removeMit  = getMenuItem(TXT.BT_DELETE_TABLE_PL,  TXT.HK_DELETE_TABLE_PL,  event -> removeMarkedRows());
		toTaskMit  = getMenuItem(TXT.BT_TO_TASK_TABLE_PL, TXT.HK_TO_TASK_TABLE_PL, event -> toTask(GUI.getWorklist()));
		filterMit  = getMenuItem(TXT.BT_FILTER_TABLE_PL,  TXT.HK_TO_TASK_TABLE_PL, event -> clearFilter());
		
		// menu tags
		
		menuTags = tags.getMenu();
		
		// menu search
		
		JMenuItem openCloseSearchMit = getMenuItem(TXT.BT_SHOW_HIDE_PL, TXT.HK_SEARCH_PL, 
				event -> openCloseSearch(search));
		openCloseSearchMit.setIcon(LangH.getResource(Texts.PH_ICON_SEARCH));
		
		findMit = getMenuItem(TXT.BT_FIND_SEARCH_PL,   TXT.BT_FIND_SEARCH_PL,   event -> showOnly());
		
		menuSearch = new JMenu();
		menuSearch.setText(TXT.BT_SEARCH_PL);
		menuSearch.setFont(LangH.getFonts().getFontPlate());
		menuSearch.add(openCloseSearchMit);
		menuSearch.add(findMit);
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.MU_TABLE);
		menu.setMnemonic(TXT.MN_TABLE_PL);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(markMit);
		menu.add(markAllMit);
		menu.add(removeMit);
		menu.add(toTaskMit);
		menu.add(filterMit);
		menu.addSeparator();
		menu.add(menuTags);
		menu.add(menuSearch);
		
		// tool bar buttons
		
		openCloseBut = getButton(LangH.getResource(Texts.PH_ICON_BOOK),
				TXT.TIP_TABLE_PL, event -> openClose());
		
		openCloseTagBut = tags.getOpenCloseButton();
		
		openCloseSearchBut = getButton(LangH.getResource(Texts.PH_ICON_SEARCH), 
				TXT.TIP_SEARCH_PL, event -> openCloseSearch(search));
		
		// close panels
		
		tags.openClose();
		openCloseSearch(search);
	}

//
// Methods
//	
	
	@Override
	public void loadData(File file)
	{
		table.loadData(file);
		clearFilter();
	}
	
	/**
	 * Puts marks in check boxes in selected rows
	 */
	public void mark() 
	{
		table.mark();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows
	 */
	public void markAll() 
	{
		table.markAll();
	}
	
	@Override
	public void addAll(Collection <? extends Phrase> c)
	{
		table.addAll(c);
	}
	
	/**
	 * Removes marked rows.
	 */
	public void removeMarkedRows() 
	{
		table.removeMarkedRows();
	}
	
	/**
	 * Puts marked rows to the task list.
	 * @param worklist - an object to exchange data.
	 */
	public void toTask(AbstractFilteredTable<Integer> worklist)
	{
		table.toTask(worklist);
	}
	
	@Override
	public void addTags(Term tags) 
	{
		table.addTags(tags);
	}
	
	@Override
	public boolean getSound(AudioContainer toGet, int id)
	{
		return table.getSound(toGet, id);
	}
	
	@Override
	public void addSound(AudioContainer sound)
	{
		table.addSound(sound);
	}
	
	/**
	 * Gets the list of phrases with parts of texts specified in workspace fields. 
	 * Shows only phrases from the list in the table.
	 */
	public void showOnly() 
	{
		String[] s = search.getFields();
		table.applyFilter(s[1], s[2], s[3], s[4]);
		turnFilterButton(true);
	}
	
	@Override
	public void showOnly(Term tags) 
	{
		table.applyFilter(tags);
		this.turnFilterButton(true);
	}
	
	/**
	 * Removes all filters and shows all data.
	 */
	public void clearFilter() 
	{
		table.clearFilter();
		this.turnFilterButton(false);
	}
	
	@Override
	public int length()
	{
		return table.getModel().getRowCount();
	}
	
	@Override
	public File getPath()
	{
		return table.getPath();
	}

	@Override
	public void codeFile(File file)
	{
		table.codeFile(file);
	}
	
	@Override
	public JTable getTable()
	{
		return table;
	}
	
	@Override
	public Collection<Phrase> getAll() { return null;}
	
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
		return new JButton[] {openCloseBut, openCloseTagBut, openCloseSearchBut};
	}
	
	/**
	 * Opens and closes menu items of this menu.
	 */
	public void openClose() 
	{
		if (this.isVisible()) // close items
		{ 
			markMit.setEnabled(false);
			markAllMit.setEnabled(false);
			removeMit.setEnabled(false);
			toTaskMit.setEnabled(false);
			filterMit.setEnabled(false);
			
			openCloseBut.setBackground(LangH.getColors().getBasicBackground());
			openCloseTagBut.setEnabled(false);
			openCloseSearchBut.setEnabled(false);
				
			this.setVisible(false);
		}
		else  // open items
		{
			markMit.setEnabled(true);
			markAllMit.setEnabled(true);
			removeMit.setEnabled(true);
			toTaskMit.setEnabled(true);
			filterMit.setEnabled(true);
			
			openCloseBut.setBackground(LangH.getColors().getPushedButton());
			openCloseTagBut.setEnabled(true);
			openCloseSearchBut.setEnabled(true);
				
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}
	
	/**
	 * Opens and closes menu items of this menu
	 */
	private void openCloseSearch(JComponent search)
	{
		if (search.isVisible()) // close items
		{
			findMit.setEnabled(false);
			findBut.setEnabled(false);
			openCloseSearchBut.setBackground(LangH.getColors().getBasicBackground());
			search.setVisible(false);
			
		}
		else // open items
		{
			findMit.setEnabled(true);
			findBut.setEnabled(true);
			openCloseSearchBut.setBackground(LangH.getColors().getPushedButton());
			search.setVisible(true);
		}
	}
	
	/**
	 * Changes background of the filter button
	 * @param turnOn - if <code>true</code> background is pushed
	 */
	private void turnFilterButton(boolean turnOn) 
	{
		if (turnOn)
		{
			filterBut.setBackground(LangH.getColors().getPushedButton());
		}
		else 
		{
			filterBut.setBackground(LangH.getColors().getBasicBackground());
		}
	}
	
	/**
	 * Returns a new <code>JLabel</code> object
	 * @param text - a text for this object
	 * @return a new <code>JLabel</code> object
	 */
	private JLabel getLabel(String text) 
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setFont(LangH.getFonts().getFontBold());
		return label;
	}
	
	/**
	 * Returns a new <code>JButton</code> object with specified parameters
	 * @param labelPath - a path of label for this object
	 * @param tip - a tip text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(String labelPath, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(new ImageIcon(LangH.class.getResource(labelPath)));
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}
	
	/**
	 * Returns a new <code>JButton</code> object with specified parameters
	 * @param icon - an icon for this object
	 * @param tip - a tip text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(ImageIcon icon, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}
	
	/**
	 * Returns a new <code>JToolBar</code> object
	 * @param components - components for this object
	 * @return a new <code>JToolBar</code> object
	 */
	private JToolBar getToolBar(Component... components) 
	{
		JToolBar bar = new JToolBar();
		bar.setFloatable(false);
		for (Component each : components)
		{
			bar.add(each);
		}
		return bar;
	}
	
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
	
} // end Dictionary