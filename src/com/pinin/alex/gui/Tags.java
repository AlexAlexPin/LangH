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
import javax.swing.*;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Displays the tag list.
 */
public class Tags extends JPanel implements TagContainer
{
//
// Variables
//
	
	// elements of this panel
	
	/** The table for this panel */
	private TableTerm table;
	
	// elements of this menu
	
	private JMenu menu;
	private JMenuItem markMit;
	private JMenuItem markAllMit;
	private JMenuItem findMit;
	private JMenuItem toPhraseMit;
	
	// tppl bar buttons
	
	private JButton openCloseBut;
	
	// other
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	
//
// Constructors
//
	
	/**
	 * Constructor.
	 * @param dictionary - an object to exchange data.
	 */
	public Tags(DictionaryTable dictionary) 
	{
		// header
			
		JLabel header = getLabel(TXT.LB_HEADER_TAG_PL);
			
		// buttons
			
		JButton markBut     = getButton(LangH.getResource(Texts.PH_ICON_SELECT),  
				TXT.TIP_SELECT,  event -> mark());
		JButton markAllBut  = getButton(LangH.getResource(Texts.PH_ICON_SEL_ALL),
				TXT.TIP_SEL_ALL, event -> markAll());
		JButton findBut     = getButton(LangH.getResource(Texts.PH_ICON_SEARCH),  
				TXT.TIP_FIND,    event -> find(dictionary));
		JButton toPhraseBut = getButton(LangH.getResource(Texts.PH_ICON_TOPHR),   
				TXT.TIP_TOPHR,   event -> toPhrase(dictionary));
		
		JToolBar buttons = getToolBar(markBut, markAllBut, findBut, toPhraseBut);
			
		// table
		
		table = new TableTerm(dictionary);
			
		// add elements
			
		this.setLayout(new GridBagLayout());
			
		this.add(buttons,                new GBC(0, 0, 1, 1).setWeight(0,   0  ).setAnchor(GBC.WEST));
		this.add(header,                 new GBC(1, 0, 1, 1).setWeight(100, 0  ).setFill(GBC.HORIZONTAL));		
		this.add(new JScrollPane(table), new GBC(0, 1, 2, 1).setWeight(100, 100).setFill(GBC.BOTH));
		
		// menu items
		
		JMenuItem openCloseMit = getMenuItem(TXT.BT_SHOW_HIDE_PL, TXT.HK_TAG_PL, event -> openClose());
		openCloseMit.setIcon(LangH.getResource(Texts.PH_ICON_TAG));
		
		markMit     = getMenuItem(TXT.BT_SELECT_TAG_PL,    TXT.HK_SELECT_TAG_PL,    event -> mark());
		markAllMit  = getMenuItem(TXT.BT_SEL_ALL_TAG_PL,   TXT.HK_SEL_ALL_TAG_PL,   event -> markAll());
		findMit     = getMenuItem(TXT.BT_FIND_TAG_PL,      TXT.HK_FIND_TAG_PL,      event -> find(dictionary));
		toPhraseMit = getMenuItem(TXT.BT_TO_PHRASE_TAG_PL, TXT.HK_TO_PHRASE_TAG_PL, event -> toPhrase(dictionary));
		
		// menu
		
		menu = new JMenu();
		menu.setText(TXT.BT_TAG_PL);
		menu.setFont(LangH.getFonts().getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(markMit);
		menu.add(markAllMit);
		menu.add(findMit);
		menu.add(toPhraseMit);
		
		// tool bar buttons
		
		openCloseBut = getButton(LangH.getResource(Texts.PH_ICON_TAG), TXT.TIP_TAG_PL, event -> openClose());
	}
	
//
// Methods
//	
	
	@Override
	public void loadData(Term term) 
	{
		table.loadData(term);
	}
	
	/**
	 * Puts marks in check boxes in selected rows.
	 */
	public void mark() 
	{
		table.mark();
	}
	
	/**
	 * Puts marks in check boxes in all visible rows.
	 */
	public void markAll() 
	{
		table.markAll();
	}
	
	/**
	 * Gets selected tags. Shows only found phrases in the dictionary table.
	 * @param component - an object to exchange the data.
	 */
	public void find(DictionaryTable component)  
	{
		table.find(component);
	}
	
	/**
	 * Adds selected tags to selected phrases.
	 * @param component - an object to exchange the data.
	 */
	public void toPhrase(DictionaryTable component) 
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
	
	public JButton getOpenCloseButton()
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
			openCloseBut.setBackground(LangH.getColors().getBasicBackground());
			
			this.setVisible(false);
		}
		else // open items
		{
			markMit.setEnabled(true);
			markAllMit.setEnabled(true);
			findMit.setEnabled(true);
			toPhraseMit.setEnabled(true);
			openCloseBut.setBackground(LangH.getColors().getPushedButton());
				
			this.setVisible(true);
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
	
} // end Tags
