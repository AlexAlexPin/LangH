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
import java.util.*;

import com.pinin.alex.main.Common.*;

/**
 * Object that represents a virtual dictionary of foreign phrases that contain identifiers, 
 * phrases itself and sets of translations, comments, and tags.
 */
public class Phrases
{
	private ArrayList<PhraseSet> items;

	// The larger id attribute of elements of this object
	private int biggestID;
	
	// The list of all ID attribute values of elements of this object
	private HashSet<Integer> ids;
	
	// The list of all phrase attribute values of elements of this object
	private HashSet<String> phrases;
	
	// The list of all tags as <String< of tag attribute values of elements of this object
	private Term tags;
	
	// The character that is used to separate elements in a file
	private char DELIMITER = '~';

	public Phrases() {
		items = new ArrayList<>();
		setDefault();
	}

	/**
	 * Appends the specified element to the end of this list.<br>
	 * The new element must not be empty. The IDof this object must not contain the same value
	 * as the ID of the specified element. The phrases of this object must not contain the same value as the
	 * phrase of the specified element.
	 * In the case of failure of these conditions this method does not append the specified element and returns false.
	 */
	public boolean add(PhraseSet e) throws IllegalArgumentException {
		CheckValueHelper.checkNull(e);
		return increase(e) && items.add(e);
	}

	/**
	 * Tries to append the specified element to the end of this list or edit the existing element.
	 * Note: A new element must not be empty. The ID of this object must not be the same value as the ID
	 * of the specified element.
	 * In the case of failure of these conditions this method does not append the specified element
	 * and returns -2.
	 * If the phrases of this object contains the same value as the phrase of the specified object this method
	 * edits the existing element that contains the same phrase value and returns the index
	 * of the edited element. Edits the transl, the comment and the tag fields only.
	 * In other cases append the specified element to the end of this list and returns -1.
	 */
	public int addEdit(PhraseSet e) throws IllegalArgumentException {
		CheckValueHelper.checkNull(e);
		if (e.isEmpty()) return -2;
		if (this.add(e)) return -1;

		int result = -2;
		String find = e.getPhrase();
		for (int i=0; i<items.size(); i++) {
			PhraseSet edit = items.get(i);
			if (!find.equals(edit.getPhrase())) continue;

			edit.addTransl(e.getTransl());
			edit.addComment(e.getComment());
			edit.addTag(e.getTag());
			result = i;
		}
		tags.clear();
		for (PhraseSet each : items) {
			tags.addAll(each.getTag());
		}
		return result;
	}

	public PhraseSet remove(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		PhraseSet p = items.remove(index);
		this.decrease(p);
		return p;
	}

	public int size() {
		return items.size();
	}

	public void clear() {
		items.clear();
		setDefault();
	}

	public int getBiggestID() {
		return biggestID;
	}

	/**
	 * Returns the list of indices of elements of this object if attributes
	 * of these elements content at least a part of according specified elements.
	 */
	public int[] indexOfPart(String phrase, String transl, String comment, String tag) throws IllegalArgumentException {
		CheckValueHelper.checkNull(phrase, transl, comment, tag);

		ArrayList<Integer> collector = new ArrayList<>();
		for (int i=0; i<items.size(); i++) {
			PhraseSet each = items.get(i);
			if (each.getPhrase().toLowerCase().contains(phrase.toLowerCase())
					&& each.getTransl().toString().toLowerCase().contains(transl.toLowerCase())
					&& each.getComment().toString().toLowerCase().contains(comment.toLowerCase())
					&& each.getTag().toString().toLowerCase().contains(tag.toLowerCase())) {
				collector.add(i);
			}
		}
		int[] result = new int[collector.size()];
		for (int i=0; i<result.length; i++) {
			result[i] = collector.get(i);
		}
		return result;
	}

//
// ID
//

	public int getId(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		return items.get(index).getId();
	}

	/**
	 * Returns index of the first occurrence of element with the specified ID or -1.
	 */
	public int indexOfId(int id) throws IllegalArgumentException {
		for (int i=0; i<items.size(); i++)
			if (items.get(i).getId() == id) return i;
		return -1;
	}

//
// Phrases
//

	public String getPhrase(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		return items.get(index).getPhrase();
	}

