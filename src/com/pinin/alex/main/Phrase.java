//  
//	This file is part of LangH.
//
//  LangH is a program that allows to keep foreign phrases and test yourself.
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
@SuppressWarnings("WeakerAccess")
public class Phrase implements Comparable <Phrase>
{
	// An attribute for easier representation this element in other objects
	private Integer id = 0;
	
	// The main attribute that contains the phrase itself.
	// It can be empty in case of using the default constructor only
	private String phrase = "";
	
	// The set of translations
	private Term transl = new Term();
	
	// The set of comments
	private Term comment = new Term();
	
	// The set of tags
	private Term tag = new Term();

	public Phrase() {}
	
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
	public Phrase(Integer id, String phrase, Term transl, Term comment, Term tag) throws IllegalArgumentException {
		setId(id);
        setPhrase(phrase);
        setTransl(transl);
        setComment(comment);
        setTag(tag);
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) throws NullPointerException{
		if (id == null) throw new NullPointerException("ID is null");
		this.id = id;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) throws IllegalArgumentException {
		if (phrase == null || phrase.isEmpty()) throw new IllegalArgumentException("Phrase is null or empty");
		this.phrase = phrase;
	}

	public Term getTransl() {
		return (Term) transl.clone();
	}

	public void setTransl(Term transl) throws IllegalArgumentException {
		if (transl == null) throw new IllegalArgumentException("Translation is null");
		this.transl = transl;
	}

	public void addTransl(Term transl) throws IllegalArgumentException {
		if (transl == null) throw new IllegalArgumentException("Translation is null");
        this.transl.addAll(transl);
	}

	public Term getComment() {
		return (Term) comment.clone();
	}

	public void setComment(Term comment) throws IllegalArgumentException {
		if (comment == null) throw new IllegalArgumentException("Comment is null");
		this.comment = comment;
	}

	public void addComment(Term comment) throws IllegalArgumentException {
		if (comment == null) throw new IllegalArgumentException("Comment is null");
        this.comment.addAll(comment);
	}

	public Term getTag() {
		return (Term) tag.clone();
	}

	public void setTag(Term tag) throws IllegalArgumentException {
		if (tag == null) throw new IllegalArgumentException("Tag is null");
		this.tag = tag;
	}

	public void addTag(Term tag) throws IllegalArgumentException {
        if (tag == null) throw new IllegalArgumentException("Tag is null");
        this.tag.addAll(tag);
	}

	public boolean isEmpty() {
		return phrase.isEmpty() && transl.isEmpty() && comment.isEmpty() && tag.isEmpty();
	}

	@Override
	public int compareTo(Phrase other) {
		return this.phrase.compareTo(other.phrase);
	}

	@Override
	public int hashCode() {
		return 7 * id
			+ 13 * phrase.hashCode()
			+ 17 * transl.hashCode()
			+ 19 * comment.hashCode()
			+ 23 * tag.hashCode();
	}
	
	@Override
	public String toString() {
		return getClass().getName()
			+ "[id: "      + id      + "]"
			+ "[phrase: "  + phrase  + "]"
			+ "[transl: "  + transl  + "]"
			+ "[comment: " + comment + "]"
			+ "[tag: "     + tag     + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false;
		
		Phrase aOther = (Phrase) other;
		
		return id.equals(aOther.id)
				&& phrase.equals(aOther.phrase)
				&& transl.equals(aOther.transl)
				&& comment.equals(aOther.comment)
				&& tag.equals(aOther.tag);
	}
}
