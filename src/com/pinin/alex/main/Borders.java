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

package com.pinin.alex.main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Loads and contains borders.
 */
@SuppressWarnings("unused")
public class Borders
{
	// borders
	private Border panelBorder;
	private Border elementBorder;
	private Border fieldBorder;
	private Border labelBorder;
	private Border emptyBorder;
	private TitledBorder correctBorder;
	private TitledBorder wrongBorder;
	
	// common mains
	private Fonts fonts;
	
	/**
	 * Constructor.
	 * @param fonts - fonts for this application.
     * @param texts - texts for this application.
	 */
	public Borders(Fonts fonts, Texts texts)
	{
        this.fonts = fonts;

		panelBorder   = BorderFactory.createBevelBorder(0);
		elementBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		fieldBorder   = BorderFactory.createEtchedBorder();
		labelBorder   = BorderFactory.createEtchedBorder(1);
		emptyBorder   = BorderFactory.createEmptyBorder();
			
		Font fontP = fonts.getFontPlate();
			
		correctBorder = (BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 128, 0), 2), 
				texts.MG_CORRECT_ANSW, TitledBorder.CENTER, TitledBorder.TOP, fontP));
		correctBorder.setTitleColor(new Color(0, 128, 0));
			
		wrongBorder = (BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(255, 0, 0), 2), 
				texts.MG_WRONG_ANSW, TitledBorder.CENTER, TitledBorder.TOP, fontP));
		wrongBorder.setTitleColor(new Color(255, 0, 0));

        TitledBorder inTextBorder = (BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(),
				"", TitledBorder.CENTER, TitledBorder.TOP, fontP));
		inTextBorder.setTitleColor(Color.LIGHT_GRAY);
	}

	public Border getPanelBorder() 
	{
		return panelBorder;
	}

	public Border getElementBorder() 
	{
		return elementBorder;
	}

	public Border getFieldBorder() 
	{
		return fieldBorder;
	}

	public Border getLabelBorder() 
	{
		return labelBorder;
	}

	public Border getEmptyBorder() 
	{
		return emptyBorder;
	}

	public Border getCorrectBorder() 
	{
		return correctBorder;
	}

	public Border getWrongBorder() 
	{
		return wrongBorder;
	}

	public Border getInTextBorder(String text) 
	{
		TitledBorder border = (BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(),
				text, TitledBorder.CENTER, TitledBorder.TOP, fonts.getFontPlate()));
		border.setTitleColor(Color.LIGHT_GRAY);
		return border;
	}
}
