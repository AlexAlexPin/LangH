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

package com.pinin.alex;

import java.awt.*;
import java.io.*;
import java.util.logging.*;
import java.util.prefs.*;
import javax.swing.*;
import com.pinin.alex.gui.*;
import com.pinin.alex.main.*;

/**
 * This class starts this program
 */
public class LangH 
{
//
// Variables
//
	
	private static Logger logger;
	private static Preferences preferences;
	private static Data data;
	private static Texts texts;
	private static Fonts fonts;
	private static Colors colors;
	private static Borders borders;
	
//
// Methods
//

	/**
	 * The main method
	 */
	public static void main(String[] args) 
	{
		if (System.getProperty("java.util.logging.config.class") == null
				&& System.getProperty("java.util.logging.config.file") == null) 
		{
			logger = Logger.getLogger("com.pinin.alex.langh");
			
			try 
			{
				Preferences root = Preferences.userRoot();
				preferences = root.node("/com/pinin/alex/langh");
				
				data = new Data();
				texts = new Texts();
				fonts = new Fonts();
				colors = new Colors();
				borders = new Borders();
				
				String PATTERN = Common.getLogFolder("LangH");
				int FILE_SIZE_LIMIT = 50000;
				int LOG_ROTATION_COUNT = 1;
				boolean APPEND = true;
				
				Handler handler = new FileHandler(PATTERN, FILE_SIZE_LIMIT, LOG_ROTATION_COUNT, APPEND);
				handler.setFormatter(Common.getFormatter());
					
				logger.addHandler(handler);
				logger.setLevel(Level.ALL);
				
				logger.log(Level.CONFIG, "Program has been started");
				
				EventQueue.invokeLater(new Runnable() 
				{
					public void run() 
					{	
						GUI gui = new GUI();
						gui.setVisible(true);
					}
				});
			}
			catch (RuntimeException e) 
			{
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			catch (IOException e) 
			{
				logger.log(Level.SEVERE, "Can not create log file handler", e);
			}
		}	
	}
	
	/**
	 * Returns preferences
	 * @return preferences
	 */
	public static Preferences getPreferences() 
	{
		return preferences;
	}
	
	/**
	 * Returns the logger
	 * @return the logger
	 */
	public static Logger getLogger() 
	{
		return logger;
	}
	
	/**
	 * Returns the texts
	 * @return the texts
	 */
	public static Texts getTexts() 
	{
		return texts;
	}
	
	/**
	 * Returns the data
	 * @return the data
	 */
	public static Data getData() 
	{
		return data;
	}
	
	/**
	 * Returns fonts
	 * @return fonts
	 */
	public static Fonts getFonts() 
	{
		return fonts;
	}
	
	/**
	 * Returns colors
	 * @return colors
	 */
	public static Colors getColors() 
	{
		return colors;
	}
	
	/**
	 * Returns borders
	 * @return borders
	 */
	public static Borders getBorders() 
	{
		return borders;
	}
	
	/**
	 * Returns the specified resource as an <code>ImageIcon</code>.
	 * @param resource - a path to get the resource.
	 * @return the specified resource as an <code>ImageIcon</code>.
	 */
	public static ImageIcon getResource(String resource)
	{
		return new ImageIcon(LangH.class.getResource(resource));
	}
	
	/**
	 * Returns the specified resource as an <code>InputStream</code>.
	 * @param resource - a path to get the resource.
	 * @return the specified resource as an <code>InputStream</code>.
	 */
	public static CharSequence getResourceContent(String resource)
	{
		return Common.getResourceContent(LangH.class, resource);
	}
	
} // end LangH