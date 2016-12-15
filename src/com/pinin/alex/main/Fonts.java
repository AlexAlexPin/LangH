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
import com.pinin.alex.*;

/**
 * Contains main fonts.
 */
public class Fonts 
{
//
// Variables
//
	// fonts
	
	private Font fontPlate;	
	private Font fontBold;	
	private Font fontItalic;
	
	// font sizes
	
	public final static int FONT_NORMAL = 12;
	public final static int FONT_BIG    = 18;
	public final static int FONT_HUGE   = 24;
	
//
// Constructors
//

	/**
	 * Constructor.
	 */
	public Fonts() 
	{
		int fontSize = LangH.getData().getFontSize();
			
		fontPlate  = new Font(Font.DIALOG, Font.PLAIN,  fontSize);
		fontBold   = new Font(Font.DIALOG, Font.BOLD,   fontSize);
		fontItalic = new Font(Font.DIALOG, Font.ITALIC, fontSize);
	}
	
//
// Methods
//
	
	/**
	 * Returns the plate font
	 * @return the plate font
	 */
	public Font getFontPlate() 
	{
		return fontPlate;
	}
	
	/**
	 * Returns the bold font
	 * @return the bold font
	 */
	public Font getFontBold() 
	{
		return fontBold;
	}
	
	/**
	 * Returns the italic font
	 * @return the italic font
	 */
	public Font getFontItalic() 
	{
		return fontItalic;
	}
	
} // end Fonts
