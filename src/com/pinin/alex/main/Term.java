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

import java.util.LinkedHashSet;

/**
 * Represents a virtual set of addition information for the <code>Phrase</code> object
 * and may content set of <code>String</code> objects.
 */
public class Term extends LinkedHashSet<String> 
{
//
// Variables
//
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;

//
// Methods
//
	
	/**
	 * Parses the specified <code>String</code> by the specified delimiter to this object
	 * and adds elements into this object
	 * @param line - the <code>String</code> to be parsed
	 * @param delimiter - the delimiter to split the specified <code>String</code>
	 * @return this object
	 * @throws NullPointerException if at least one of specified <code>String</code> 
	 * is <code>null</code>
	 */
	public Term parse(String line, String delimiter) throws NullPointerException 
	{
		if (line == null || delimiter == null) 
			throw new NullPointerException("null value in Term.parse(String, String)");
		
		String[] sarr = line.split(delimiter);
		
		for (String each : sarr) 
		{
			String trim = each.trim();
			boolean isEmpty = trim.isEmpty();
			if (!isEmpty) this.add(each.trim());
		}
		return this;
	}
	
	/**
	 * Parses the specified <code>String</code> to this object
	 * @param line - the <code>String</code> to be parsed
	 * @return this object
	 * @throws NullPointerException the specified <code>String</code> is <code>null</code>
	 */
	public Term parse(String line) 
	{
		if (line == null) throw new NullPointerException("null value in Term.parse(String)");
		this.add(line);
		return this;
	}
	
//
// Extending Object
//
	
	@Override
	public String toString() 
	{
		if (this.size() == 0) return "";
		
		StringBuilder sb = new StringBuilder();

		for (String each : this) 
		{
			sb.append(each);
			sb.append("; ");
		}
		
		int length = sb.length();
		sb.delete(length-2, length);
		
		return sb.toString();
	}
	
} // end Term