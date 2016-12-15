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

import java.io.*;
import java.util.*;
import java.util.function.*;
import com.pinin.alex.main.Common.*;

/**
 * Object that represents a virtual dictionary of foreign phrases that contain identifiers, 
 * phrases itself and sets of translations, comments, and tags.
 * Attributes of this object:<br>
 * <blockquote>
 * <code>int largerID</code> - the largest identifier value of all phrases of this set;<br>
 * <code>HashSet<'Integer> IDs</code> - the list of all identifier values of all phrases in this set.
 * It is not possible to put the new element in this set if its identifier has already been
 * contained in this list;<br>
 * <code>HashSet<'String> phrases</code> - the list of all phrase values of all phrases of this set. 
 * It is not possible to put the new element in this set if its phrase value has already been
 * contained in this list. It is possible to upgrade an excising element with the same phrase value;<br>
 * <code>Term tags</code> - the list of all tags of all phrases of this set;<br>
 * <code>char delimiter</code> - a character that is used to separate elements in a file. 
 * The default value is <i>'~'</i>;<br>
 * <code>char replacement</code> - a character to replace the delimiter in case if user try to enter it.
 * The delimiter will be replaced before a file will be written. The default value is <i>'`'</i>;<br>
 * </blockquote>
 */
public class Phrases extends ArrayList<Phrase>
{
//
// Variables	
//	
	
	/** The larger <code>id</code> attribute of elements of this object */
	private int largerID;
	
	/** The list of all <code>ID</code> attribute values of elements of this object */
	private HashSet<Integer> IDs;
	
	/** The list of all <code>phrase</code> attribute values of elements of this object */
	private HashSet<String> phrases;
	
	/** The list of all tags as <code>String</code> of <code>tag</code> attribute values 
	 * of elements of this object */
	private Term tags;
	
	/** The character that is used to separate elements in a file */
	private char delimiter;
	
	/** The character to replace the delimiter in case if user try to enter it */
	private char replacement;
	
	/** Generated serial version ID */
	private static final long serialVersionUID = -3893537185982556327L;
	
//	
//	Constructors
//
	
	/**
	 * Constructor
	 */
	public Phrases() 
	{
		delimiter = '~';
		replacement = '^';
		setDefault();
	}

//
// Methods
//
	
	/**
	 * Sets variables default values
	 */
	private void setDefault() 
	{
		largerID = 0;
		IDs = new HashSet<Integer>();
		phrases = new HashSet<String>();
		tags = new Term();
	}

	/**
	 * Adds to variables of this object information about the specified element.<br>
	 * Works with <code>IDs</code>, <code>phrases</code>, <code>tags</code>. Can change
	 * the <code>largerID</code>.</br>
	 * <b>Note:</b> The new element must not be <code>null</code> and empty. The <code>ID</code> 
	 * attribute of this object must not contain the same value as the <code>ID</code> attribute value 
	 * of the specified object. The <code>phrases</code> attribute of this object must not contain the 
	 * same value as the <code>phrase</code> attribute value of the specified object. In the case
	 * of failure of these conditions this method does not add information about the specified
	 * object and returns <code>false</code>.
	 * @param e - the new element to be added
	 * @return <code>true</code> if information has been added
	 */
	private boolean increase(Phrase e) 
	{
		int ID = e.getId();
		String phrase = e.getPhrase();
		
		if (e.isEmpty() || IDs.contains(ID) || phrases.contains(phrase)) return false;
		
		if (largerID < ID) largerID = ID;
		
		IDs.add(ID);
		phrases.add(phrase);
		Term t = e.getTag();
		tags.addAll(t);
		
		return true;
	}
	
	/**
	 * Removes from variables of this object information about the specified element.<br>
	 * Works with <code>IDs</code>, <code>phrases</code>, <code>tags</code>. Can change
	 * the <code>largerID</code>.
	 * @param e - the element to be removed
	 */
	private void decrease(Phrase e) 
	{
		final Integer ID = e.getId();
		IDs.remove(ID);
		if (ID == largerID) 
		{
			largerID = 0;
			for (Integer each : IDs) 
			{
				if (largerID < each) largerID = each;
			}
		}
		phrases.remove(e.getPhrase());
		
		tags.clear();
		for (Phrase each : this) 
		{
			tags.addAll(each.getTag());
		}
	}
	
