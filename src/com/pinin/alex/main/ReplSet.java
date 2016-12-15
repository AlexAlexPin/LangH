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

/**
 * Contains the couple of a text and a set of replacements of this text.
 * The number of replacements is limited to <code>ReplSet.LIMIT</code> value.<br>
 * Attributes of this class:<br>
 * <blockquote>
 * - <code>String text</code> - the text to be replaced;<br>
 * - <code>String[] replacements</code> - the set of replacements for the text;<br>
 * - <code>final static int LIMIT = 10</code> - the limit of replacements for the text;<br>
 * - <code>final static int REPL_0 = 0</code> - an option to get the <b>0</b> replacement for the text;<br>
 * - <code>final static int REPL_1 = 1</code> - an option to get the <b>1</b> replacement for the text;<br>
 * - <code>final static int REPL_2 = 2</code> - an option to get the <b>2</b> replacement for the text;<br>
 * - <code>final static int REPL_3 = 3</code> - an option to get the <b>3</b> replacement for the text;<br>
 * - <code>final static int REPL_4 = 4</code> - an option to get the <b>4</b> replacement for the text;<br>
 * - <code>final static int REPL_5 = 5</code> - an option to get the <b>5</b> replacement for the text;<br>
 * - <code>final static int REPL_6 = 6</code> - an option to get the <b>6</b> replacement for the text;<br>
 * - <code>final static int REPL_7 = 7</code> - an option to get the <b>7</b> replacement for the text;<br>
 * - <code>final static int REPL_8 = 8</code> - an option to get the <b>8</b> replacement for the text;<br>
 * - <code>final static int REPL_9 = 9</code> - an option to get the <b>9</b> replacement for the text.<br>
 * </blockquote>
 */
public class ReplSet 
{
//
// Variables
//
	
	/** The text to be replaced */
	private String text;
	
	/** The set of replacements for the text */
	private String[] replacements;
	
	/** The limit of replacements for the text */
	public final static int LIMIT = 10;
	
	/** An option to get the <b>0</b> replacement for the text */
	public final static int REPL_0 = 0;
	
	/** An option to get the <b>1</b> replacement for the text */
	public final static int REPL_1 = 1;
	
	/** An option to get the <b>2</b> replacement for the text */
	public final static int REPL_2 = 2;
	
	/** An option to get the <b>3</b> replacement for the text */
	public final static int REPL_3 = 3;
	
	/** An option to get the <b>4</b> replacement for the text */
	public final static int REPL_4 = 4;
	
	/** An option to get the <b>5</b> replacement for the text */
	public final static int REPL_5 = 5;
	
	/** An option to get the <b>6</b> replacement for the text */
	public final static int REPL_6 = 6;
	
	/** An option to get the <b>7</b> replacement for the text */
	public final static int REPL_7 = 7;
	
	/** An option to get the <b>8</b> replacement for the text */
	public final static int REPL_8 = 8;
	
	/** An option to get the <b>9</b> replacement for the text */
	public final static int REPL_9 = 9;

//
// Constructors
//
	
	/**
	 * Constructor.<br>
	 * Initializes <code>text</code> and <code>replacements</code> 
	 * with empty <code>String</code> values.
	 */
	public ReplSet() 
	{
		text = "";
		replacements = new String[0];
	}
	
	/**
	 * Constructor.<br>
	 * Initializes <code>text</code> and <code>replacements</code> with the specified data.<br>
	 * <b>Note</b> that the length of the specified array must be less than or equally to
	 * <code>ReplSet.LIMIT</code> value.
	 * @param text - a new value for <code>text</code>
	 * @param arr - a new value for <code>replacements</code>
	 * @throws NullPointerException if at least one of specified values is <code>null</code>
	 * @throws IndexOutOfBoundsException if the length of the specified array is more than
	 * <code>ReplSet.LIMIT</code> value.
	 */
	public ReplSet(String text, String[] replacements) 
			throws NullPointerException, IndexOutOfBoundsException 
	{
		if (text == null || replacements == null) 
			throw new NullPointerException("null value in ReplSet constructor");
		
		int length = replacements.length;
		if (length > LIMIT)
			throw new IndexOutOfBoundsException("limit: " + LIMIT + ", array length: " 
					+ length + " in ReplSet constructor");
		
		this.text = text;
		this.replacements = replacements;
	}
	
//
// Methods
//