	/**
	 * Replaces element's phrase value at the specified position in this with the specified value.
	 * The new value must not be empty. The phrases of this object must not contain the specified value.
	 * In the case of failure of these conditions this method does not replace value.
	 */
	public void setPhrase(int index, String value) throws IllegalArgumentException {
		CheckValueHelper.checkNull(value);
		CheckValueHelper.checkSize(index, size());
		if (value.isEmpty() || phrases.contains(value)) return;

		String old = items.get(index).getPhrase();
		phrases.remove(old);
		items.get(index).setPhrase(value);
		phrases.add(value);
	}

	/**
	 * Returns true if one of elements contains ths specified phrase.
	 */
	public boolean containsPhrase(String phrase) throws IllegalArgumentException {
		CheckValueHelper.checkNull(phrase);
		return phrases.contains(phrase);
	}

//
// Translations
//

	public Term getTransl(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		return items.get(index).getTransl();
	}

	public void setTransl(int index, Term value) throws IllegalArgumentException {
		CheckValueHelper.checkNull(value);
		CheckValueHelper.checkSize(index, size());
		items.get(index).setTransl(value);
	}

//
// Comments
//

	public Term getComment(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		return items.get(index).getComment();
	}

	public void setComment(int index, Term value) throws IllegalArgumentException {
		CheckValueHelper.checkNull(value);
		CheckValueHelper.checkSize(index, size());
		items.get(index).setComment(value);
	}

//
// Tags
//

	public Term getTag(int index) throws IllegalArgumentException {
		CheckValueHelper.checkSize(index, size());
		return items.get(index).getTag();
	}

	/**
	 * Replaces element's tag value at the specified position in this list with the specified value.
	 * Note: To set value it is strongly recommended to use this method rather then
	 * this.findContaining(...).setTag(...) because this method allows to correlate new values with list of tags.
	 */
	public void setTag(int index, Term value) throws IllegalArgumentException {
		CheckValueHelper.checkNull(value);
		CheckValueHelper.checkSize(index, size());
		items.get(index).setTag(value);
		tags.clear();
		for (PhraseSet each : items) {
			tags.addAll(each.getTag());
		}
	}

	public void addTag(int index, Term tags) throws IllegalArgumentException {
		CheckValueHelper.checkNull(tags);
		CheckValueHelper.checkSize(index, size());
		items.get(index).addTag(tags);
	}

	public String[] getTagList() {
		String[] result = new String[tags.size()];
		int i=0;
		for (String each : tags)
			result[i++] = each;
		return result;
	}

	/**
	 * Returns indices of elements with at least one of he specified tag.
	 */
	public int[] indexOfTags(Term t) throws IllegalArgumentException {
		CheckValueHelper.checkNull(t);
		ArrayList<Integer> collector = new ArrayList<>();
		for (int i=0; i<items.size(); i++) {
			Term eachTagRow = items.get(i).getTag();
			for (String eachNewTag : t) {
				if (eachTagRow.contains(eachNewTag)) {
					collector.add(i);
					break;
				}
			}
		}
		int[] result = new int[collector.size()];
		for (int i=0; i<result.length; i++) {
			result[i] = collector.get(i);
		}
		return result;
	}

//
// Files
//

