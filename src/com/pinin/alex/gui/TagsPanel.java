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
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Displays the tag list.
 */
public class TagsPanel extends JPanel implements TagContainer
{
	// the table for this panel
	private TableTerm table;
	
	// elements of this menu
	private JMenu menu;
	private JMenuItem markMit;
	private JMenuItem markAllMit;
	private JMenuItem findMit;
	private JMenuItem toPhraseMit;
	
	// menu bar buttons
	private JButton openCloseBut;
	
	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
    private FontsRepo fontsRepo;
    private ColorsRepo colorsRepo;

	/**
	 * Constructor.
	 * @param dictionary - an object to exchange data.
	 * @param dataFactory - a common data factory;
	 */
	public TagsPanel(DictionaryTable dictionary, CommonDataFactory dataFactory)
	{
        fontsRepo = dataFactory.getFontsRepo();
        colorsRepo = dataFactory.getColorsRepo();
        TextsRepo textsRepo = dataFactory.getTextsRepo();

		// header
			
		JLabel header = getLabel(textsRepo.LB_HEADER_TAG_PL);
			
		// buttons
			
		JButton markBut     = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SELECT),
				textsRepo.TIP_SELECT, event -> mark());
		JButton markAllBut  = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SEL_ALL),
				textsRepo.TIP_SEL_ALL, event -> markAll());
		JButton findBut     = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_SEARCH),
				textsRepo.TIP_FIND, event -> find(dictionary));
		JButton toPhraseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_TOPHR),
				textsRepo.TIP_TOPHR, event -> toPhrase(dictionary));
		
		JToolBar buttons = getToolBar(markBut, markAllBut, findBut, toPhraseBut);
			
		// table
		
		table = new TableTerm(dictionary, dataFactory);
			
		// add elements
			
		this.setLayout(new GridBagLayout());
			
		this.add(buttons,                new GBC(0, 0, 1, 1).setWeight(0,   0  ).setAnchor(GBC.WEST));
		this.add(header,                 new GBC(1, 0, 1, 1).setWeight(100, 0  ).setFill(GBC.HORIZONTAL));		
		this.add(new JScrollPane(table), new GBC(0, 1, 2, 1).setWeight(100, 100).setFill(GBC.BOTH));
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_TAG_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_TAG));
		
		markMit     = getMenuItem(textsRepo.BT_SELECT_TAG_PL,    textsRepo.HK_SELECT_TAG_PL, event -> mark());
		markAllMit  = getMenuItem(textsRepo.BT_SEL_ALL_TAG_PL,   textsRepo.HK_SEL_ALL_TAG_PL, event -> markAll());
		findMit     = getMenuItem(textsRepo.BT_FIND_TAG_PL,      textsRepo.HK_FIND_TAG_PL, event -> find(dictionary));
		toPhraseMit = getMenuItem(textsRepo.BT_TO_PHRASE_TAG_PL, textsRepo.HK_TO_PHRASE_TAG_PL, event -> toPhrase(dictionary));
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.BT_TAG_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(markMit);
		menu.add(markAllMit);
		menu.add(findMit);
		menu.add(toPhraseMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_TAG), textsRepo.TIP_TAG_PL, event -> openClose());
	}

	@Override
	public void loadData(Term term) 
	{
		table.loadData(term);
	}
	
	/**
	 * Puts marks in check boxes in selected rows.
	 */
    private void mark()
	{
		table.mark();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows.
	 */
    private void markAll()
	{
		table.markAll();
	}
	
	/**
	 * Gets selected tags. Shows only found phrases in the dictionary table.
	 * @param component - an object to exchange the data.
	 */
    private void find(DictionaryTable component)
	{
		table.find(component);
	}
	
	/**
	 * Adds selected tags to selected phrases.
	 * @param component - an object to exchange the data.
	 */
	private void toPhrase(DictionaryTable component)
	{
		table.toPhrase(component);
	}
	
	/**
	 * Returns the menu to operate this panel.
	 * @return the menu to operate this panel.
	 */
	public JMenu getMenu()
	{
		return menu;
	}
	
	JButton getOpenCloseButton()
	{
		return openCloseBut;
	}
	
	/**
	 * Opens and closes menu items of this menu
	 */
	public void openClose() 
	{
		if (this.isVisible()) // close items
		{
			markMit.setEnabled(false);
			markAllMit.setEnabled(false);
			findMit.setEnabled(false);
			toPhraseMit.setEnabled(false);
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
			
			this.setVisible(false);
		}
		else // open items
		{
			markMit.setEnabled(true);
			markAllMit.setEnabled(true);
			findMit.setEnabled(true);
			toPhraseMit.setEnabled(true);
			openCloseBut.setBackground(colorsRepo.getPushedButton());
				
			this.setVisible(true);
		}
	}

	private JLabel getLabel(String text) 
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setFont(fontsRepo.getFontBold());
		return label;
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
