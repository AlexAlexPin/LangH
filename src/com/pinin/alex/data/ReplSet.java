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

package com.pinin.alex.data;

import java.util.Arrays;

/**
 * Contains the couple of a text and a set of replacements for this text.
 */
public class ReplSet 
{
	private String text = "";
	private String[] replacements = new String[0];

	ReplSet(){}

	ReplSet(String text, String[] replacements) throws IllegalArgumentException {
		CheckValueHelper.checkNull(text, replacements);
		this.text = text;
		this.replacements = replacements;
	}

	public String getText() {
		return this.text;
	}

	/**
	 * For the specified text returns the specified replacement.
	 */
	public String getReplacement(String text, int replIndex) throws IllegalArgumentException {
		CheckValueHelper.checkNull(text);
		CheckValueHelper.checkSize(replIndex, replacements.length);
		return replacements[replIndex];
	}

	public int size() {
		return replacements.length;
	}

	public boolean isEmpty() {
		return text.isEmpty() && replacements.length == 0;
	}

	@Override
	public int hashCode() {
		return 7 * text.hashCode()
			+ 13 * Arrays.hashCode(replacements);
	}
	
	@Override
	public String toString() {
		return getClass().getName()
			+ "[text: " + text + "]"
			+ "[replacements: " + Arrays.toString(replacements) + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;
		ReplSet aOther = (ReplSet) other;
		return text.equals(aOther.text);
	}
}
