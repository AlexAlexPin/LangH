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

import java.io.*;
import java.util.*;
import javax.swing.*;
import com.pinin.alex.main.*;

public interface DictionaryTable
{
	/**
	 * Displays only phrases with the specified <code>Term</code>.
	 * @param tags - tags that must contain phrases to be displayed.
	 */
	void showOnly(Term tags);
	
	/**
	 * Add the specified <code>Term</code> to these phrases.
	 * @param tags - tags to be added.
	 */
	void addTags(Term tags);
	
	/**
	 * Adds the specified sound to the first marked row.
	 * @param sound - a sound to be added.
	 */
	void addSound(AudioContainer sound);
	
	/**
	 * Returns a sound with the specified id is a <code>File</code>.
	 * @param toGet - an object to get the sound.
	 * @param id - an id to be used to get the sound.
	 * @return <code>if the sound has been got successfully.
	 */
	boolean getSound(AudioContainer toGet, int id);
	
	/**
	 * Returns a list of all components of this container.
	 * @return a list of all components of this container.
	 */
	Collection<Phrase> getAll();
	
	/**
	 * Adds new elements.
	 * @param c - new elements.
	 */
	void addAll(Collection <? extends Phrase> c);
	
	/**
	 * Returns a number of kept phrases.
	 * @return a number of kept phrases.
	 */
	int length();
	
	/**
	 * Returns the current data path.
	 * @return the current data path.
	 */
	File getPath();
	
	/**
	 * Loads data from the specified file.
	 * @param file - a file to be read. Accepting <code>null</code> cleans database.
	 */
	void loadData(File file);
	
	/**
	 * Writes data to the specified file.
	 * @param file - a file to be written.
	 */
	void codeFile(File file);
	
	/**
	 * Returns the table of this container.
	 * @return the table of this container.
	 */
	JTable getTable();
	
} // end DictionaryTable