	/**
	 * Tries to fill this object from the specified file.
	 * Elements of each PhraseSet object in the file must be separated by '~' character.
	 */
	public void fillFromFile(File file) throws IllegalArgumentException, ParseFileException
	{
		CheckValueHelper.checkNull(file);
		try {
			CharSequence fileContent = Common.getFileContent(file);

			StringBuilder id      = new StringBuilder();
			StringBuilder phrase  = new StringBuilder();
			StringBuilder transl  = new StringBuilder();
			StringBuilder comment = new StringBuilder();
			StringBuilder tag     = new StringBuilder();

			int stage = 0;  // shows which element of the PhraseSet object is being parsed now.
			// 0 - id, 1 - phrase, 2 - transl, 3 - comment, 4 - tag

			for (int i=0; i<fileContent.length(); i++) {
				final char each = fileContent.charAt(i);

				if (each == DELIMITER) stage++;
				else {
					switch (stage) {
						case 0: id     .append(each); break;
						case 1: phrase .append(each); break;
						case 2: transl .append(each); break;
						case 3: comment.append(each); break;
						case 4: tag    .append(each); break;
					}
				}

				if (stage > 4) {	// all elements of one PhraseSet object have been parsed
					stage = 0;	// reset the stage to parse the next PhraseSet object
					PhraseSet toAdd;
					try {
						toAdd = new PhraseSet(
								Integer.parseInt(id.toString()),
								phrase.toString(),
								new Term().parse(transl.toString(),  ";"),
								new Term().parse(comment.toString(), ";"),
								new Term().parse(tag.toString(),     ";") );
					}
					catch (NumberFormatException e) {
						this.clear();
						throw new ParseFileException("incorrect id ["
								+ id + "] in PhraseSet.fillFromFile()");
					}

					if (!this.add(toAdd)) {
						this.clear();
						throw new ParseFileException("incorrect element to be added ["
								+ toAdd + "] in PhraseSet.fillFromFile()");
					}

					id      = new StringBuilder();
					phrase  = new StringBuilder();
					transl  = new StringBuilder();
					comment = new StringBuilder();
					tag     = new StringBuilder();
				}
			}
			if (stage != 0) { // the number of delimiters at the end is incorrect
				this.clear();
				throw new ParseFileException("stage at the end ["
						+ stage + "], expects [0] in PhraseSet.fillFromFile()");
			}
		}
		catch (Exception e) {
			FileReadException ee = new FileReadException(e.getClass() + " exception in PhraseSet.fillFromFile()");
			ee.initCause(e);
			throw ee;
		}
	}

	/**
	 * Write this object to the specified file.
	 * All '~' characters will be replaced by '^' character.
	 */
	public void codeFile(File file) throws IllegalArgumentException, CodeFileException {
		CheckValueHelper.checkNull(file);
		char delimiterFileReplacement = '^';
		try {
			StringBuilder sb = new StringBuilder();
			for (PhraseSet each : items) {
				sb.append(each.getId()).append(DELIMITER);
				sb.append(each.getPhrase()            .replace(DELIMITER, delimiterFileReplacement)).append(DELIMITER);
				sb.append(each.getTransl() .toString().replace(DELIMITER, delimiterFileReplacement)).append(DELIMITER);
				sb.append(each.getComment().toString().replace(DELIMITER, delimiterFileReplacement)).append(DELIMITER);
				sb.append(each.getTag()    .toString().replace(DELIMITER, delimiterFileReplacement)).append(DELIMITER);
			}
			Common.putFileContent(file, sb);
		}
		catch (Exception e) {
			CodeFileException ee = new CodeFileException(e.getClass() + " exception in Phrases.codeFile()");
			ee.initCause(e);
			throw ee;
		}
	}

//
// Tools
//

	private void setDefault() {
		biggestID = 0;
		ids = new HashSet<>();
		phrases = new HashSet<>();
		tags = new Term();
	}

	/**
	 * Adds to variables of this object information about the specified element.
	 * Note: A new element must be not null and empty.
	 * The ID of this object must not contain the same value as the ID of the specified object.
	 * The phrases of this object must not contain the same value as the phrase phrases of the specified object.
	 * In the case of failure of these conditions this method does not add information about the specified
	 * object and returns false.
	 */
	private boolean increase(PhraseSet e) {
		int ID = e.getId();
		String phrase = e.getPhrase();
		if (e.isEmpty() || ids.contains(ID) || phrases.contains(phrase)) return false;

		if (biggestID < ID) biggestID = ID;

		ids.add(ID);
		phrases.add(phrase);
		Term t = e.getTag();
		tags.addAll(t);
		return true;
	}

	/**
	 * Removes from variables of this object information about the specified element.
	 * @param e - the element to be removed
	 */
	private void decrease(PhraseSet e) {
		final Integer ID = e.getId();
		ids.remove(ID);
		if (ID == biggestID) {
			biggestID = 0;
			ids.stream().filter(each -> biggestID < each).forEach(each -> biggestID = each);
		}
		phrases.remove(e.getPhrase());

		tags.clear();
		for (PhraseSet each : items) {
			tags.addAll(each.getTag());
		}
	}

//
// Exceptions
//

	private class ParseFileException extends RuntimeException
	{
		public final static long serialVersionUID = 1L;
		private ParseFileException(String text) { super(text); }
	}

	private class CodeFileException extends RuntimeException
	{
		public final static long serialVersionUID = 1L;
		private CodeFileException(String text) { super(text); }
	}
}
