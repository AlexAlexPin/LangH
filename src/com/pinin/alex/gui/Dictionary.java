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
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Displays the whole phrases list.
 */
public class Dictionary extends AbstractDictionaryTable
{
	// elements of this panel
	private Search search;
	private TablePhrases table;
	private JButton filterBut;
	private JButton findBut;
	
	// elements of this menu
	private JMenu menu;
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
	private static final long serialVersionUID = 1L;
	
	// common mains
	private ColorsRepo colorsRepo;
	private FontsRepo fontsRepo;
	
//
// Constructor
//
	
	/**
	 * Constructor.
	 */ 
	public Dictionary(CommonDataFactory dataFactory)
	{
		TextsRepo textsRepo = dataFactory.getTextsRepo();
		colorsRepo = dataFactory.getColorsRepo();
		fontsRepo = dataFactory.getFontsRepo();

		Tags tags = new Tags(this, dataFactory);
		search = new Search(dataFactory);
		
		// header
			
		JLabel header = this.getLabel(textsRepo.LB_HEADER_TABLE_PL);
			
		// buttons
			
		JButton mark    = getButton(TextsRepo.PH_ICON_SELECT,  textsRepo.TIP_SELECT, event -> mark());
		JButton markAll = getButton(TextsRepo.PH_ICON_SEL_ALL, textsRepo.TIP_SEL_ALL, event -> markAll());
		JButton remove  = getButton(TextsRepo.PH_ICON_DELETE,  textsRepo.TIP_DELETE, event -> removeMarkedRows());
		JButton toTask  = getButton(TextsRepo.PH_ICON_TOTASK,  textsRepo.TIP_TOTASK, event -> toTask(GUI.getWorklist()));
		filterBut       = getButton(TextsRepo.PH_ICON_FILTER,  textsRepo.TIP_CLEAR_FILTER, event -> clearFilter());
		findBut         = getButton(TextsRepo.PH_ICON_SEARCH,  textsRepo.TIP_FIND, event -> showOnly());
			
		JToolBar buttons = getToolBar(mark, markAll, remove, toTask, filterBut, findBut);
			
		// table
			
		table = new TablePhrases(tags, GUI.getWorklist(), dataFactory);
		
		// add elements
			
		this.setBorder(dataFactory.getBordersRepo().getPanelBorder());
		this.setLayout(new GridBagLayout());
					
		this.add(buttons,                new GBC(0, 0, 1, 1).setWeight(0,   0  ).setAnchor(GBC.WEST));
		this.add(header,                 new GBC(1, 0, 1, 1).setWeight(100, 0  ).setFill(GBC.HORIZONTAL));
		this.add(tags,                   new GBC(2, 0, 1, 3).setWeight(30,  100).setFill(GBC.BOTH));
		this.add(search,                 new GBC(0, 1, 2, 1).setWeight(100, 10 ).setFill(GBC.BOTH));
		this.add(new JScrollPane(table), new GBC(0, 2, 2, 1).setWeight(100, 100).setFill(GBC.BOTH));
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_ADD_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_BOOK));
		
		markMit    = getMenuItem(textsRepo.BT_SELECT_TABLE_PL,  textsRepo.HK_SELECT_TABLE_PL, event -> mark());
		markAllMit = getMenuItem(textsRepo.BT_SEL_ALL_TABLE_PL, textsRepo.HK_SEL_ALL_TABLE_PL, event -> markAll());
		removeMit  = getMenuItem(textsRepo.BT_DELETE_TABLE_PL,  textsRepo.HK_DELETE_TABLE_PL, event -> removeMarkedRows());
		toTaskMit  = getMenuItem(textsRepo.BT_TO_TASK_TABLE_PL, textsRepo.HK_TO_TASK_TABLE_PL, event -> toTask(GUI.getWorklist()));
		filterMit  = getMenuItem(textsRepo.BT_FILTER_TABLE_PL,  textsRepo.HK_TO_TASK_TABLE_PL, event -> clearFilter());
		
		// menu tags

		JMenu menuTags = tags.getMenu();
		
		// menu search
		
		JMenuItem openCloseSearchMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_SEARCH_PL,
				event -> openCloseSearch(search));
		openCloseSearchMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SEARCH));
		
		findMit = getMenuItem(textsRepo.BT_FIND_SEARCH_PL,   textsRepo.BT_FIND_SEARCH_PL, event -> showOnly());

		JMenu menuSearch = new JMenu();
		menuSearch.setText(textsRepo.BT_SEARCH_PL);
		menuSearch.setFont(fontsRepo.getFontPlate());
		menuSearch.add(openCloseSearchMit);
		menuSearch.add(findMit);
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_TABLE);
		menu.setMnemonic(textsRepo.MN_TABLE_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
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
		
		openCloseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_BOOK),
				textsRepo.TIP_TABLE_PL, event -> openClose());
		
		openCloseTagBut = tags.getOpenCloseButton();
		
		openCloseSearchBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SEARCH),
				textsRepo.TIP_SEARCH_PL, event -> openCloseSearch(search));
		
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
	private void mark()
	{
		table.mark();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows
	 */
	private void markAll()
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
	private void removeMarkedRows()
	{
		table.removeMarkedRows();
	}
	
	/**
	 * Puts marked rows to the task list.
	 * @param workList - an object to exchange data.
	 */
	private void toTask(AbstractFilteredTable<Integer> workList)
	{
		table.toTask(workList);
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
	private void showOnly()
	{
		String[] s = search.getFields();
		table.applyFilter(s[1], s[2], s[3], s[4]);
		turnFilterButtonBackground(true);
	}
	
	@Override
	public void showOnly(Term tags) 
	{
		table.applyFilter(tags);
		this.turnFilterButtonBackground(true);
	}

	private void clearFilter()
	{
		table.clearFilter();
		this.turnFilterButtonBackground(false);
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

	public JMenu getMenu()
	{
		return menu;
	}

	public JButton[] getToolBarButtons()
	{
		return new JButton[] {openCloseBut, openCloseTagBut, openCloseSearchBut};
	}

	public void openClose() 
	{
		if (this.isVisible()) // close items
		{ 
			markMit.setEnabled(false);
			markAllMit.setEnabled(false);
			removeMit.setEnabled(false);
			toTaskMit.setEnabled(false);
			filterMit.setEnabled(false);
			
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
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
			
			openCloseBut.setBackground(colorsRepo.getPushedButton());
			openCloseTagBut.setEnabled(true);
			openCloseSearchBut.setEnabled(true);
				
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}

	private void openCloseSearch(JComponent search)
	{
		if (search.isVisible()) // close items
		{
			findMit.setEnabled(false);
			findBut.setEnabled(false);
			openCloseSearchBut.setBackground(colorsRepo.getBasicBackground());
			search.setVisible(false);
			
		}
		else // open items
		{
			findMit.setEnabled(true);
			findBut.setEnabled(true);
			openCloseSearchBut.setBackground(colorsRepo.getPushedButton());
			search.setVisible(true);
		}
	}

	private void turnFilterButtonBackground(boolean turnOn)
	{
		if (turnOn)
		{
			filterBut.setBackground(colorsRepo.getPushedButton());
		}
		else 
		{
			filterBut.setBackground(colorsRepo.getBasicBackground());
		}
	}

	private JLabel getLabel(String text) 
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setFont(fontsRepo.getFontBold());
		return label;
	}

	private JButton getButton(String labelPath, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(new ImageIcon(LangH.class.getResource(labelPath)));
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}

	private JButton getButton(ImageIcon icon, String tip, ActionListener action) 
	{
		JButton button = new JButton();
		button.setIcon(icon);
		button.setToolTipText(tip);
		button.addActionListener(action);
		return button;
	}

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

	private JMenuItem getMenuItem(String text, String key_Komb, ActionListener action) 
	{
		JMenuItem item = new JMenuItem(text);
		item.setFont(fontsRepo.getFontPlate());
		item.setAccelerator(KeyStroke.getKeyStroke(key_Komb));
		item.addActionListener(action);
		return item;
	}
}
