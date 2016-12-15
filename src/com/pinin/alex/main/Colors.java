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

/**
 * Loads and contains colors.
 */
public class Colors 
{
//
// Variables
//
	// colors
	
	private Color basicBackground;
	private Color pushedButton;
	
//
// Constructors
//	
	
	/**
	 * Constructor.
	 */
	public Colors() 
	{
		basicBackground = new JLabel().getBackground();
		pushedButton    = new Color(200, 238, 250);
	}
	
//
// Methods
//
	
	/**
	 * Returns a common background swing color.
	 * @return a common background swing color.
	 */
	public Color getBasicBackground() 
	{
		return basicBackground;
	}
	
	/**
	 * Returns a color of pushed button.
	 * @return a color of pushed button.
	 */
	public Color getPushedButton() 
	{
		return pushedButton;
	}
	
} // end Colors
