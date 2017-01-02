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

import java.util.LinkedHashSet;

/**
 * Represents a virtual set of addition information for the PhraseSet object.
 */
public class Term extends LinkedHashSet<String> 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Parses the specified String by the specified delimiter to this object
	 * and adds elements into this object.
	 */
	public Term parse(String line, String delimiter) throws IllegalArgumentException {
		CheckValueHelper.checkNull(line, delimiter);
		for (String each : line.split(delimiter)) {
			String trim = each.trim();
			boolean isEmpty = trim.isEmpty();
			if (!isEmpty) this.add(each.trim());
		}
		return this;
	}

	@Override
	public String toString() {
		if (this.size() == 0) return "";
		StringBuilder sb = new StringBuilder();
		for (String each : this) {
			sb.append(each);
			sb.append("; ");
		}
		int length = sb.length();
		sb.delete(length - 2, length);
		return sb.toString();
	}
}