	/**
	 * Clears all support lists and fills them again.
	 */
	private void reload() 
	{
		setDefault();
		for (Phrase each : this) 
		{
			Integer ID = each.getId();
			IDs.add(ID);
			if (largerID > ID) largerID = ID;
			phrases.add(each.getPhrase());
			tags.addAll(each.getTag());
		}
	}
	
	/**
	 * Returns the current <code>largerID</code> value
	 * @return the current <code>largerID</code> value
	 */
	public int getLargerID() 
	{
		return largerID;
	}
	
	/**
	 * Returns the current <code>delimiter</code> value
	 * @return the current <code>delimiter</code> value
	 */
	public char getDelimiter() 
	{
		return delimiter;
	}
	
	/**
	 * Sets the new <code>delimiter</code> value
	 * @param delimiter - the new value
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setDelimiter(Character delimiter) throws NullPointerException
	{
		if (delimiter == null) throw new NullPointerException("null value in Phrases.setDelimiter()");
		this.delimiter = delimiter;
	}
	
	/**
	 * Returns current <code>replacement</code> value
	 * @return current <code>replacement</code> value
	 */
	public char getReplasement() 
	{
		return replacement;
	}
	
	/**
	 * Sets the new <code>replacement</code> value
	 * @param replacement - the new value
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setReplacement(Character replacement) throws NullPointerException
	{
		if (replacement == null) throw new NullPointerException("null value in Phrases.setReplacement()");
		this.replacement = replacement;
	}
	
	/**
	 * Returns the copy of current list of phrases as <code>String[]</code>.
	 * @return the copy of current list of phrases as <code>String[]</code>.
	 */
	public String[] getPhraseList() 
	{
		String[] sarr = new String[phrases.size()];
		int i=0;
		for (String each : phrases)
		{
			sarr[i++] = each;
		}
		return sarr;
	}
	
	/**
	 * Returns the copy of current list of tags as <code>String[]</code>.
	 * @return the copy of current list of tags as <code>String[]</code>.
	 */
	public String[] getTagList() 
	{
		String[] sarr = new String[tags.size()];
		int i=0;
		for (String each : tags)
		{
			sarr[i++] = each;
		}
		return sarr;
	}
	
	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * <b>Note:</b> The new element must not be empty. The <code>ID</code> attribute of this object 
	 * must not contain the same value as the <code>ID</code> attribute value of the specified object. 
	 * The <code>phrases</code> attribute of this object must not contain the same value as the 
	 * <code>phrase</code> attribute value of the specified object. In the case of failure of these 
	 * conditions this method does not add information about the specified object and returns 
	 * the specified element.
	 */
	@Override
	public Phrase set(int index, Phrase element) throws IndexOutOfBoundsException, NullPointerException 
	{
		if (element == null) throw new NullPointerException("null value in Phrase.set()");
		
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.set()");
		
		Phrase old = this.get(index);
		decrease(old);
		
		if (increase(element)) return super.set(index, element);
		
		else increase(old);
		return element;
	}
	
	/**
	 * Replaces element's <code>ID</code> attribute value at the specified position 
	 * in this list with the specified value.<br>
	 * <b>Note: </b>To set value it is strongly recommended to use this method rather then 
	 * <code>this.get(...).setID(...)</code> because this method allows to correlate
	 * new values with list of IDs.<br>
	 * The <code>ID</code> attribute of this object must not contain the specified value. 
	 * In the case of failure of these conditions this method does not replace value and 
	 * returns the <code>false</code>.
	 * @param index - index of the element
	 * @param value - the new value
	 * @return <code>true</code> if this list changed
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IndexOutOfBoundsException if the specified index is out of bounds
	 */
	public boolean setId(int index, Integer value) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (value == null) throw new NullPointerException("null value in Phrase.setId()");
		
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.setID()");
		
		if (IDs.contains(value)) return false;
		
		Integer old = this.get(index).getId();
		IDs.remove(old);
		if (largerID == old) largerID--;
		
		this.get(index).setId(value);
		IDs.add(value);
		if (largerID < value) largerID = value;
		
