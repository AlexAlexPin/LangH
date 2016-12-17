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

package com.pinin.alex;

import java.awt.*;
import java.io.*;
import java.util.logging.*;

import com.pinin.alex.gui.*;

public class LangH 
{
	public static void main(String[] args) throws IOException {

		if (System.getProperty("java.util.logging.config.class") == null
				&& System.getProperty("java.util.logging.config.file") == null) 
		{
			CommonDataFactory dataFactory = new CommonDataFactoryImpl(); // TODO how to log IO
			Logger logger = dataFactory.getLogger();

			try 
			{
				logger.log(Level.CONFIG, "Program has been started");
				
				EventQueue.invokeLater(() -> {
                    GUI gui = new GUI(dataFactory);
                    gui.setVisible(true);
                });
			}
			catch (RuntimeException e) 
			{
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}	
	}
}
