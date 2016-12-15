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
import javax.swing.*;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>JPanel</code>. Allows to find phrases in the search panel.
 */
public class Search extends JPanel implements PhraseFieldsContainer
{
	
//
// Variables
//
	
	// elements of this panel
	
	private JTextArea findPhrase;
	private JTextArea findTransl;
	private JTextArea findComment;
	private JTextArea findTag;
	
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
	 */
	public Search() 
	{
		// label
			
		JLabel label = getLabel("  " + TXT.LB_HEADER_SEARCH_PL + "  ");
			
		// workspace
			
		findPhrase   = getTextArea(TXT.LB_COL_PHRASE);
		findTransl   = getTextArea(TXT.LB_COL_TRANSL);
		findComment  = getTextArea(TXT.LB_COL_COMMENT);
		findTag      = getTextArea(TXT.LB_COL_TAG);
			
		JPanel workspace = getPanel(new GridLayout(1,4), new JScrollPane(findPhrase), 
				new JScrollPane(findTransl), new JScrollPane(findComment), new JScrollPane(findTag));
			
		// add elements
			
		this.setLayout(new BorderLayout());
		this.add(label,     BorderLayout.WEST);
		this.add(workspace, BorderLayout.CENTER);
	}	
	
//
// Methods
//
	
	@Override
	public String[] getFields()
	{
		String[] result = new String[5];
		result[0] = findPhrase.getText();
		result[1] = findPhrase.getText();
		result[2] = findTransl.getText();
		result[3] = findComment.getText();
		result[4] = findTag.getText();
		return result;
	}
	
	/**
	 * Returns a new <code>JPanel</code> object
	 * @param mgr - a layout manager for this object
	 * @param components - components for this object
	 * @return a new <code>JPanel</code> object
	 */
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
	 * @return a new <code>JTextArea</code> object
	 */
	private JTextArea getTextArea(String tip)
	{
		JTextArea textArea = new JTextArea();
		textArea.setFont(LangH.getFonts().getFontPlate());
		textArea.setToolTipText(tip);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
		return textArea;
	}
	
	/**
	 * Returns a new <code>JLabel</code> object
	 * @param text - a text for this object
	 * @return a new <code>JLabel</code> object
	 */
	private JLabel getLabel(String text) 
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setFont(LangH.getFonts().getFontPlate());
		return label;
	}
	
} // end Search
