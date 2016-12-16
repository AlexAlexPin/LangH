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

package com.pinin.alex.gui;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;
import com.pinin.alex.LangH;
import com.pinin.alex.main.*;

/**
 * Extends <code>AbstractTableModel</code>. Contains a <code>Phrases</code>
 * object as the database. Allows to edit this database.
 */
public class ModelPhrases extends AbstractTableModel 
{
//
// Variables
//

	/** The database */
	private Phrases data;
	
	/** The column with check boxes */
	private ArrayList<Boolean> checkbox;
	
	/** Columns names */
	private String[] columns;
	
	/** A path of this database. */
	private File dataPath;
	
	/** A folder to keep sounds.*/
	private String soundFolder;
	
	/** A list of existing sound records */
	private LinkedList<String> sounds;
	
	// icons for the button column
	
	/** An icon that shows a existing sound record */
	private Icon icon_sound;
	
	/** An icon that shows a not existing sound record */
	private Icon icon_no_sound;
	
	// column identifiers
	
	/** A column for check boxes */
	public final static int CHECK_COL = 0;
	
	/** A column that contains <code>id</code> attribute values of <code>Phrase</code> objects.*/
	public final static int ID_COL = 1;
	
	/** A column that contains <code>phrase</code> attribute values of <code>Phrase</code> objects.*/
	public final static int PHRASE_COL = 2;
	
	/** A column that contains <code>transl</code> attribute values of <code>Phrase</code> objects. */
	public final static int TRANSL_COL = 3;
	
	/** A column that contains <code>comment</code> attribute values of <code>Phrase</code> objects. */
	public final static int COMMENT_COL = 4;
	
	/** A column that contains <code>tag</code> attribute values of <code>Phrase</code> objects. */
	public final static int TAG_COL = 5;
	
	/** A column that contains sound playing buttons.*/
	public final static int PLAY_COL = 6;
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	// common mains
	
	private final Logger LOGGER = LangH.getLogger();
	private final Texts  TXT    = LangH.getTexts();
	
//
// Constructors
//
	
	/**
	 * Constructor.
	 */
	public ModelPhrases() 
	{
		data = new Phrases();
		checkbox = new ArrayList<Boolean>();
		dataPath = new File("");
		soundFolder = "";
		sounds = new LinkedList<String>();
		
		icon_sound    = new ImageIcon(LangH.class.getResource(Texts.PH_ICON_SOUND));
		icon_no_sound = new ImageIcon(LangH.class.getResource(Texts.PH_ICON_NO_SOUND));
		
		columns = new String[] {TXT.LB_COL_CHECK, TXT.LB_COL_NUM, TXT.LB_COL_PHRASE, 
				TXT.LB_COL_TRANSL, TXT.LB_COL_COMMENT, TXT.LB_COL_TAG, TXT.LB_COL_PLAY};
	}

//	
// Methods
//
	