	/**
	 * Returns <code>text</code> value.
	 * @return <code>text</code> value.
	 */
	public String getText() 
	{
		return this.text;
	}
	
	/**
	 * Sets <code>text</code> value.<br>
	 * @param text - a new value
	 * @throws NullPointerException if the specified value is <code>null</code>
	 */
	public void setText(String text) throws NullPointerException 
	{
		if (text == null) throw new NullPointerException("null value in ReplSet.setText()");
		this.text = text;
	}
	
	/**
	 * Returns <code>replacements</code> value.
	 * @return <code>replacements</code> value.
	 */
	public String[] getReplacements() 
	{
		return this.replacements;
	}
	
	/**
	 * Sets <code>replacements</code> values.<br>
	 * <b>Note</b> that the length of the specified array must be less than or equally to
	 * <code>ReplSet.LIMIT</code> value.
	 * @param replacements - a new values
	 * @throws NullPointerException if the specified value is <code>null</code>
	 * @throws IndexOutOfBoundsException if the length of the specified array is more than
	 * <code>ReplSet.LIMIT</code> value.
	 */
	public void setReplacements(String[] replacements) 
			throws NullPointerException, IndexOutOfBoundsException 
	{
		if (replacements == null) 
			throw new NullPointerException("null value in ReplSet.setReplacements()");
		
		int length = replacements.length;
		if (length > LIMIT) 
			throw new IndexOutOfBoundsException("Limit: " + LIMIT + ", array length: " 
					+ length + " in ReplSet.setReplacements()");

		for (int i=0; i<replacements.length; i++)
			this.replacements[i] = replacements[i];
		
		for (int i=length; i<LIMIT; i++)
			this.replacements[i] = "";
	}
	
	/**
	 * Returns a replacement for the specified <code>String</code>.
	 * @param text - the <code>String</code> to be replaced
	 * @param option - an option to choose the replacement.
	 * <blockquote>
	 * A list of options:<br>
	 * - <code>ReplSet.REPL_0</code>;<br>
	 * - <code>ReplSet.REPL_1</code>;<br>
	 * - <code>ReplSet.REPL_2</code>;<br>
	 * - <code>ReplSet.REPL_3</code>;<br>
	 * - <code>ReplSet.REPL_4</code>;<br>
	 * - <code>ReplSet.REPL_5</code>;<br>
	 * - <code>ReplSet.REPL_6</code>;<br>
	 * - <code>ReplSet.REPL_7</code>;<br>
	 * - <code>ReplSet.REPL_8</code>;<br>
	 * - <code>ReplSet.REPL_9</code>.<br>
	 * </blockquote>
	 * @return a replacement for the specified <code>String</code>.
	 * @throws NullPointerException if the specified <code>String</code> is <code>null</code>
	 */
	public String getReplacement(String text, Integer option) throws NullPointerException 
	{
		if (text == null || option == null) 
			throw new NullPointerException("null value in ReplSet.getReplacement()");
		
		switch (option) 
		{
		case REPL_0: return replacements[0];			
		case REPL_1: return replacements[1];				
		case REPL_2: return replacements[2];			
		case REPL_3: return replacements[3];			
		case REPL_4: return replacements[4];			
		case REPL_5: return replacements[5];			
		case REPL_6: return replacements[6];			
		case REPL_7: return replacements[7];			
		case REPL_8: return replacements[8];			
		case REPL_9: return replacements[9];
		default:     return "";
		}
	}

	/**
	 * Returns <code>replacements</code> size.
	 * @return <code>replacements</code> size.
	 */
	public int size() 
	{
		return replacements.length;
	}
	
	/**
	 * Returns <code>true</code> if this object is empty
	 * @return <code>true</code> if this object is empty
	 */
	public boolean isEmpty() 
	{
		return text.isEmpty() && replacements.length == 0;
	}
	
//
// Extending Object
//
	
	@Override
	public int hashCode() 
	{
		return 7 * text.hashCode()
			+ 13 * replacements.hashCode();
	}
	
	@Override
	public String toString() 
	{
		return getClass().getName()
			+ "[text: "          + text          + "]"
			+ "[replacements: "  + replacements  + "]";
	}

	@Override
	public boolean equals(Object other) 
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;
		
		ReplSet aOther = (ReplSet) other;
		
		return text == aOther.text
				&& replacements == aOther.replacements;
	}
	
} // end ReplSet
