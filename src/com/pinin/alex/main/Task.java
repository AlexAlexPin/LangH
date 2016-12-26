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

import java.util.*;

/**
 * Contains a set of exercises and allow to apply them to a list of <code>PhraseSet</code> objects.<br>
 * Attributes of this object:
 * <blockquote>
 * <code>ArrayList<'PhraseSet> list</code> - the database for exercises.
 * This class takes phrases from this database and puts them into the exercises. 
 * Element are removed from the database random way while the database is not empty;<br>
 * <code>PhraseSet testing</code> - the phrase what is being used in one if exercise right now;<br>
 * <code>int option</code> - lets to choose a type of exercise;<br>
 * <code>int attempt</code> - the attempt of doing the current exercise;<br>
 * <code>int helpLength</code> - the length of the prompt in number of characters;<br>
 * <code>int helpOpenLenght</code> - the value of increasing of <code>helpLength</code>;<br>
 * <code>int numOfMistakes</code> - the number of mistakes during all exercises;<br>
 * <code>boolean added</code> - if <code>testing</code> has been put back to the database or not.
 * PhraseSet is being put back if there is a mistake in the exercise with this phrase;<br>
 * <code>final int OPTION_BY_PHRASE = 1</code> - an option to choose "By phrase" exercise;<br>
 * <code>final int OPTION_BY_TRANSL = 2</code> - an option to choose "By translation" exercise;<br>
 * <code>final int WRONG_ANSW = -1</code> - a result if an answer is wrong;<br>
 * <code>final int RIGHT_ANSW = 0</code> - a result if an answer is correct and there are more questions;<br>
 * <code>final int RIGHT_ANSW_FIN = 1</code> - a result if an answer is correct and there are not more questions.<br>
 * </blockquote><p>
 * Algorithm:<br>
 * 1. Initialize this object using <code>ArrayList<'PhraseSet> via constructor</code>;<br>
 * 2. Optionally: set the value of increasing of a prompt via <code>setHelpOpenLenght()</code>;<br>
 * 3. Call <code>doExercise(int option)</code> using one of options;<br>
 * 4. Optionally: call <code>help()</code> to findContaining a prompt;<br>
 * 5. Call <code>enter(String answer)</code> using <code>String</code> to check answer;<br>
 * 6. Check <code>enter(String answer)</code> result.
 */
public class Task 
{
//
// Variables
//
	
	/**
	 * The database for exercises. This class takes phrases from this database and
	 * puts them into the exercises. Elements are being removed from the database random way
	 * while the database is not empty.
	 */
	private ArrayList<PhraseSet> list;
	
	/** The phrase what is being used in one if exercise right now. */
	private PhraseSet testing;
	
	/** Lets to choose a type of exercise. */
	private int option;
	
	/** The current attempt of doing the current exercise. */
	private int attempt;
	
	/** The length of the prompt in number of characters. */
	private int helpLength;
	
	/** The value of increasing of <code>helpLength</code>. */
	private int helpOpenLenght;
	
	/** The number of mistakes during all exercises. */
	private int numOfMistakes;

	/**
	 * If <code>testing</code> has been put back to the database or not.
	 * PhraseSet is being put back if there is a mistake in the exercise with this phrase.
	 */
	private boolean added;
	
	// options
	
	/** An option to choose "By phrase" exercise. */
	public final static int OPTION_BY_PHRASE = 1;
	
	/** An option to choose "By translation" exercise. */
	public final static int OPTION_BY_TRANSL = 2;
	
	/** A result if an answer is wrong. */
	public static final int WRONG_ANSW = -1;
	
	/** A result if an answer is correct and there are more questions. */
	public static final int RIGHT_ANSW = 0;
	
	/** A result if an answer is correct and there are not more question. */
	public static final int RIGHT_ANSW_FIN = 1;
	
//
// Constructors
//
	
	@SuppressWarnings("unused")
	private Task() {}
	
	/**
	 * Constructor
	 * @param list - the database for exercises. This method makes a copy of this list. This 
	 * class takes phrases from this copy and puts them into the exercises. Elements are 
	 * being removed from the copy random way while it is not empty.
	 * @throws NullPointerException if the specified list is <code>null</code>
	 */
	public Task(ArrayList<PhraseSet> list) throws NullPointerException
	{
		if (list == null) throw new NullPointerException("null value in Task constructor");
		
		this.list = new ArrayList<PhraseSet>();
		for (PhraseSet each : list)
		{
			this.list.add(each);
		}
		
		helpOpenLenght = 3;
		numOfMistakes = 0;
		added = false;
	}

//
// Methods
//
	
