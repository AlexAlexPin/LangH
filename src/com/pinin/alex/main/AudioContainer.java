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

import java.io.*;

/**
 * Contains a sound.
 */
public interface AudioContainer
{
	/**
	 * Starts sound capturing.
	 */
	void capture();
	
	/**
	 * Stops sound capturing.
	 */
	void stop();
	
	/**
	 * Plays captured or loaded sound.
	 */
	void play();
	
	/**
	 * Saves sound to the specified file.
	 * @param file - a file to be written.
	 * @return <code>true</code> if the file has been written successfully.
	 */
	boolean save(File file);
	
	/**
	 * Loads sound from the specified file.
	 * @param file - a file to be read.
	 * @return <code>true</code> if the file has been read successfully.
	 */
	boolean load(File file);
	
} // end AudioContainer
