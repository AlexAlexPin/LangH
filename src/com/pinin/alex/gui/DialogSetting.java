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
import javax.swing.border.*;
import com.pinin.alex.*;
import com.pinin.alex.main.*;

/**
 * Extends <code>JDialog</code> and contains settings of this program. After changing
 * of settings this class puts information into the global preferences. All changes
 * will be able after restart. Object is modeless.
 */
class DialogSetting extends JDialog
{
    // control buttons
    private JRadioButton engLang;
    private JRadioButton rusLang;
    private JRadioButton normalFont;
    private JRadioButton bigFont;
    private JRadioButton hugeFont;

	// other
	private static final long serialVersionUID = 1L;
	
	// common mains
    private FontsRepo fontsRepo;

	/**
	 * Constructor
	 * @param owner - the <code>Frame</code> from which the dialog is displayed
	 * @param title - the <code>String</code> to display in the dialog's title bar
	 * @param dataFactory - a common data factory
	 */
	DialogSetting(JFrame owner, String title, CommonDataFactory dataFactory)
	{	
		super(owner, title, true);

        fontsRepo = dataFactory.getFontsRepo();
        TextsRepo textsRepo = dataFactory.getTextsRepo();

		// languages

		engLang = getRadioButton(textsRepo.BT_SET_LANG_ENG);
		rusLang = getRadioButton(textsRepo.BT_SET_LANG_RUS);
		makeRadioButtonGroup(engLang, rusLang);

		String language = dataFactory.getPrefFacade().getLanguage();
		if (language.equals(TextsRepo.PH_TEXT_RUS)) rusLang.setSelected(true);

		Font fontP = dataFactory.getFontsRepo().getFontPlate();
			
		JPanel langPanel = getPanel(new GridLayout(2,1), engLang, rusLang);		
		langPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				textsRepo.LB_HEADER_SET_LANG, TitledBorder.LEFT, TitledBorder.TOP, fontP));
			
		// font sizes

		normalFont = getRadioButton(textsRepo.BT_SET_FSIZE_NORM);
		bigFont    = getRadioButton(textsRepo.BT_SET_FSIZE_BIG);
		hugeFont   = getRadioButton(textsRepo.BT_SET_FSIZE_HUGE);
		makeRadioButtonGroup(normalFont, bigFont, hugeFont);
			
		int fontSize = dataFactory.getPrefFacade().getFontSize();
		if (fontSize == FontsRepo.FONT_BIG)  bigFont.setSelected(true);
		if (fontSize == FontsRepo.FONT_HUGE) hugeFont.setSelected(true);
			
		JPanel fontPanel = this.getPanel(new GridLayout(3,1), normalFont, bigFont, hugeFont);
		fontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				textsRepo.LB_HEADER_SET_FSIZE, TitledBorder.LEFT, TitledBorder.TOP, fontP));
			
		// main panel
				
		JPanel mainPl = this.getPanel(new GridLayout(1,2), langPanel, fontPanel);
		mainPl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
				textsRepo.MG_SET_REST_TO_APL, TitledBorder.CENTER, TitledBorder.BOTTOM, fontP));
		
		// menuItem
				
		JButton button = getButton(textsRepo.BT_SET_OK_BT, event -> applySettings(dataFactory));
				
		// add elements
		
		int width  = fontSize*30;
		int height = fontSize*18;
		this.setSize(width, height);
				
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
				
		this.add(mainPl,                             BorderLayout.CENTER);
		this.add(getPanel(new FlowLayout(), button), BorderLayout.SOUTH);
	}

	private void applySettings(CommonDataFactory dataFactory)
	{
		if (rusLang.isSelected()) dataFactory.getPrefFacade().putLanguage(TextsRepo.PH_TEXT_RUS);
		if (engLang.isSelected()) dataFactory.getPrefFacade().putLanguage(TextsRepo.PH_TEXT);
			
		if (normalFont.isSelected()) dataFactory.getPrefFacade().putFontSize(FontsRepo.FONT_NORMAL);
		if (bigFont   .isSelected()) dataFactory.getPrefFacade().putFontSize(FontsRepo.FONT_BIG);
		if (hugeFont  .isSelected()) dataFactory.getPrefFacade().putFontSize(FontsRepo.FONT_HUGE);
			
		this.setVisible(false);
	}

	private JButton getButton(String text, ActionListener action) 
	{
		JButton button = new JButton(text);
		button.setFont(fontsRepo.getFontPlate());
		button.addActionListener(action);
		return button;
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

	private JRadioButton getRadioButton(String text)
	{
		JRadioButton button = new JRadioButton(text);
		button.setFont(fontsRepo.getFontPlate());
		return button;
	}

	private void makeRadioButtonGroup(JRadioButton... buttons)
	{
		ButtonGroup grout = new ButtonGroup();
		for (JRadioButton each : buttons)
		{
			grout.add(each);
		}
		buttons[0].setSelected(true);
	}
}
