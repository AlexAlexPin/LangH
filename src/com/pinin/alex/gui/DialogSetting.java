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
import javax.swing.border.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JDialog</code> and contains settings of this program. After changing
 * of settings this class puts information into the global preferences. All changes
 * will be able after restart. Object is modeless.
 */
public class DialogSetting extends JDialog 
{
//
// Variables
//
	
	// elements of this dialog window
	
	JRadioButton engLang;
	JRadioButton rusLang;
	
	JRadioButton normalFont;
	JRadioButton bigFont;
	JRadioButton hugeFont;
	
	// other
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();

//
// Constructors
//
	
	/**
	 * Constructor
	 * @param owner - the <code>Frame</code> from which the dialog is displayed
	 * @param title - the <code>String</code> to display in the dialog's title bar
	 */
	public DialogSetting(JFrame owner, String title)
	{	
		super(owner, title, true);
		
		// languages
				
		engLang = getRadioButton(TXT.BT_SET_LANG_ENG);
		rusLang = getRadioButton(TXT.BT_SET_LANG_RUS);
		makeGroup(engLang, rusLang);

		String language = LangH.getData().getLanguage();
		if (language.equals(Texts.PH_TEXT_RUS)) rusLang.setSelected(true);

		Font fontP = LangH.getFonts().getFontPlate();
			
		JPanel langPanel = getPanel(new GridLayout(2,1), engLang, rusLang);		
		langPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				TXT.LB_HEADER_SET_LANG, TitledBorder.LEFT, TitledBorder.TOP, fontP));
			
		// font sizes
			
		normalFont = getRadioButton(TXT.BT_SET_FSIZE_NORM);
		bigFont    = getRadioButton(TXT.BT_SET_FSIZE_BIG);
		hugeFont   = getRadioButton(TXT.BT_SET_FSIZE_HUGE);
		makeGroup(normalFont, bigFont, hugeFont);
			
		int fontSize = LangH.getData().getFontSize();
		if (fontSize == Fonts.FONT_BIG)  bigFont.setSelected(true);
		if (fontSize == Fonts.FONT_HUGE) hugeFont.setSelected(true);
			
		JPanel fontPanel = this.getPanel(new GridLayout(3,1), normalFont, bigFont, hugeFont);
		fontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				TXT.LB_HEADER_SET_FSIZE, TitledBorder.LEFT, TitledBorder.TOP, fontP));
			
		// main panel
				
		JPanel mainPl = this.getPanel(new GridLayout(1,2), langPanel, fontPanel);
		mainPl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				TXT.MG_SET_REST_TO_APL, TitledBorder.CENTER, TitledBorder.BOTTOM, fontP));
		
		// button
				
		JButton button = getButton(TXT.BT_SET_OK_BT, event -> applySettings());
				
		// add elements
		
		int width  = fontSize*30;
		int height = fontSize*18;
		this.setSize(width, height);
				
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
				
		this.add(mainPl,                             BorderLayout.CENTER);
		this.add(getPanel(new FlowLayout(), button), BorderLayout.SOUTH);
	}
	
	/**
	 * Puts new setting to preferences
	 */
	private void applySettings() 
	{
		if (rusLang.isSelected()) LangH.getData().putLanguage(Texts.PH_TEXT_RUS);
		if (engLang.isSelected()) LangH.getData().putLanguage(Texts.PH_TEXT);
			
		if (normalFont.isSelected()) LangH.getData().putFontSize(Fonts.FONT_NORMAL);
		if (bigFont   .isSelected()) LangH.getData().putFontSize(Fonts.FONT_BIG);
		if (hugeFont  .isSelected()) LangH.getData().putFontSize(Fonts.FONT_HUGE);
			
		this.setVisible(false);
	}
	
	/**
	 * Returns a new <code>JButton</code> object
	 * @param text - a text for this object
	 * @param action - an action for this object
	 * @return a new <code>JButton</code> object
	 */
	private JButton getButton(String text, ActionListener action) 
	{
		JButton button = new JButton(text);
		button.setFont(LangH.getFonts().getFontPlate());
		button.addActionListener(action);
		return button;
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
	 * Returns a new <code>JRadioButton</code> object 
	 * @param text - a text for this object
	 * @return a new <code>JRadioButton</code> object 
	 */
	private JRadioButton getRadioButton(String text)
	{
		JRadioButton button = new JRadioButton(text);
		button.setFont(LangH.getFonts().getFontPlate());
		return button;
	}
	
	/**
	 * Makes a group of <code>JRadioButton</code> objects
	 * @param buttons - components for this object
	 */
	private void makeGroup(JRadioButton... buttons) 
	{
		ButtonGroup grout = new ButtonGroup();
		for (JRadioButton each : buttons)
		{
			grout.add(each);
		}
		buttons[0].setSelected(true);
	}
	
} // end DialogSetting