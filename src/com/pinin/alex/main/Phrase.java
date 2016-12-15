//  
//	This file is part of LangH.
//
//  LangH is a program that allows to keep foreign phrases and test yourself.
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

import java.io.Serializable;

/**
 * Element that represents a virtual foreign phrase and contains the identifier, the phrase itself
 * and sets of translations, comments, and tags.<br>
 * Attributes of this object:<br>
 * <blockquote>
 * <code>int id</code> - an attribute for easier representation this element in other objects;<br>
 * It case of using the default constructor it equals 0;<br>
 * <code>String phrase</code> - the main attribute that contains the phrase itself. 
 * It can be empty in case of using the default constructor only;<br>
 * <code>Term transl</code> - the set of translations;<br>
 * <code>Term comment</code> - the set of comments;<br>
 * <code>Term tag</code> - the set of tags;<br>
 * </blockquote><p>
 */
public class Phrase implements Comparable <Phrase>, Serializable
{

//
// Variables
//

	/** An attribute for easier representation this element in other objects */
	private Integer id;
	
	/** The main attribute that contains the phrase itself.
	 * It can be empty in case of using the default constructor only */
	private String phrase;
	
	/** The set of translations */
	private Term transl;
	
	/** The set of comments */
	private Term comment;
	
	/** The set of tags */
	private Term tag;
	
	/** Generated serial version ID */
	private static final long serialVersionUID = -4473683568332091424L;

//
// Constructors
//

	/**
	 * Constructor.<br>
	 * <code>id<code> = 0, other attributes values are empty
	 */
	public Phrase() 
	{
		id = 0;
		phrase = new String();
		transl = new Term();
		comment = new Term();
		tag = new Term();
	}
	
	/**
	 * Constructor<br>
	 * In case of <code>null</code> values will be initialized by default values.
	 * @param id - the new <code>id</code> value
	 * @param phrase - the new <code>phrase</code> value. Can not be empty or <code>null</code>.
	 * @param transl - the new <code>transl</code> value. Can be empty or <code>null</code>.
	 * @param comment - the new <code>comment</code> value. Can be empty or <code>null</code>.
	 * @param tag - the new <code>tag</code> value. Can be empty or <code>null</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IllegalArgumentException if the new <code>phrase</code> value is empty.
	 */
	public Phrase(Integer id, String phrase, Term transl, Term comment, Term tag) 
			throws NullPointerException, IllegalArgumentException
	{
		if (id == null || phrase  == null || transl  == null || comment == null || tag == null)
			throw new NullPointerException("null value in Phrase constructor");
		
		if (phrase.isEmpty()) 
			throw new IllegalArgumentException("phrase value is empty in Phrase constructor");
		
		this.id = id;
		this.phrase = phrase;
		this.transl = transl;
		this.comment = comment;
		this.tag = tag;
	}
	
//
// Methods
// 
	
	/**
	 * Returns the <code>id</code> attribute value.
	 * @return the <code>id</code> attribute value.
	 */
	public int getId() 
	{
		return id;
	}
	
	/**
	 * Sets the <code>id</code> attribute value.
	 * @param id - the new value.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setId(Integer id) throws NullPointerException
	{
		if (id == null) throw new NullPointerException("null value in Phrase.setid()");
		this.id = id;
	}
	
	/**
	 * Returns the <code>phrase</code> attribute value.
	 * @return the <code>phrase</code> attribute value.
	 */
	public String getPhrase() 
	{
		return phrase;
	}
	
	/**
	 * Sets the <code>phrase</code> attribute value.
	 * @param phrase - the new value. Can not be empty or <code>null</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws IllegalArgumentException if the new <code>phrase</code> value is empty.
	 */
	public void setPhrase(String phrase) throws NullPointerException, IllegalArgumentException 
	{
		if (phrase == null) throw new NullPointerException("null value in Phrase.setPhrase()");
		if (phrase.isEmpty()) throw new IllegalArgumentException("phrase value is empty in Phrase.setPhrase()");
		this.phrase = phrase;
	}
	
