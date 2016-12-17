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
import javax.swing.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JDialog</code>. Shows about information.
 */
class DialogAbout extends JDialog
{
	private static final long serialVersionUID = 1L;

	private Fonts fonts;

	/**
	 * Constructor
	 * @param owner - the <code>Frame</code> from which the dialog is displayed.
	 * @param title - the <code>String</code> to display in the dialog's title bar.
	 * @param dataFactory - the common data factory object.
	 */
	DialogAbout(JFrame owner, String title, CommonDataFactory dataFactory)
	{
		super(owner, title, true);

		fonts = dataFactory.getFonts();
		
		JTextPane infoPane = getTextPane();
		infoPane.setText(dataFactory.getResourceContent(Texts.PH_INFO).toString());
		infoPane.setCaretPosition(0);
		add(new JScrollPane(infoPane));
			
		Dimension size = dataFactory.getData().getScreenSize();
		this.setSize(size.width/3, size.height/3);
		this.setLocationRelativeTo(null);
	}

	private JTextPane getTextPane() 
	{
		JTextPane textPane = new JTextPane();
		textPane.setFont(fonts.getFontPlate());
		textPane.setEditable(false);	
		return textPane;
	}
}
