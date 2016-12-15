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

package com.pinin.alex.gui;

public interface PhraseFieldsContainer
{
	
	/**
	 * Returns fields content as a <code>String[5]</code> that contains:<br>
	 * [0] - id field's content;<br>
	 * [1] - phrase field's content;<br>
	 * [2] - transl field's content;<br>
	 * [3] - comment field's content;<br>
	 * [4] - tag field's content.
	 * @return fields content as a <code>String[5]</code>.
	 */
	String[] getFields();
	
} // end PhraseFieldsContainer
