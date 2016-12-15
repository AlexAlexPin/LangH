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
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JDialog</code>. Shows help information.
 */
public class DialogHelp extends JDialog 
{
//
// Variables
//
	
	// other
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	private final Logger LOGGER = LangH.getLogger();
	
//
// Constructors
//
	
	/**
	 * Constructor
	 * @param owner - the <code>Frame</code> from which the dialog is displayed
	 * @param title - the <code>String</code> to display in the dialog's title bar
	 */
	public DialogHelp(JFrame owner, String title) 
	{
		super(owner, title, true);
		
		final JTextArea message = getTextArea(TXT.MG_HELP_LINK);
			
		JButton openSite = getButton(TXT.URL_WEBSITE, event -> openSite(TXT.URL_SUPPORT));
			
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(message), BorderLayout.CENTER);
		this.add(openSite, BorderLayout.SOUTH);
			
		final Dimension size = LangH.getData().getScreenSize();
		this.setSize(size.width/6, size.height/6);
		this.setLocationRelativeTo(null);
	}
	
//
// Methods
//
	
	/**
	 * opens the specified url address
	 * @param url - an address to be opened
	 */
	private void openSite(String url) 
	{
		try 
		{
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Returns a new <code>JButton</code> object with specified parameters
	 * @param text - a text for this object
	 * @param action - an action for this object
	 */
	private JButton getButton(String text, ActionListener action) 
	{
		JButton button = new JButton(text);
		button.addActionListener(action);
		button.setFont(LangH.getFonts().getFontPlate());
		return button;
	}

	/**
	 * Returns a new <code>JTextArea</code> object
	 * @param text - a text for this object
	 * @return a new <code>JTextArea</code> object
	 */
	private JTextArea getTextArea(String text) 
	{
		JTextArea textArea = new JTextArea();
		textArea.setText(text);
		textArea.setFont(LangH.getFonts().getFontPlate());
		textArea.setEditable(false);	
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		return textArea;
	}
	
} // end DialogHelp
