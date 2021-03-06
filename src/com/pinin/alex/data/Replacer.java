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

import java.util.*;

/**
 * Contains and operates with special symbol replacements for letters.
 */
public class Replacer 
{
	private HashSet<ReplSet> items = new HashSet<>();

	/**
	 * Parses the specified sequence into this object.
	 * Sequence must be a items of lines.
	 * Each line must be in format: "letter: replacement_1; replacement_2; ... replacement_n;"
	 * In the end of the specified sequence must be a new line symbol.
	 */
	public Replacer(CharSequence source) {
		CheckValueHelper.checkNull(source);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<source.length(); i++) {
			char c = source.charAt(i);
			sb.append(c);
			if (c == '\n') {
				ReplSet rs = parseLine(sb);
				add(rs);
				sb = new StringBuilder();
			}
		}
	}

	private ReplSet parseLine(CharSequence line) {
		StringBuilder text = new StringBuilder();
		StringBuilder repl = new StringBuilder();
		LinkedList<String> replacements = new LinkedList<>();

		boolean isText = true;
		for (int i=0; i<line.length(); i++) {
			char each = line.charAt(i);
			switch (each) {
				case ' ': case '\t': case '\n': case '\f': case '\r': // skip spaces
					continue;
				case ':': // turn up to replacements parsing
					isText = false;
					continue;
				case ';': // one replacement has been got
					replacements.add(repl.toString());
					repl = new StringBuilder();
					continue;
			}
			if (isText) text.append(each);
			else repl.append(each);
		}

		String[] replacementsArr = new String[replacements.size()];
		int i=0;
		for (String each : replacements)
			replacementsArr[i++] = each;

		return new ReplSet(text.toString(), replacementsArr);
	}

	public boolean add(ReplSet item) throws IllegalArgumentException {
		CheckValueHelper.checkNull(item);
		return items.add(item);
	}

	public boolean remove(ReplSet item) throws IllegalArgumentException {
		CheckValueHelper.checkNull(item);
		return items.remove(item);
	}

	/**
	 * Returns an object that contains the specified text or empty object.
	 */
	public ReplSet findWithText(String text) {
		for (ReplSet item : items)
			if (item.getText().equals(text))
				return item;
		return new ReplSet();
	}
}
