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

/**
 * A set of <code>ReplSet</code> elements.<br>
 * Attributes of this object:<br>
 * <blockquote>
 * <code>HashSet<'ReplSet> set</code> - a set of <code>ReplSet</code> elements;<br>
 * <code>texts</code> - list of <code>text</code> of <code>ReplSet</code> elements.<br>
 * </blockquote>
 */
public class Replacer 
{
//
// Variables
//
	
	/** A set of <code>ReplSet</code> elements */
	private HashSet<ReplSet> set;
	
	/** A list of <code>text</code> of <code>ReplSet</code> elements */
	private HashSet<String> texts;

//
// Constructors
//
	
	/**
	 * Constructor
	 */
	public Replacer() 
	{
		set   = new HashSet<ReplSet>();
		texts = new HashSet<String>();
	}
	
//
// Methods
//
	
	/**
	 * Adds the specified element to this object if this <code>texts</code> did not already 
	 * contain the specified element's <code>text</code>.
	 * @param e - a new object to be added
	 * @return <code>true</code> if the specified element has been added
	 * @throws NullPointerException if the specified element is <code>null</code>
	 */
	public boolean add(ReplSet e) throws NullPointerException 
	{
		if (e == null) throw new NullPointerException("null value in Replacer.add()");
		String s = e.getText();
		if (!texts.add(s)) return false;
		return set.add(e);
	}
	
	/**
	 * Removes the specified element to this object.
	 * @param e - an object to be removed
	 * @return <code>true</code> if the specified element has been removed
	 * @throws NullPointerException if the specified element is <code>null</code>
	 */
	public boolean remove(ReplSet e) throws NullPointerException 
	{
		if (e == null) throw new NullPointerException("null value in Replacer.remove()");
		String s = e.getText();
		if (!texts.remove(s)) return false;
		return set.remove(e);
	}
	
	/**
	 * Searches the <code>ReplSet</code> object with the <code>text</code> value that
	 * equals the specified <code>String</code> and returns it.
	 * @param text - a <code>text</code> value of the <code>ReplSet</code> object to be found
	 * @return found <code>ReplSet</code> object or empty <code>ReplSet</code> object
	 * @throws NullPointerException if the specified <code>String</code> is <code>null</code>
	 */
	public ReplSet get(String text) throws NullPointerException 
	{
		if (text == null) throw new NullPointerException("null value in Replacer.get()");

		ReplSet rs = null;
		for (ReplSet each : set) 
		{
			if (text.equals(each.getText())) 
			{
				rs = each;
				return rs;
			}
		}
		return new ReplSet();
	}
	
	/**
	 * Searches the specified text among all <code>ReplSet</code> elements, and returns
	 * the required replacement using the specified option value.
	 * @param text - a text to get replacement.
	 * @param option - an option to choose replacement.
	 * @return the required replacement or an empty <code>String</code> if there is no such
	 * text among all <code>ReplSet</code> elements.
	 * @throws NullPointerException if the text is <code>null</code>
	 */
	public String replace(String text, Integer option) throws NullPointerException 
	{
		if (text == null || option == null) 
			throw new NullPointerException("null value in Replacer.replace()");
		
		ReplSet rs = null;
		for (ReplSet each : set) 
		{
			if (text.equals(each.getText())) 
			{
				rs = each;
				break;
			}
		}
		if (rs == null) return "";
		
		return rs.getReplacement(text, option);
	}
	
	/**
	 * Parses the specified <code>File</code> to this object.
	 * @param file - a file to be parsed.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void parseFile(File file) throws NullPointerException
	{
		if (file == null) throw new NullPointerException("null value in Replacer.parseFile()");
		
		// get lines. Each line is a set of a text and its replacements
			
		CharSequence fileContent = Common.getFileContent(file);	
		LinkedList<StringBuilder> lines = new LinkedList<StringBuilder>();
		StringBuilder line = new StringBuilder();
			
		for (int i=0; i<fileContent.length(); i++) 
		{
			final char each = fileContent.charAt(i);
			if (each == '\n') // end line
			{
				lines.add(line);
				line = new StringBuilder();
				continue;
			}
			line.append(each);
		}
			
		for (StringBuilder each : lines) // parse each line
		{
			ReplSet rs = this.parse(each);
			this.add(rs);
		}
	}
	
	/**
	 * Codes this object to the specified <code>File</code>.
	 * @param file - a file to be coded.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void codeFile(File file) throws NullPointerException 
	{
		if (file == null) throw new NullPointerException("null value in Replacer.codeFile()");
		
		StringBuilder sb = new StringBuilder();
			
		for (ReplSet each : this.set) 
		{
			sb.append(each.getText()).append(": ");
				
			final String[] replacements = each.getReplacements();
			for (String eachRepl : replacements)
			{
				sb.append(eachRepl).append("; ");
			}
			sb.append("\n");
		}
		Common.putFileContent(file, sb);
	}
	
	/**
	 * Parses the specified <code>CharSequence</code> to this object.
	 * @param csq - a <code>CharSequence</code> to be parsed.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void parseSequence(CharSequence csq) throws NullPointerException
	{
		if (csq == null) throw new NullPointerException("null value in Replacer.parseSequence()");
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<csq.length(); i++)
		{
			char c = csq.charAt(i);
			sb.append(c);
			if (c == '\n')
			{
				ReplSet rs = parse(sb);
				add(rs);
				sb = new StringBuilder();
			}
		}
	}
	
	/**
	 * Parses <code>ReplSet</code> object from the specified <code>StringBuilder</code>.<br>
	 * Structure of the specified source:<br>
	 * <i>
	 * text#0: replacement#0; replacement#1; ... replacement#10;<br>
	 * text#1: replacement#0; replacement#1; ... replacement#10;<br>
	 * ...<br>
	 * text#99: replacement#0; replacement#1; ... replacement#10;<br>
	 * </i>
	 * @param csq - the data source
	 * @return a parsed <code>ReplSet</code> object
	 */
	private ReplSet parse(CharSequence csq) 
	{
		StringBuilder text = new StringBuilder();
		StringBuilder repl = new StringBuilder();
		LinkedList<String> replacements = new LinkedList<String>();
			
		boolean isText = true;
			
		for (int i=0; i<csq.length(); i++) 
		{
			final char each = csq.charAt(i);
				
			switch (each) 
			{
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
			else        repl.append(each);
		}
			
		String[] replacementsArr = new String[replacements.size()];
		int i=0;
		for (String each : replacements) 
		{
			replacementsArr[i++] = each;
		}
		
		return new ReplSet(text.toString(), replacementsArr);
	}

} // end Replacer