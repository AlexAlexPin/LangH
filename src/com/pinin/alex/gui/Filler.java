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
import java.util.LinkedList;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Allows to add new phrases to the database.
 */
class Filler extends AbstractControlledPanel
{
	// elements of this panel
	private JTextArea addPhrase;
	private JTextArea addTransl;
	private JTextArea addComment;
	private JTextArea addTag;
	
	// elements of this menu
	private JMenu menu;
	private JMenuItem addMit;
	
	// tool bar buttons
	private JButton openCloseBut;
	
	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
	private TextsRepo textsRepo;
	private FontsRepo fontsRepo;
    private BordersRepo bordersRepo;
    private ColorsRepo colorsRepo;

	/**
	 * Constructor.
	 * @param dic - an object to exchange data.
     * @param dataFactory - a common data factory.
	 */
	Filler(DictionaryTable dic, CommonDataFactory dataFactory)
	{
        textsRepo = dataFactory.getTextsRepo();
        fontsRepo = dataFactory.getFontsRepo();
        bordersRepo = dataFactory.getBordersRepo();
        colorsRepo = dataFactory.getColorsRepo();

		// workspace
			
		addPhrase  = getTextArea(textsRepo.LB_HEADER_ADD_PHRASE);
		addTransl  = getTextArea(textsRepo.LB_HEADER_ADD_TRANSL);
		addComment = getTextArea(textsRepo.LB_HEADER_ADD_COMM);
		addTag     = getTextArea(textsRepo.LB_HEADER_ADD_TAG);
			
		JPanel workspace = getPanel(new GridLayout(1,4), new JScrollPane(addPhrase), 
				new JScrollPane(addTransl), new JScrollPane(addComment), new JScrollPane(addTag));
			
		// buttons
			
		JButton addBut = getButton(
		        dataFactory.getIconFromResource(TextsRepo.PH_ICON_ADDNEW), textsRepo.TIP_ADD, event -> add(dic));
			
		JToolBar buttons = getToolBar(addBut);
			
		// head panel
			
		JPanel headPanel = new JPanel(new BorderLayout());
		headPanel.add(buttons, BorderLayout.WEST);
			
		// add elements
			
		this.setBorder(bordersRepo.getPanelBorder());
		this.setLayout(new BorderLayout());
			
		this.add(headPanel,    BorderLayout.NORTH);
		this.add(workspace,    BorderLayout.CENTER);

		this.setToolTipText(textsRepo.TIP_ADD_HELP);
		
		// menu items
		
		JMenuItem openCloseMit = this.getMenuItem(textsRepo.BT_SHOW_HIDE_PL, textsRepo.HK_ADD_PL, event -> openClose());
		openCloseMit.setIcon(dataFactory.getIconFromResource(TextsRepo.PH_ICON_ADD));
		
		addMit = this.getMenuItem(textsRepo.BT_NEW_ADD_PL, textsRepo.HK_NEW_ADD_PL, event -> add(dic));
		
		// menu
		
		menu = new JMenu();
		menu.setText(textsRepo.MU_ADD);
		menu.setMnemonic(textsRepo.MN_ADD_PL);
		menu.setFont(fontsRepo.getFontPlate());
		
		menu.add(openCloseMit);
		menu.addSeparator();
		menu.add(addMit);
		
		// tool buttons
		
		openCloseBut = getButton(dataFactory.getIconFromResource(TextsRepo.PH_ICON_ADD), textsRepo.TIP_ADD_PL, event -> openClose());
	}
	
//
// Methods
//
	
	/**
	 * Adds new phrases to the database.<br>
	 * Allows to add a few phrases and translates separated by the newline character.
	 */
	public void add(DictionaryTable dic) 
	{
		// get values at all
		
		String phraseText  = addPhrase.getText();
		String translText  = addTransl.getText();
		String commentText = addComment.getText();
		String tagText     = addTag.getText();
				
		// split values by line
		
		String[] phraseSplit  = phraseText.split("\n");
		String[] translSplit  = translText.split("\n");
		String[] commentSplit = commentText.split("\n");
		String[] tagSplit     = tagText.split("\n");
				
		// check are fields empty
		
		boolean noTransl = false;
		if (translText.length() == 0) noTransl = true;
				
		boolean noComment = false;
		if (commentText.length() == 0) noComment = true;
				
		boolean noTag = false;
		if (tagText.length() == 0) noTag = true;
				
		// check if numbers of lines are equally
		
		if ( (phraseSplit.length != translSplit .length && !noTransl)  || 
			 (phraseSplit.length != commentSplit.length && !noComment) ||
			 (phraseSplit.length != tagSplit    .length && !noTag)     )
		{
			GUI.showErrorDialog(textsRepo.MG_ERROR_ADD, textsRepo.TL_ERROR);
			return;
		}
			
		// add data to the database
		
		int sizeBefore = dic.length();
		
		LinkedList<PhraseSet> phrases = new LinkedList<>();
		for (int i=0; i<phraseSplit.length; i++) 
		{
			final PhraseSet p = new PhraseSet(
				0,
				phraseSplit[i],
				noTransl  ? new Term() : new Term().parse(translSplit[i] , ";"),
				noComment ? new Term() : new Term().parse(commentSplit[i], ";"),
				noTag     ? new Term() : new Term().parse(tagSplit[i]    , ";") );
			
			phrases.add(p);
		}
		dic.addAll(phrases);
		
		int sizeAfter = dic.length();
				
		if (sizeBefore == sizeAfter) return;
				
		addPhrase.setText("");
		addTransl.setText("");
		addComment.setText("");
		addTag.setText("");	
	}
	
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
		return new JButton[] {openCloseBut};
	}
	
	/**
	 * Opens and closes menu items of this menu.
	 */
	public void openClose() 
	{
		if (this.isVisible()) // close items
		{
			addMit.setEnabled(false);
			openCloseBut.setBackground(colorsRepo.getBasicBackground());
			this.setVisible(false);
		}
		else // open items
		{
			addMit.setEnabled(true);
			openCloseBut.setBackground(colorsRepo.getPushedButton());
			this.setVisible(true);
		}
		GUI.infoPanelVisibility();
	}

	private JPanel getPanel(LayoutManager mgr, JComponent... components) 
	{
		JPanel panel = new JPanel();
		panel.setLayout(mgr);
		for (JComponent each : components)
		{
			panel.add(each);
		}
		return panel;
	}
	
	/**
	 * Returns a new <code>JTextArea</code> object
	 * @param header - a header in the border for this object
	 * @return a new <code>JTextArea</code> object
	 */
	private JTextArea getTextArea(String header) 
	{
		JTextArea textArea = new JTextArea();
		textArea.setFont(fontsRepo.getFontPlate());
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(bordersRepo.getInTextBorder(header));
		return textArea;
	}

//	private JButton getButton(String labelPath, String tip, ActionListener action)
//	{
//		JButton button = new JButton();
//		button.setIcon(CommonDataFactoryImpl.getIconFromResource(labelPath));
//		button.setToolTipText(tip);
//		button.addActionListener(action);
//		return button;
//	}

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
	
} // end Filler
