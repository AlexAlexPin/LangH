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

package com.pinin.alex.data;

/**
 * Element that represents a virtual foreign phrase and contains the identifier, the phrase itself
 * and sets of translations, comments, and tags.
 */
public class PhraseSet implements Comparable <PhraseSet>
{
	// An attribute for easier representation this element in other objects
	private Integer id = 0;
	
	// The data attribute that contains the phrase itself.
	// It can be empty in case of using the default constructor only
	private String phrase = "";
	
	// The set of translations
	private Term transl = new Term();
	
	// The set of comments
	private Term comment = new Term();
	
	// The set of tags
	private Term tag = new Term();

	public PhraseSet() {}

	public PhraseSet(int id, String phrase, Term transl, Term comment, Term tag) throws IllegalArgumentException {
		setId(id);
        setPhrase(phrase);
        setTransl(transl);
        setComment(comment);
        setTag(tag);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws NullPointerException{
		this.id = id;
	}

	public String getPhrase() {
		return phrase;
	}

	void setPhrase(String phrase) throws IllegalArgumentException {
		CheckValueHelper.checkNull(phrase);
		if (phrase.isEmpty()) throw new IllegalArgumentException("Value can not be empty");
		this.phrase = phrase;
	}

	Term getTransl() {
		return (Term) transl.clone();
	}

	void setTransl(Term transl) throws IllegalArgumentException {
		CheckValueHelper.checkNull(transl);
		this.transl = transl;
	}

	void addTransl(Term transl) throws IllegalArgumentException {
		CheckValueHelper.checkNull(transl);
        this.transl.addAll(transl);
	}

	Term getComment() {
		return (Term) comment.clone();
	}

	void setComment(Term comment) throws IllegalArgumentException {
		CheckValueHelper.checkNull(comment);
		this.comment = comment;
	}

	void addComment(Term comment) throws IllegalArgumentException {
		CheckValueHelper.checkNull(comment);
        this.comment.addAll(comment);
	}

	Term getTag() {
		return (Term) tag.clone();
	}

	void setTag(Term tag) throws IllegalArgumentException {
		CheckValueHelper.checkNull(tag);
		this.tag = tag;
	}

	void addTag(Term tag) throws IllegalArgumentException {
		CheckValueHelper.checkNull(tag);
        this.tag.addAll(tag);
	}

	boolean isEmpty() {
		return phrase.isEmpty() && transl.isEmpty() && comment.isEmpty() && tag.isEmpty();
	}

	@Override
	public int compareTo(PhraseSet other) {
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
		
		PhraseSet aOther = (PhraseSet) other;
		
		return id.equals(aOther.id)
				&& phrase.equals(aOther.phrase)
				&& transl.equals(aOther.transl)
				&& comment.equals(aOther.comment)
				&& tag.equals(aOther.tag);
	}
}