	/**
	 * Loads data from the specified file.
	 * @param dataPath - a file to get data.
	 */
	void loadData(File dataPath)
	{
		try 
		{
			LOGGER.entering("ModelPhrases", "loadData", dataPath);
			
			if (dataPath == null)
			{
				clear();
				return;
			}
			
			if (!dataPath.exists())
			{
				LOGGER.log(Level.WARNING, "Data path does not exist");
				return;
			}
			
			this.dataPath = dataPath;
			
			data.clear();
			data.parseFile(dataPath);
			
			checkbox.clear();
			for (int i=0; i<data.size(); i++)
			{
				checkbox.add(false);
			}
			
			soundFolder = LangH.getData().getSoundFolder(dataPath, "Sound");
			File[] files = Common.getListFiles(new File(soundFolder), Common.GET_FILES);
			sounds.clear();
			
			for (File each : files)
			{
				sounds.add(each.getName());
			}
			
			this.fireTableDataChanged();
			
			LOGGER.exiting("ModelPhrases", "loadData", "No exceptions");
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Writes this object to the file.
	 * @param file - a file to be written.
	 */
	void codeFile(File file)
	{
		try
		{
			data.codeFile(file);
			dataPath = file;
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Returns a <code>Phrase</code> with the specified id.
	 * @param id - an id of an <code>Phrase</code>.
	 * @return a <code>Phrase</code> with the specified id.
	 */
	Phrase get(int id) 
	{
		int row = invert(data.indexOf(id));
		
		int    idd     = (int)    getValueAt(row, ID_COL);
		String phrase  = (String) getValueAt(row, PHRASE_COL);
		Term   transl  = (Term)   getValueAt(row, TRANSL_COL);
		Term   comment = (Term)   getValueAt(row, COMMENT_COL);
		Term   tags    = (Term)   getValueAt(row, TAG_COL);
	
		return new Phrase(idd, phrase, transl, comment, tags);
	}
	
	/**
	 * Adds new elements.
	 * @param c - new elements.
	 */
	void addAll(Collection <? extends Phrase> c)
	{
		try
		{
			int ok = GUI.showConfirmDialog(TXT.MG_ADD_QUESTION, TXT.TL_CONF_EDIT);
			if (ok == JOptionPane.OK_OPTION)
			{
				for (Phrase each : c)
				{
					int id = data.getLargerID()+1;
					if (id < 0) id = 1;
					each.setId(id);
					
					String phrase = each.getPhrase();
					if (data.contPhrase(phrase)) 
					{
						String message = TXT.MG_LIST_CONT_PHRASE + " " + phrase + ". " + TXT.MG_EDIT_QUESTION;
						int ok2 = GUI.showConfirmDialog(message, TXT.TL_CONF_EDIT);
						if (ok2 == JOptionPane.OK_OPTION) 
						{
							if (data.addEdit(each) == -1) checkbox.add(false);
						}
					}
					else 
					{
						if (data.add(each)) checkbox.add(false);
					}
				}
				this.fireTableDataChanged();
			}
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Removes marked rows.
	 */
	void removeMarkedRows()
	{
		try
		{
			int ok = GUI.showConfirmDialog(TXT.MG_REMOVE_QUESTION, TXT.TL_CONF_REMOVE);
			if (ok == JOptionPane.OK_OPTION) 
			{
				LinkedList<Integer> ids = getMarkedIds();
				for (int row=0; row<this.getRowCount(); row++)
				{
					for (int id : ids)
					{
						if ((int) getValueAt(row, ID_COL) == id)
						{
							int index = data.indexOfId(id);
							data.remove(index);
						}
					}
				}
				this.fireTableDataChanged();
			}
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Puts marked rows to the worklist.
	 * @param worklist - an object to to exchange data.
	 */
	void toTask(AbstractFilteredTable<Integer> worklist)
	{
		LinkedList<Integer> ids = getMarkedIds();
		worklist.addAllFiltered(ids);
	}
	
	/**
	 * Returns tags of this object as a <code>Term</code>.
	 * @return tags of this object as a <code>Term</code>.
	 */
	Term getTags()
	{
		String[] tags = data.getTagList();
		Term result = new Term();
		for (String tag : tags)
		{
			result.add(tag);
		}
		return result;
	}
	
	/**
	 * Adds the specified tags to marked rows.
	 * @param tags - tags to be added.
	 */
	void addTags(Term tags)
	{
		try
		{
			int ok = GUI.showConfirmDialog(TXT.MG_TO_PHRASE_QUESTION, TXT.TL_CONF_EDIT);
			if (ok == JOptionPane.OK_OPTION) 
			{
				LinkedList<Integer> ids = getMarkedIds();
				for (int row=0; row<this.getRowCount(); row++)
				{
					for (int id : ids)
					{
						if ((int) getValueAt(row, ID_COL) == id)
						{
							int index = data.indexOfId(id);
							data.get(index).addTag(tags);
						}
					}
				}
				this.fireTableDataChanged();
			}
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Fill the specified <code>AudioContainer</code> with sound with the specified id.
	 * @param toGet - an object to get the sound.
	 * @param id - an id to be used to get the sound.
	 * @return <code>true</code> if the sound has been got successfully.
	 */
	boolean getSound(AudioContainer toGet, int id)
	{
		if (data.indexOfId(id) == -1)
		{
			return false;
		}
			
		String fileName = id + "." + TXT.PH_EXT_SOUND;
		String filePath = soundFolder + File.separator + fileName;
		File file = new File(filePath);
				
		if (!file.exists())
		{
			return false;
		}
			
		return toGet.load(file);
	}
	
	/**
	 * Adds the specified sound to the first marked row.
	 * @param sound - a sound to be added.
	 */
	void addSound(AudioContainer sound)
	{
		try
		{
			int markedId = getMarkedId();
			if (markedId == -1)
			{
				GUI.showInformDialog(TXT.MG_SELECT_ROWS, TXT.TL_NO_SELECTION);
				return;
			}
			
			int index = data.indexOfId(markedId);
			String message = TXT.MG_EDIT_REC_QUESTION + " " + data.get(index).getPhrase() + "?";
			int ok = GUI.showConfirmDialog(message, TXT.TL_CONF_EDIT);
			if (ok == JOptionPane.OK_OPTION) 
			{
				String fileName = markedId + "." + TXT.PH_EXT_SOUND;
				sounds.add(fileName);
				
				String filePath = soundFolder + File.separator + fileName;
				sound.save(new File(filePath));
				
				LOGGER.log(Level.CONFIG, "Record has been saved " + dataPath);
				GUI.sendMessage(TXT.MG_SAVED_REPORT + " " + dataPath);
			}
			
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Returns the path of this database.
	 * @return the path of this database.
	 */
	File getPath()
	{
		return dataPath;
	}
	
	/**
	 * Returns the sound folder of this database.
	 * @return the sound folder of this database.
	 */
	String getSoundFolder()
	{
		return soundFolder;
	}
	
	/**
	 * Returns Phrases.indexOfPart(String, String, String, String) converted to rows.
	 * @param phrase - a value to search in among phrases.
	 * @param transl - a value to search in among translations.
	 * @param comment - a value to search in among comments.
	 * @param tag - a value to search in among tags.
	 * @return Phrases.indexOfPart(String, String, String, String) converted to rows.
	 */
	int[] indexOfPart(String phrase, String transl, String comment, String tag)
	{
		int[] indices = data.indexOfPart(phrase, transl, comment, tag);
		int[] rows = new int[indices.length];
		for (int i=0; i<indices.length; i++)
		{
			rows[i] = invert(indices[i]);
		}
		return rows;
	}
	
	/**
	 * Returns Phrases.indexOfTags(Term) converted to rows.
	 * @param tags - an object to be found.
	 * @return Phrases.indexOfTags(Term) converted to rows.
	 */
	int[] indexOfTags(Term tags)
	{
		int[] indices = data.indexOfTags(tags);
		int[] rows = new int[indices.length];
		for (int i=0; i<indices.length; i++)
		{
			rows[i] = invert(indices[i]);
		}
		return rows;
	}
	
	/**
	 * Returns Phrases.indexOfId(int) converted to rows.
	 * @param id - an id to be found
	 * @return Phrases.indexOfId(int) converted to rows.
	 */
	int indexOfId(int id)
	{
		return invert(data.indexOfId(id));
	}
	
	/**
	 * Inverts specified index to show the table from the last element 
	 * @param index - an index to be inverted
	 * @return the inverted index
	 */
	private int invert(int i) 
	{
		try 
		{
			return data.size() - i - 1;
		}
		catch (RuntimeException e) 
		{
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			System.exit(-1);
			return 0;
		}
	}
	
	/**
	 * Returns a list of IDs of marked rows.
	 * @return a list of IDs of marked rows.
	 */
	private LinkedList<Integer> getMarkedIds() 
	{
		LinkedList<Integer> result = new LinkedList<Integer>();
			
		for (int row=0; row<getRowCount(); row++) 
		{
			if ((boolean) getValueAt(row, CHECK_COL)) 
			{
				setValueAt(false, row, CHECK_COL);
				result.add((int) getValueAt(row, ID_COL));
			}
		}
		return result;
	}
	
	/**
	 * Returns the first marked row or -1;
	 * @return the first marked row or -1;
	 */
	private int getMarkedId()
	{
		for (int row=0; row<getRowCount(); row++)
		{
			if ((boolean) getValueAt(row, CHECK_COL))
			{
				setValueAt(false, row, CHECK_COL);
				return (int) getValueAt(row, ID_COL);
			}
		}
		return -1;
	}
	
	/**
	 * Clears all data.
	 */
	private void clear()
	{
		data.clear();
		checkbox.clear();
		dataPath = new File("");
		soundFolder = "";
		sounds.clear();
	}

//
// Extending AbstractTableModel
//
	
    @Override
	public int getRowCount() 
    { 
    	return data.size();
	}
	
    @Override
	public int getColumnCount()
    {
    	return columns.length;
	}
	
    @Override
	public Object getValueAt(int rowIndex, int columnIndex)
    {
    	Phrase p = data.get(invert(rowIndex));
		switch (columnIndex) 
		{
		case CHECK_COL:   return checkbox.get(rowIndex);
		case ID_COL:	  return p.getId();
		case PHRASE_COL:  return p.getPhrase();
		case TRANSL_COL:  return p.getTransl();
		case COMMENT_COL: return p.getComment();
		case TAG_COL:     return p.getTag();
		case PLAY_COL:
			boolean isSound = sounds.contains(p.getId() + "." + TXT.PH_EXT_SOUND);
			if (isSound) return icon_sound;
			else         return icon_no_sound;
		default: return "";
    	}
	}
    
	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) 
	{
		String valAsString = "";
		switch (columnIndex) 
		{
		case CHECK_COL: 
			checkbox.set(rowIndex, (Boolean) obj);
			break;
		case PHRASE_COL:
			valAsString = ((String) obj).replace("\n", " ");
			data.setPhrase(invert(rowIndex), valAsString);
			break;
		case TRANSL_COL:
			valAsString = ((String) obj).replace("\n", " ");
			data.setTransl(invert(rowIndex), new Term().parse(valAsString, ";"));
			break;
		case COMMENT_COL:
			valAsString = ((String) obj).replace("\n", " ");
			data.setComment(invert(rowIndex), new Term().parse(valAsString, ";"));
			break;
		case TAG_COL:
			valAsString = ((String) obj).replace("\n", " ");
			data.setTag(invert(rowIndex), new Term().parse(valAsString, ";"));
			this.fireTableCellUpdated(rowIndex, columnIndex); // notice to change tags list
			break;
		default:
			break;
		}
	}
	
	@Override
	public String getColumnName(int columnIndex) 
	{
		return columns[columnIndex];
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int columnIndex) 
	{
		switch (columnIndex) 
		{
		case CHECK_COL:  
			return Boolean.class;
		case PHRASE_COL: case TRANSL_COL: case COMMENT_COL: case TAG_COL:
			return JTextArea.class;
		case PLAY_COL:
			return JButton.class;
		default:
			return Object.class;
		}
	}
    
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		switch (columnIndex) 
		{
		case ID_COL: return false;
		default:     return true;
		}
	}
	
} // end ModelPhrases