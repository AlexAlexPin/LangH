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
import java.util.logging.*;
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JDialog</code>. Shows help information.
 */
class DialogHelp extends JDialog
{
	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
	private TextsRepo textsRepo;
	private Logger logger;
    private FontsRepo fontsRepo;

	/**
	 * Constructor
	 * @param owner - the <code>Frame</code> from which the dialog is displayed.
	 * @param title - the <code>String</code> to display in the dialog's title bar.
	 * @param dataFactory - a common data factory.
	 */
	DialogHelp(JFrame owner, String title, CommonDataFactory dataFactory)
	{
		super(owner, title, true);

        textsRepo = dataFactory.getTextsRepo();
        logger = dataFactory.getLogger();
        fontsRepo = dataFactory.getFontsRepo();
		
		final JTextArea message = getTextArea(textsRepo.MG_HELP_LINK);
			
		JButton openSite = getButton(textsRepo.URL_WEBSITE, event -> openSite(textsRepo.URL_SUPPORT));
			
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(message), BorderLayout.CENTER);
		this.add(openSite, BorderLayout.SOUTH);
			
		final Dimension size = dataFactory.getPrefFacade().getScreenSize();
		this.setSize(size.width/6, size.height/6);
		this.setLocationRelativeTo(null);
	}

	private void openSite(String url) 
	{
		try 
		{
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		}
		catch (Exception e) 
		{
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}

	private JButton getButton(String text, ActionListener action) 
	{
		JButton button = new JButton(text);
		button.addActionListener(action);
		button.setFont(fontsRepo.getFontPlate());
		return button;
	}

	private JTextArea getTextArea(String text) 
	{
		JTextArea textArea = new JTextArea();
		textArea.setText(text);
		textArea.setFont(fontsRepo.getFontPlate());
		textArea.setEditable(false);	
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		return textArea;
	}
}
