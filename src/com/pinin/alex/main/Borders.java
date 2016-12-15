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

package com.pinin.alex.main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.pinin.alex.LangH;

/**
 * Loads and contains borders.
 */
public class Borders 
{
//
// Variables
//
	// borders
	
	private Border panelBorder;
	private Border elementBorder;
	private Border fieldBorder;
	private Border labelBorder;
	private Border emptyBorder;
	
	private TitledBorder correctBorder;
	private TitledBorder wrongBorder;
	private TitledBorder inTextBorder;
	
	// common mains
	
	private final Texts TXT = LangH.getTexts();
	
//
// Constructors
//
	
	/**
	 * Constructor.
	 */
	public Borders() 
	{
		panelBorder   = BorderFactory.createBevelBorder(0);
		elementBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		fieldBorder   = BorderFactory.createEtchedBorder();
		labelBorder   = BorderFactory.createEtchedBorder(1);
		emptyBorder   = BorderFactory.createEmptyBorder();
			
		Font fontP = LangH.getFonts().getFontPlate();
			
		correctBorder = (BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 128, 0), 2), 
				TXT.MG_CORRECT_ANSW, TitledBorder.CENTER, TitledBorder.TOP, fontP));
		correctBorder.setTitleColor(new Color(0, 128, 0));
			
		wrongBorder = (BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(255, 0, 0), 2), 
				TXT.MG_WRONG_ANSW, TitledBorder.CENTER, TitledBorder.TOP, fontP));
		wrongBorder.setTitleColor(new Color(255, 0, 0));

		inTextBorder = (BorderFactory.createTitledBorder( 
				BorderFactory.createEmptyBorder(),
				"", TitledBorder.CENTER, TitledBorder.TOP, fontP));
		inTextBorder.setTitleColor(Color.LIGHT_GRAY);
	}

//
// Methods
//
	
	/**
	 * Returns a border for panels.
	 * @return a border for panels.
	 */
	public Border getPanelBorder() 
	{
		return panelBorder;
	}
	
	/**
	 * Returns a border for elements.
	 * @return a border for elements.
	 */
	public Border getElementBorder() 
	{
		return elementBorder;
	}
	
	/**
	 * Returns a border for fields.
	 * @return a border for fields.
	 */
	public Border getFieldBorder() 
	{
		return fieldBorder;
	}
	
	/**
	 * Returns a border for labels.
	 * @return a border for labels.
	 */
	public Border getLabelBorder() 
	{
		return labelBorder;
	}
	
	
	/**
	 * Returns an empty border.
	 * @return an empty border.
	 */
	public Border getEmptyBorder() 
	{
		return emptyBorder;
	}
	
	/**
	 * Returns a border for correct answers.
	 * @return a border for correct answers.
	 */
	public Border getCorrectBorder() 
	{
		return correctBorder;
	}
	
	/**
	 * Returns a border for incorrect answers.
	 * @return a border for incorrect answers.
	 */
	public Border getWrongBorder() 
	{
		return wrongBorder;
	}
	
	/**
	 * Returns a border with the specified text inside.
	 * @return a border with the specified text inside.
	 */
	public Border getInTextBorder(String text) 
	{
		TitledBorder border = (BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(),
				text, TitledBorder.CENTER, TitledBorder.TOP, LangH.getFonts().getFontPlate()));
		border.setTitleColor(Color.LIGHT_GRAY);
		return border;
	}
	
} // end Borders