	/**
	 * Runs one of exercises.
	 * @param option - an option to choose the task.<br>
	 * <code>Task.OPTION_BY_PHRASE</code> - by phrase exercise.<br>
	 * <code>Task.OPTION_BY_TRANSL</code> - by translation exercise.<br>
	 * @return your task as <code>String</code>
	 * @throws ExcerciseException in case of mistakes during the exercise
	 */
	public String doExercise(int option) throws ExcerciseException 
	{
		try 
		{
			if (list.size() == 0) return "";
			
			attempt = 1;
			helpLength = helpOpenLenght;
			this.option = option;		
			testing = Common.getRandomOne(list);
			
			switch (option) 
			{
			case OPTION_BY_PHRASE: return testing.getPhrase();
			case OPTION_BY_TRANSL: return testing.getTransl().toString();
			default:               return "";
			}
		}
		catch (Exception e) 
		{
			ExcerciseException ee = new ExcerciseException(e.getClass() 
					+ " exception in Task.doExercise()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Step by step opens hidden translations or phrases
	 * @return return the tip as a part of answer. Step by step tip is getting longer.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws ExcerciseException in case of mistakes during the exercise.
	 */
	public String help() throws NullPointerException, ExcerciseException 
	{
		if (testing == null) throw new NullPointerException("null value in Task.help()");
		
		try 
		{
			String prompt = "";
			switch (option) 
			{
			case OPTION_BY_PHRASE: 
				prompt = testing.getTransl().toString(); 
				break;
			case OPTION_BY_TRANSL: 
				prompt = testing.getPhrase();            
				break;
			}
			
			int length = prompt.length();
			if (helpLength >= length) helpLength = length;
			
			String toReturn = prompt.substring(0, helpLength);	// show the prompt
			helpLength += helpOpenLenght;
			return toReturn;
		}
		catch (Exception e)
		{
			ExcerciseException ee = new ExcerciseException(e.getClass() 
					+ " exception in Task.help()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Enters and checks an answer
	 * @param answer - the answer to be checked
	 * @return the result of the examination as a variable<br>
	 * <code>Task.RIGHT_ANSW_FIN</code> (or 1) - the answer is correct an it was the last question<br>
	 * <code>Task.RIGHT_ANSW</code> (or 0) - the answer is correct and there are more questions<br>
	 * <code>Task.WRONG_ANSW</code> (or -1) - the answer is incorrect<br>
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws ExcerciseException if this method has been called before <code>doExercise()</code> 
	 * or the specified <code>String</code> is <code>null</code>
	 */
	public int enter(String answer) throws NullPointerException, ExcerciseException 
	{
		if (testing == null) throw new ExcerciseException("exercise has not been started in Task.enter()");
		if (answer == null)  throw new NullPointerException("null value in Task.enter()");
		
		boolean correct = false;
		
		switch (option) 
		{
		case OPTION_BY_PHRASE:
			Term transl = testing.getTransl();
			for (String each : transl)
			{
				if (answer.equalsIgnoreCase(each)) 
				{
					correct = true;
					break;
				}
			}
			break;
		case OPTION_BY_TRANSL:
			correct = answer.equalsIgnoreCase(testing.getPhrase());
			break;
		}
		
		if (correct) 
		{
			added = false;
			
			if (list.size() == 0) // all exercises have been done
			{
				return RIGHT_ANSW_FIN;
			}
			else // there are more exercises
			{
				return RIGHT_ANSW;
			}
		}
		else // wrong answer
		{ 
			if (!added) list.add(testing);
			added = true;
			attempt++;
			numOfMistakes++;
			return WRONG_ANSW;
		}
	}
	
	/**
	 * Returns the copy of <code>PhraseSet</code> object that is being processed now
	 * @return the copy of <code>PhraseSet</code> object that is being processed now
	 */
	public PhraseSet getPhrase()
	{
		return new PhraseSet(testing.getId(), testing.getPhrase(),
				testing.getTransl(), testing.getComment(), testing.getTag());
	}
	
	/**
	 * Sets the new value to <code>helpOpenLenght</code>. 
	 * @param i - the new value.
	 */
	public void setHelpOpenLenght(int i) 
	{
		helpLength = i;
	}
	
	/**
	 * Returns the current option.
	 * @return the current option.
	 */
	public int getOption() 
	{
		return option;
	}
	
	/**
	 * Returns the current number of attempt within the current question
	 * @return the current number of attempt within the current question
	 */
	public int getAttempt() 
	{
		return attempt-1;
	}
	
	/**
	 * Returns the current value to <code>helpOpenLenght</code>.
	 * @return the current value to <code>helpOpenLenght</code>.
	 */
	public int getHelpOpenLenght() 
	{
		return helpOpenLenght;
	}
	
	/**
	 * Returns the rest of questions to be answered
	 * @return the rest of questions to be answered
	 */
	public int getRestOfPhrases() 
	{
		return list.size()+1;
	}
	
	/**
	 * Returns the number of mistakes within whole questions
	 * @return the number of mistakes within whole questions
	 */
	public int getNumOfMistakes() 
	{
		return numOfMistakes;
	}
	
//
// Exceptions
//
	
	public class ExcerciseException extends RuntimeException 
	{
		private static final long serialVersionUID = 1L;
		public ExcerciseException() {};
		public ExcerciseException(String text) { super(text); }
	}
	
} // end Task