	/**
	 * Returns the <code>transl</code> attribute value.
	 * @return the <code>transl</code> attribute value.
	 */
	public Term getTransl() 
	{
		return (Term) transl.clone();
	}
	
	/**
	 * Sets the <code>transl</code> attribute value.<br>
	 * In case of <code>null</code> value will be initialized by default value.
	 * @param transl - the new value. Can be empty or <code>null</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setTransl(Term transl) throws NullPointerException
	{
		if (transl == null) throw new NullPointerException("null value in Phrase.setTransl()");
		this.transl = transl;
	}
	
	/**
	 * Add the new element to the <code>transl</code> attribute.
	 * @param transl - the new element to be added.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void addTransl(Term transl) throws NullPointerException
	{
		if (transl == null) throw new NullPointerException("null value in Phrase.addTransl()");
		for (String each : transl) this.transl.add(each);
	}
	
	/**
	 * Returns the <code>comment</code> attribute value.
	 * @return the <code>comment</code> attribute value.
	 */
	public Term getComment() 
	{
		return (Term) comment.clone();
	}
	
	/**
	 * Sets the <code>comment</code> attribute value.<br>
	 * In case of <code>null</code> value will be initialized by default value.
	 * @param comment - the new value. Can be empty or <code>null</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setComment(Term comment) throws NullPointerException
	{
		if (comment == null) throw new NullPointerException("null value in Phrase.setComment()");
		this.comment = comment;
	}
	
	/**
	 * Add the new element to the <code>comment</code> attribute.
	 * @param comment - the new element to be added.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void addComment(Term comment) throws NullPointerException
	{
		if (comment == null) throw new NullPointerException("null value in Phrase.addComment()");
		for (String each : comment) this.comment.add(each);
	}
	
	/**
	 * Returns the <code>tag</code> attribute value.
	 * @return the <code>tag</code> attribute value.
	 */
	public Term getTag() 
	{
		return (Term) tag.clone();
	}
	
	/**
	 * Sets the <code>tag</code> attribute value.<br>
	 * In case of <code>null</code> value will be initialized by default value.
	 * @param tag - the new value. Can be empty or <code>null</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void setTag(Term tag) throws NullPointerException
	{
		if (tag == null) throw new NullPointerException("null value in Phrase.setTag()");
		this.tag = tag;
	}
	
	/**
	 * Add the new element to the <code>tag</code> attribute.
	 * @param tag - the new element to be added.
	 * @throws NullPointerException in case of <code>null</code> value.
	 */
	public void addTag(Term tag) throws NullPointerException
	{
		if (tag == null) throw new NullPointerException("null value in Phrase.addTag()");
		for (String each : tag) this.tag.add(each);
	}
	
	/**
	 * Returns true if this object contains no elements.<br>
	 * The <code>id</code> element is not considered.
	 * @return true if this object contains no elements otherwise false
	 */
	public boolean isEmpty() 
	{
		return phrase.isEmpty() && transl.isEmpty() && comment.isEmpty() && tag.isEmpty();
	}
	
//
// Implementation Comparable
//
	
	@Override
	public int compareTo(Phrase other) 
	{
		return this.phrase.compareTo(other.phrase);
	}
	
//	
// Extending Object
//	
	
	@Override
	public int hashCode() 
	{
		return 7 * id
			+ 13 * phrase.hashCode()
			+ 17 * transl.hashCode()
			+ 19 * comment.hashCode()
			+ 23 * tag.hashCode();
	}
	
	@Override
	public String toString() 
	{
		return getClass().getName()
			+ "[id: "      + id      + "]"
			+ "[phrase: "  + phrase  + "]"
			+ "[transl: "  + transl  + "]"
			+ "[comment: " + comment + "]"
			+ "[tag: "     + tag     + "]";
	}

	@Override
	public boolean equals(Object other) 
	{
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;
		
		Phrase aOther = (Phrase) other;
		
		return id == aOther.id
				&& phrase.equals(aOther.phrase)
				&& transl.equals(aOther.transl)
				&& comment.equals(aOther.comment)
				&& tag.equals(aOther.tag);
	}
	
} // end Phrase