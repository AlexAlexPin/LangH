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
 * Contains a set of exercises and allow to apply them to a data of PhraseSet objects.
 */
public class Task 
{
	// The database for exercises. This class takes phrases from this database and
	// puts them into the exercises. Elements are being removed from the database random way
	// while the database is not empty.
	private ArrayList<PhraseSet> data;
	
	// The phrase that is being used in one if exercise right now.
	private PhraseSet inTesting;
	
	// Lets to choose a type of exercise.
	private TaskEnum option;
	
	// The current attempt of doing the current exercise.
	private int attempt;
	
	// The length of the prompt as an amount of characters.
	private int helpLength;
	
	// The value of increasing of helpLength.
	private int helpOpenLength;
	
	// The number of mistakes during all exercises.
	private int numOfMistakes;

	// If inTesting has been put back to the database or not.
	// PhraseSet is being put back if there is a mistake in the exercise with this phrase/
	private boolean added;
	
	/**
	 * Constructor
	 * @param data - the database for exercises. This method makes a copy of this data. This
	 * class takes phrases from this copy and puts them into the exercises. Elements are 
	 * being removed from the copy random way while it is not empty.
	 */
	public Task(ArrayList<PhraseSet> data) throws IllegalArgumentException {
		CheckValueHelper.checkNull(data);

		this.data = new ArrayList<>();
		this.data.addAll(data);

		helpOpenLength = 3;
		numOfMistakes = 0;
		added = false;
	}
	
	/**
	 * Runs one of exercises.
	 * @param option - an option to choose the task.
	 */
	public String doExercise(TaskEnum option) throws ExerciseException {
		try {
			if (data.size() == 0) return "";
			
			attempt = 1;
			helpLength = helpOpenLength;
			this.option = option;		
			inTesting = Common.getRandomOne(data);
			
			switch (option) {
				case trainByPhrase:
					return inTesting.getPhrase();
				case trainByTranslation:
					return inTesting.getTransl().toString();
				default:
					return "";
			}
		}
		catch (Exception e) {
			ExerciseException ee = new ExerciseException();
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Step by step opens hidden translations or phrases.
	 */
	public String help() throws IllegalArgumentException, ExerciseException {
		CheckValueHelper.checkNull(inTesting);
		try {
			String prompt = "";
			switch (option) {
				case trainByPhrase:
					prompt = inTesting.getTransl().toString();
					break;
				case trainByTranslation:
					prompt = inTesting.getPhrase();
					break;
			}
			int length = prompt.length();
			if (helpLength >= length) helpLength = length;
			
			String toReturn = prompt.substring(0, helpLength);	// show the prompt
			helpLength += helpOpenLength;
			return toReturn;
		}
		catch (Exception e) {
			ExerciseException ee = new ExerciseException();
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Enters and checks an answer.
	 */
	public TaskResultEnum enter(String answer) throws IllegalArgumentException, ExerciseException {
		CheckValueHelper.checkNull(answer);
		if (inTesting == null) throw new ExerciseException("Exercise has not been started");
		
		boolean correctAnswer = false;
		switch (option) {
		case trainByPhrase:
			Term transl = inTesting.getTransl();
			for (String each : transl) {
				if (answer.equalsIgnoreCase(each)) {
					correctAnswer = true;
					break;
				}
			}
			break;
		case trainByTranslation:
			correctAnswer = answer.equalsIgnoreCase(inTesting.getPhrase());
			break;
		}
		
		if (correctAnswer) {
			added = false;
			return data.size() == 0 ? TaskResultEnum.finalCorrectAnswer : TaskResultEnum.correctAnswer;
		}
		else {
			if (!added) data.add(inTesting);
			added = true;
			attempt++;
			numOfMistakes++;
			return TaskResultEnum.wrongAnswer;
		}
	}

	public PhraseSet getPhrase() {
		return new PhraseSet(inTesting.getId(), inTesting.getPhrase(),
				inTesting.getTransl(), inTesting.getComment(), inTesting.getTag());
	}

	public TaskEnum getOption() {
		return option;
	}

	public int getAttempt() {
		return attempt;
	}

	public int getRestOfPhrases() {
		return data.size() + 1;
	}

	public int getNumOfMistakes() {
		return numOfMistakes;
	}

	private class ExerciseException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
		ExerciseException() {}
		ExerciseException(String text) { super(text); }
	}
}
