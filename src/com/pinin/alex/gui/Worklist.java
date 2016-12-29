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

import com.pinin.alex.CommonDataFactory;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>.Contains only part of phrases from the main table.
 */
public class Worklist extends AbstractFilteredTable<Integer>
{
	// The table for this panel.
	private TablePhrasesFiltered table;
	
	// elements of this menu
	private JMenu menu;
	private JMenuItem markMit;
	private JMenuItem markAllMit;
	private JMenuItem removeMit;
	
	// tool bar buttons
	private JButton openCloseBut;
	
	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
	private FontsRepo fontsRepo;
    private ColorsRepo colorsRepo;

	/**
	 * Constructor.
	 * @param dic - an object to exchange data.
     * @param dataFactory - a common data factory,
	 */
	public Worklist(DictionaryTable dic, CommonDataFactory dataFactory)
	{
        fontsRepo = dataFactory.getFontsRepo();
        colorsRepo = dataFactory.getColorsRepo();
        TextsRepo textsRepo = dataFactory.getTextsRepo();

		// header
			
		JLabel header = getLabel(textsRepo.LB_HEADER_EXER_PL);
			
		// buttons
			
		JButton markBut    = getButton(TextsRepo.PH_ICON_SELECT,  textsRepo.TIP_SELECT, event -> mark());
		JButton markAllBut = getButton(TextsRepo.PH_ICON_SEL_ALL, textsRepo.TIP_SEL_ALL, event -> markAll());
		JButton removeBut  = getButton(TextsRepo.PH_ICON_DELETE,  textsRepo.TIP_DELETE, event -> remove());
			
		JToolBar buttons = getToolBar(markBut, markAllBut, removeBut);
			
		// table
			
		table = new TablePhrasesFiltered(dic.getTable().getModel(), dataFactory);
			
		// add elements
			
		this.setBorder(dataFactory.getBordersRepo().getPanelBorder());
		this.setLayout(new GridBagLayout());
			
		this.add(buttons,                new GBC(0, 0, 1, 1).setWeight(0,   0  ).setAnchor(GBC.WEST));
		this.add(header,                 new GBC(1, 0, 1, 1).setWeight(100, 0  ).setFill(GBC.HORIZONTAL));		
		this.add(new JScrollPane(table), new GBC(0, 1, 2, 1).setWeight(100, 100).setFill(GBC.BOTH));
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_TASK_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_LIST));
		
		markMit    = getMenuItem(textsRepo.BT_SELECT_TASK_PL,  textsRepo.HK_SELECT_TASK_PL, event -> mark());
		markAllMit = getMenuItem(textsRepo.BT_SEL_ALL_TASK_PL, textsRepo.HK_SEL_ALL_TASK_PL, event -> markAll());
		removeMit  = getMenuItem(textsRepo.BT_DELETE_TASK_PL,  textsRepo.HK_DELETE_TASK_PL, event -> remove());
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_TASK);
		menu.setMnemonic(textsRepo.MN_TASK_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(markMit);
		menu.add(markAllMit);
		menu.add(removeMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_LIST), textsRepo.TIP_TASK_PL, event -> openClose());
	}

//
// Methods
//
	
	@Override
	public void loadData(File file)
	{
		table.loadData(file);
	}

	private void mark()
	{
		table.mark();
	}

    private void markAll()
	{
		table.markAll();
	}
	
	/**
	 * Removes rows with marks in check boxes
	 */
	public void remove() 
	{
		table.removeMarkedRows();
	}
	
	/**
	 * Ads new elements. 
	 * @param ids - a list of IDs to add.
	 */
	public void addAllFiltered(Collection<? extends Integer> ids) 
	{
		table.addAll(ids);
	}
	
	@Override
	public Collection<PhraseSet> getAll()
	{
		return table.getAll();
	}
	
	@Override
	public void addAll(Collection <? extends PhraseSet> c) {}
	
	@Override
	public int length()
	{
		return table.getRowCount();
	}
	
	@Override
	public void codeFile(File file)
	{
		table.codeFile(file);	
	}
	
	@Override
	public boolean getSound(AudioContainer toGet, int id) 
	{ 
		return table.getSound(toGet, id);
	}
	
	@Override
	public File getPath() { return null; }
	
	@Override
	public void showOnly(Term tags) {}

	@Override
	public void addTags(Term tags) {}

	@Override
	public void addSound(AudioContainer sound) {}

	@Override
	public JTable getTable() { return null; }
	
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
	public JButton[] getButtons()
	{
		return new JButton[] {openCloseBut};
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
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
				
			this.setVisible(false);
		}
		else // open items
		{
			markMit.setEnabled(true);
			markAllMit.setEnabled(true);
			removeMit.setEnabled(true);
			openCloseBut.setBackground(colorsRepo.getPushedButton());
				
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}
	
	/**
	 * Returns a new <code>JLabel</code> object
	 * @param text - a text for this object
	 * @return a new <code>JLabel</code> object
	 */
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
	
} // end Worklist