		return true;
	}
	
	/**
	 * Replaces element's <code>phrase</code> attribute value at the specified position 
	 * in this list with the specified value.<br>
	 * <b>Note: </b>To set value it is strongly recommended to use this method rather then 
	 * <code>this.get(...).setPhrase(...)</code> because this method allows to correlate
	 * new values with list of phrases.<br>
	 * The new value must not be empty. The <code>phrases</code> attribute of this object 
	 * must not contain the specified value. In the case of failure of these conditions 
	 * this method does not replace value and returns <code>false</code>.
	 * @param index - index of the element
	 * @param value - the new value
	 * @return <code>true</code> if this list changed
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IndexOutOfBoundsException if the specified index is out of bounds
	 */
	public boolean setPhrase(int index, String value) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (value == null) throw new NullPointerException("null value in Phrase.setPhrase()");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.setPhrase()");
		
		if (value.isEmpty() || phrases.contains(value)) return false;
		
		String old = this.get(index).getPhrase();
		phrases.remove(old);
		
		this.get(index).setPhrase(value);
		phrases.add(value);
		
		return true;
	}
	
	/**
	 * Replaces element's <code>transl</code> attribute value at the specified position 
	 * in this list with the specified value.<br>
	 * @param index - index of the element
	 * @param value - the new value
	 * @return <code>true</code> if this list changed
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IndexOutOfBoundsException if the specified index is out of bounds
	 */
	public boolean setTransl(int index, Term value) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (value == null) throw new NullPointerException("null value in Phrase.setTransl()");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.setPhrase()");
		
		this.get(index).setTransl(value);
		return true;
	}
	
	/**
	 * Replaces element's <code>comment</code> attribute value at the specified position 
	 * in this list with the specified value.<br>
	 * @param index - index of the element
	 * @param value - the new value
	 * @return <code>true</code> if this list changed
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IndexOutOfBoundsException if the specified index is out of bounds
	 */
	public boolean setComment(int index, Term value) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (value == null) throw new NullPointerException("null value in Phrase.setComment()");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.setPhrase()");
		
		this.get(index).setComment(value);
		return true;
	}
	
	/**
	 * Replaces element's <code>tag</code> attribute value at the specified position 
	 * in this list with the specified value.<br>
	 * <b>Note: </b>To set value it is strongly recommended to use this method rather then 
	 * <code>this.get(...).setTag(...)</code> because this method allows to correlate
	 * new values with list of tags.<br>
	 * @param index - index of the element
	 * @param value - the new value
	 * @return <code>true</code> if this list changed
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IndexOutOfBoundsException if the specified index is out of bounds
	 */
	public boolean setTag(int index, Term value) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (value == null) throw new NullPointerException("null value in Phrase.setTag()");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.setTag()");
		
		this.get(index).setTag(value);
		
		tags.clear();
		for (Phrase each : this)
		{
			tags.addAll(each.getTag());
		}
		return true;
	}
	
	/**
	 * Appends the specified element to the end of this list.<br>
	 * <b>Note:</b> The new element must not be empty. The <code>ID</code> attribute of this object 
	 * must not contain the same value as the <code>ID</code> attribute value of the specified element. 
	 * The <code>phrases</code> attribute of this object must not contain the same value as the 
	 * <code>phrase</code> attribute value of the specified element. In the case of failure of these 
	 * conditions this method does not append the specified element and returns <code>false</code>.
	 */
	@Override
	public boolean add(Phrase e) throws NullPointerException 
	{
		if (e == null) throw new NullPointerException("null value in Phrase.add(Phrase)");
		
		if (!increase(e)) return false;
		return super.add(e);
	}
	
	/**
	 * Inserts the specified element at the specified position in this list. 
	 * Shifts the element currently at that position (if any) and any subsequent 
	 * elements to the right (adds one to their indices).<br>
	 * <b>Note:</b> The new element must not be empty. The <code>ID</code> attribute of this object 
	 * must not contain the same value as the <code>ID</code> attribute value of the specified element.
	 * The <code>phrases</code> attribute of this object must not contain the same value as the 
	 * <code>phrase</code> attribute value of the specified object. In the case of failure of these 
	 * conditions this method does not insert the specified element.
	 */
	@Override
	public void add(int index, Phrase element) throws NullPointerException, IndexOutOfBoundsException 
	{
		if (element == null) throw new NullPointerException("null value in Phrase.add(int, Phrase)");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index 
					+ " in Phrase.add(int, Phrase)");
		
		super.add(index, element);
	}
	
	/**
	 * Appends all of the elements in the specified collection to the end of this list, in the order 
	 * that they are returned by the specified collection's Iterator.<br>
	 * <b>Note:</b> New elements must not be empty. The <code>ID</code> attribute of this object 
	 * must not contain the same value as the <code>ID</code> attribute value of the specified elements. 
	 * The <code>phrases</code> attribute of this object must not contain the same value as the 
	 * <code>phrase</code> attribute value of the specified elements. In the case of failure of these 
	 * conditions this method does not append the specified elements and returns <code>false</code>.
	 */
	@Override
	public boolean addAll(Collection<? extends Phrase> c) throws NullPointerException 
	{
		if (c == null) throw new NullPointerException("null value in Phrase.addAll(Collection)");
		
		final int sizeBef = this.size();
		
		for (Phrase each : c)
		{
			this.add(each);
		}
		final int sizeAft = this.size();
		
		return sizeBef - sizeAft == 0;
	}
	
	/**
	 * Inserts all of the elements in the specified collection into this list, 
	 * starting at the specified position. Shifts the element currently at that position (if any) 
	 * and any subsequent elements to the right (increases their indices). The new elements 
	 * will appear in the list in the order that they are returned by the specified collection's 
	 * iterator.
	 * <b>Note:</b> The new element must not be empty. The <code>ID</code> attribute of this object 
	 * must not contain the same value as the <code>ID</code> attribute value of the specified element.
	 * The <code>phrases</code> attribute of this object must not contain the same value as the 
	 * <code>phrase</code> attribute value of the specified object. In the case of failure of these 
	 * conditions this method does not insert the specified element.
	 */
	@Override
	public boolean addAll(int index, Collection<? extends Phrase> c) 
			throws IndexOutOfBoundsException, NullPointerException 
	{
		if (c == null) throw new NullPointerException("null value in Phrase.addAll(int, Collection)");
		
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index 
					+ " in Phrase.addAll(int, Collection)");
		
		final int sizeBef = this.size();
		
		int ind = index;
		for (Phrase each : c) 
		{
			int sizeB = this.size();
			this.add(ind++, each);
			int sizeA = this.size();
			if (sizeA > sizeB) ind++;
		}
		
		final int sizeAft = this.size();
		
		return sizeBef - sizeAft == 0;
	}
	
	/**
	 * Tries to append the specified element to the end of this list or edit the existing element.<br>
	 * <b>Note:</b> The new element must not be empty. The <code>ID</code> attribute of this object must 
	 * not contain the same value as the <code>ID</code> attribute value of the specified element. 
	 * In the case of failure of these conditions this method does not append the specified element 
	 * and returns <code>-2</code>.<br>
	 * If the <code>phrases</code> attribute of this object contains the same value as the 
	 * <code>phrase</code> attribute value of the specified object this method edits the existing 
	 * element that contains the same <code>phrase</code> attribute value and returns the <code>index</code> 
	 * of the edited element. Edits the <code>transl</code>, the <code>comment</code> and the <code>tag</code> 
	 * attributes only.<br>
	 * In other cases append the specified element to the end of this list and returns <code>-1</code>.
	 * @param e - element to be appended to this list
	 * @return <code>-2</code> if the specified element has not added, <code>-1</code> if
	 * the specified element has added, <code>index</code> if the existing element has edited.
	 * @throws NullPointerException if the specified value is <code>null</code>
	 */
	public int addEdit(Phrase e) throws NullPointerException 
	{
		if (e == null) throw new NullPointerException("null value in Phrase.addEdit()");
		
		if (e.isEmpty()) return -2;
		if (this.add(e)) return -1;
		
		else 
		{
			int editIndex = -2;
			String find = e.getPhrase();
			for (int i=0; i<this.size(); i++) 
			{
				Phrase edit = this.get(i);
				if (find.equals(edit.getPhrase())) 
				{
					edit.addTransl(e.getTransl());
					edit.addComment(e.getComment());
					edit.addTag(e.getTag());
					editIndex = i;
				}
			}

			tags.clear();
			for (Phrase each : this)
			{
				tags.addAll(each.getTag());
			}
			return editIndex;
		}
	}
	
	@Override
	public Phrase remove(int index) throws IndexOutOfBoundsException 
	{
		if (index < 0 || index >= this.size()) 
			throw new IndexOutOfBoundsException("size: " + this.size() + ", index: " + index + " in Phrase.remove(int)");

		Phrase p = super.remove(index);
		this.decrease(p);
		return p;
	}
	
	@Override
	public boolean remove(Object o) 
	{
		boolean b = super.remove(o);
		if (b) this.decrease((Phrase) o);
		return b;
	}
	
	@Override
	public void removeRange(int fromIndex, int toIndex) throws IndexOutOfBoundsException 
	{
		if (fromIndex < 0 || fromIndex >= this.size() || toIndex > this.size() || toIndex < fromIndex) 
			throw new IndexOutOfBoundsException("Size: " + this.size() 
			+ ", fromIndex: " + fromIndex + ", toIndex: " + toIndex + " in Phrase.removeRange()");

		super.removeRange(fromIndex, toIndex);
		reload();
	}
	
	@Override
	public boolean removeIf(Predicate<? super Phrase> filter) 
	{
		boolean b = super.removeIf(filter);
		if (b) reload();
		return b;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) 
	{
		boolean b = super.removeAll(c);
		if (b) reload();
		return b;
	}
	
	@Override
	public void clear() 
	{
		super.clear();
		setDefault();
	}
	
	/**
	 * Returns the list of indices of elements of this object if attributes 
	 * of these elements content at least a part of according specified elements.<br>
	 * The <code>ID</code> attribute is not processed.
	 * @param phrase - the element to search in <code>phrase</code> attributes
	 * @param transl - the element to search in <code>transl</code> attributes
	 * @param comment - the element to search in <code>comment</code> attributes
	 * @param tag - the element to search in <code>tag</code> attributes
	 * @return the required list of indices
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public int[] indexOfPart(String phrase, String transl, String comment, String tag) 
			throws NullPointerException 
	{
		
		if (phrase == null || transl == null || comment == null || tag == null)
				throw new NullPointerException("null value in Phrase.indexOfPart()");
		
		ArrayList<Integer> collector = new ArrayList<Integer>();
		for (int i=0; i<this.size(); i++) 
		{
			Phrase each = this.get(i);
			if (each.getPhrase().toLowerCase().contains(phrase.toLowerCase())
					&& each.getTransl().toString().toLowerCase().contains(transl.toLowerCase())
					&& each.getComment().toString().toLowerCase().contains(comment.toLowerCase())
					&& each.getTag().toString().toLowerCase().contains(tag.toLowerCase()))
			{
				collector.add(i);
			}
		}
		
		int[] result = new int[collector.size()];
		for (int i=0; i<result.length; i++)
		{
			result[i] = collector.get(i);
		}
		return result;
	}
	
	/**
	 * Returns the list of indices of elements of this object if the <code>tag</code> 
	 * attribute of these elements content at least one subelement of the specified
	 * object.
	 * @param t - the object with data
	 * @return the required list of indices
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public int[] indexOfTags(Term t) throws NullPointerException 
	{
		if (t == null) throw new NullPointerException("null value in Phrase.indexOfTags()");
		
		ArrayList<Integer> collector = new ArrayList<Integer>();
		for (int i=0; i<this.size(); i++) 
		{
			Term eachTagRow = this.get(i).getTag();
			for (String eachNewTag : t) 
			{
				if (eachTagRow.contains(eachNewTag)) 
				{
					collector.add(i);
					break;
				}
			}
		}
		
		int[] result = new int[collector.size()];
		for (int i=0; i<result.length; i++)
		{
			result[i] = collector.get(i);
		}
		return result;
	}
	
	/**
	 * Returns the first index of element of this object if the <code>ID</code> 
	 * attribute of that element equals the specified <code>int</code>.
	 * @param id - the <code>ID</code> to be found
	 * @return the index of the element or -1 if the element has not been found
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public int indexOfId(Integer id) throws NullPointerException
	{
		if (id == null) throw new NullPointerException("null value in Phrase.indexOfTags()");
		for (int i=0; i<this.size(); i++)
		{
			if (this.get(i).getId() == id) return i;
		}
		return -1;
	}

	/**
	 * Returns <code>true</code> if this list contains the specified <code>String</code> among
	 * <code>phrase</code> attributes of its elements
	 * @param s - the <code>String</code> to be checked
	 * @return true if one of elements of this list contains the <code>String</code> in its
	 * <code>phrase</code> attribute
	 * @throws NullPointerException in case of <code>null</code> value
	 */
	public boolean contPhrase(String s) throws NullPointerException 
	{
		if (s == null) throw new NullPointerException("null value in Phrase.contPhrase()");
		return phrases.contains(s);
	}
	
	/**
	 * Tries to fill this object from the file.<br>
	 * Elements of each <code>Phrase</code> object in the file must be separated
	 * by the <code>delimiter</code> character.
	 * @param file -  - a file to be read.
	 * @throws NullPointerException in case of <code>null</code> value
	 * @throws ParseFileException in case of any mistakes during the process
	 */
	public void parseFile(File file) throws NullPointerException, ParseFileException 
	{
		if (file == null) throw new NullPointerException("null value in Phrase.readFile()");
		
		try 
		{
			CharSequence fileContent = Common.getFileContent(file);

			StringBuilder id      = new StringBuilder();
			StringBuilder phrase  = new StringBuilder();
			StringBuilder transl  = new StringBuilder();
			StringBuilder comment = new StringBuilder();
			StringBuilder tag     = new StringBuilder();
			
			int stage = 0;  // shows which element of the Phrase object is being parsed now.
							// 0 - id, 1 - phrase, 2 - transl, 3 - comment, 4 - tag
			
			for (int i=0; i<fileContent.length(); i++) 
			{
				final char each = fileContent.charAt(i);
				
				if (each == delimiter)	// if the delimiter has been met,
				{
					stage++;			// then move to the next stage
				}
				else 
				{
					switch (stage) 
					{
					case 0: id     .append(each); break;
					case 1: phrase .append(each); break;
					case 2: transl .append(each); break;
					case 3: comment.append(each); break;
					case 4: tag    .append(each); break;
					}
				}
				
				if (stage > 4)	// all elements of one Phrase object have been parsed
				{				
					stage = 0;	// reset the stage to parse the next Phrase object
					
					Phrase toAdd = new Phrase();
					try 
					{
						toAdd = new Phrase(
								Integer.parseInt(id.toString()),
								phrase.toString(),
								new Term().parse(transl.toString(),  ";"),	
								new Term().parse(comment.toString(), ";"),
								new Term().parse(tag.toString(),     ";") );
					}
					catch (NumberFormatException e) 
					{
						this.clear();
						throw new ParseFileException("incorrect id [" 
								+ id + "] in Phrase.parseFile()");
					}
					
					if (!this.add(toAdd)) 
					{
						this.clear();
						throw new ParseFileException("incorrect element to be added [" 
								+ toAdd + "] in Phrase.parseFile()");
					}
					
					id      = new StringBuilder();
					phrase  = new StringBuilder();
					transl  = new StringBuilder();
					comment = new StringBuilder();
					tag     = new StringBuilder();
				}		
			}
			if (stage != 0) // the number of delimiters at the end is incorrect
			{ 
				this.clear();
				throw new ParseFileException("stage at the end ["
						+ stage + "], expects [0] in Phrase.parseFile()");
			}
		}
		catch (Exception e)
		{
			FileReadException ee = new FileReadException(e.getClass() + " exception in Phrase.parseFile()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Tries to write this object to the file.<br>
	 * All characters that are equal the <code>delimiter</code> will be replaced
	 * by <code>replacement</code> character.
	 * @param file - a file to be written.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws CodeFileException in case of any mistakes in during the process.
	 */
	public void codeFile(File file) throws NullPointerException, CodeFileException 
	{
		if (file == null) throw new NullPointerException("null value in Phrases.codeFile()");
		
		try
		{
			StringBuilder sb = new StringBuilder();
			for (Phrase each : this) 
			{
				sb.append(each.getId()).append(delimiter);
				sb.append(each.getPhrase()            .replace(delimiter, replacement)).append(delimiter);
				sb.append(each.getTransl() .toString().replace(delimiter, replacement)).append(delimiter);
				sb.append(each.getComment().toString().replace(delimiter, replacement)).append(delimiter);
				sb.append(each.getTag()    .toString().replace(delimiter, replacement)).append(delimiter);
			}
			Common.putFileContent(file, sb);
		}
		catch (Exception e) 
		{
			CodeFileException ee = new CodeFileException(e.getClass() + " exception in Phrases.codeFile()");
			ee.initCause(e);
			throw ee;
		}
	}
	
//
// Exceptions
//
	
	public class ParseFileException extends RuntimeException
	{
		public final static long serialVersionUID = 1L;
		public ParseFileException() {}
		public ParseFileException(String text) { super(text); }
	}
	
	public class CodeFileException extends RuntimeException
	{
		public final static long serialVersionUID = 1L;
		public CodeFileException() {}
		public CodeFileException(String text) { super(text); }
	}

} // end Phrases