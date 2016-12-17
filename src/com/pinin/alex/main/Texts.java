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

package com.pinin.alex.main;

import java.util.*;

/**
 * Loads and contains all texts.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Texts
{
	/**
	 * Constructor.
	 * @param textsSource - a texts for this program.
	 */
	public Texts(CharSequence textsSource)
	{
		final int KEY = 1;
		final int VALUE = 2;
		final int COMMENT = 3;
		
		int stage = KEY;
		
		HashMap<String, String> hm = new HashMap<>();
		StringBuilder key = new StringBuilder();
		StringBuilder value = new StringBuilder();
		
		for (int i=0; i<textsSource.length(); i++)
		{
			char c = textsSource.charAt(i);
			switch (c)
			{
				case '*': 
					stage = COMMENT;
					continue;
				case '=': 
					stage = VALUE;
					continue;
				case '\n': 
					stage = KEY;
					if (key.length() > 0) 
					{
						hm.put(key.toString().trim(), value.toString().trim());
					}
					key = new StringBuilder();
					value = new StringBuilder();
					continue;
				case '\r':
					continue;
			}
			switch (stage)
			{
				case KEY:
					key.append(c);
					break;
				case VALUE:
					value.append(c);
					break;
			}	
		}
		
		// hot keys
		
		HK_NEW_FILE 			= hm.get("HK_NEW_FILE");
		HK_OPEN_FILE 			= hm.get("HK_OPEN_FILE");		
		HK_SAVE_FILE 			= hm.get("HK_SAVE_FILE");
		HK_SAVE_AS_FILE 		= hm.get("HK_SAVE_AS_FILE");
		HK_EXIT_FILE 			= hm.get("HK_EXIT_FILE");
		
		HK_REPL_EDIT 			= hm.get("HK_REPL_EDIT");
		HK_SET_EDIT 			= hm.get("HK_SET_EDIT");
		
		HK_EXER_PL 				= hm.get("HK_EXER_PL");
		HK_BY_PHRASE_EXER_PL 	= hm.get("HK_BY_PHRASE_EXER_PL");
		HK_BY_TRANSL_EXER_PL 	= hm.get("HK_BY_TRANSL_EXER_PL");
		HK_ENTER_EXER_PL 		= hm.get("HK_ENTER_EXER_PL");
		HK_CLEAN_EXER_PL 		= hm.get("HK_CLEAN_EXER_PL");
		HK_PLAY_EXER_PL 		= hm.get("HK_PLAY_EXER_PL");
		HK_HELP_EXER_PL 		= hm.get("HK_HELP_EXER_PL");
		HK_RUN_EXER_PL 			= hm.get("HK_RUN_EXER_PL");
			
		HK_TASK_PL 				= hm.get("HK_TASK_PL");
		HK_SELECT_TASK_PL 		= hm.get("HK_SELECT_TASK_PL");
		HK_SEL_ALL_TASK_PL 		= hm.get("HK_SEL_ALL_TASK_PL");
		HK_DELETE_TASK_PL 		= hm.get("HK_DELETE_TASK_PL");
		
		HK_ADD_PL 				= hm.get("HK_ADD_PL");
		HK_NEW_ADD_PL 			= hm.get("HK_NEW_ADD_PL");
			
		HK_TABLE_PL 			= hm.get("HK_TABLE_PL");
		HK_SELECT_TABLE_PL 		= hm.get("HK_SELECT_TABLE_PL");
		HK_SEL_ALL_TABLE_PL 	= hm.get("HK_SEL_ALL_TABLE_PL");
		HK_DELETE_TABLE_PL 		= hm.get("HK_DELETE_TABLE_PL");
		HK_TO_TASK_TABLE_PL 	= hm.get("HK_TO_TASK_TABLE_PL");
		
		HK_TAG_PL 				= hm.get("HK_TAG_PL");	
		HK_SELECT_TAG_PL 		= hm.get("HK_SELECT_TAG_PL");
		HK_SEL_ALL_TAG_PL 		= hm.get("HK_SEL_ALL_TAG_PL");
		HK_FIND_TAG_PL 			= hm.get("HK_FIND_TAG_PL");
		HK_TO_PHRASE_TAG_PL 	= hm.get("HK_TO_PHRASE_TAG_PL");
		HK_UPDATE_TAG_PL 		= hm.get("HK_UPDATE_TAG_PL");
			
		HK_SEARCH_PL 			= hm.get("HK_SEARCH_PL");
		HK_FIND_SEARCH_PL 		= hm.get("HK_FIND_SEARCH_PL");
		HK_CLEAN_SEARCH_PL 		= hm.get("HK_CLEAN_SEARCH_PL");
		
		HK_SOUND_PL 			= hm.get("HK_SOUND_PL");
		HK_REC_SOUND_PL 		= hm.get("HK_REC_SOUND_PL");
		HK_STOP_SOUND_PL 		= hm.get("HK_STOP_SOUND_PL");
		HK_PLAY_SOUND_PL 		= hm.get("HK_PLAY_SOUND_PL");
		HK_SAVE_SOUND_PL 		= hm.get("HK_SAVE_SOUND_PL");
		
		HK_HELP_HELP 			= hm.get("HK_HELP_HELP");
		HK_ABOUT_HELP 			= hm.get("HK_ABOUT_HELP");
		
		// mnemonics
		
		MN_FILE 				= getChar(hm, "MN_FILE");
		MN_EDIT 				= getChar(hm, "MN_EDIT");
		MN_EXER_PL 				= getChar(hm, "MN_EXER_PL");
		MN_TASK_PL 				= getChar(hm, "MN_TASK_PL");
		MN_ADD_PL 				= getChar(hm, "MN_ADD_PL");
		MN_TABLE_PL 			= getChar(hm, "MN_TABLE_PL");
		MN_SOUND_PL 			= getChar(hm, "MN_SOUND_PL");
		MN_HELP  				= getChar(hm, "MN_HELP");
		
		// buttons
		
		BT_NEW_FILE 			= hm.get("BT_NEW_FILE");
		BT_OPEN_FILE 			= hm.get("BT_OPEN_FILE");
		BT_SAVE_FILE 			= hm.get("BT_SAVE_FILE");
		BT_SAVE_AS_FILE 		= hm.get("BT_SAVE_AS_FILE");
		BT_EXIT_FILE 			= hm.get("BT_EXIT_FILE");
		
		BT_REPL_EDIT 			= hm.get("BT_REPL_EDIT");
		BT_SET_EDIT 			= hm.get("BT_SET_EDIT");

		BT_EXER_PL 				= hm.get("BT_EXER_PL");
		BT_BY_PHRASE_EXER_PL 	= hm.get("BT_BY_PHRASE_EXER_PL");
		BT_BY_TRANSL_EXER_PL 	= hm.get("BT_BY_TRANSL_EXER_PL");
		BT_BY_PH_SND_EXER_PL 	= hm.get("BT_BY_PH_SND_EXER_PL");
		BT_BY_TR_SND_EXER_PL 	= hm.get("BT_BY_TR_SND_EXER_PL");	
		BT_ENTER_EXER_PL 		= hm.get("BT_ENTER_EXER_PL");
		BT_CLEAN_EXER_PL 		= hm.get("BT_CLEAN_EXER_PL");
		BT_PLAY_EXER_PL 		= hm.get("BT_PLAY_EXER_PL");
		BT_HELP_EXER_PL 		= hm.get("BT_HELP_EXER_PL");
		BT_RUN_EXER_PL 			= hm.get("BT_RUN_EXER_PL");

		BT_TASK_PL 				= hm.get("BT_TASK_PL");
		BT_SELECT_TASK_PL 		= hm.get("BT_SELECT_TASK_PL");
		BT_SEL_ALL_TASK_PL 		= hm.get("BT_SEL_ALL_TASK_PL");
		BT_DELETE_TASK_PL 		= hm.get("BT_DELETE_TASK_PL");

		BT_ADD_PL 				= hm.get("BT_ADD_PL");
		BT_NEW_ADD_PL 			= hm.get("BT_NEW_ADD_PL");

		BT_TABLE_PL 			= hm.get("BT_TABLE_PL");
		BT_SELECT_TABLE_PL 		= hm.get("BT_SELECT_TABLE_PL");
		BT_SEL_ALL_TABLE_PL 	= hm.get("BT_SEL_ALL_TABLE_PL");
		BT_DELETE_TABLE_PL 		= hm.get("BT_DELETE_TABLE_PL");
		BT_TO_TASK_TABLE_PL 	= hm.get("BT_TO_TASK_TABLE_PL");
		BT_FILTER_TABLE_PL 		= hm.get("BT_FILTER_TABLE_PL");

		BT_TAG_PL 				= hm.get("BT_TAG_PL");
		BT_SELECT_TAG_PL 		= hm.get("BT_SELECT_TAG_PL");
		BT_SEL_ALL_TAG_PL 		= hm.get("BT_SEL_ALL_TAG_PL");
		BT_FIND_TAG_PL 			= hm.get("BT_FIND_TAG_PL");
		BT_TO_PHRASE_TAG_PL 	= hm.get("BT_TO_PHRASE_TAG_PL");
		BT_UPDATE_TAG_PL 		= hm.get("BT_UPDATE_TAG_PL");

		BT_SEARCH_PL 			= hm.get("BT_SEARCH_PL");
		BT_FIND_SEARCH_PL 		= hm.get("BT_FIND_SEARCH_PL");
		BT_CLEAN_SEARCH_PL 		= hm.get("BT_CLEAN_SEARCH_PL");

		BT_SOUND_PL 			= hm.get("BT_SOUND_PL");
		BT_REC_SOUND_PL 		= hm.get("BT_REC_SOUND_PL");
		BT_STOP_SOUND_PL 		= hm.get("BT_STOP_SOUND_PL");
		BT_PLAY_SOUND_PL 		= hm.get("BT_PLAY_SOUND_PL");
		BT_SAVE_SOUND_PL 		= hm.get("BT_SAVE_SOUND_PL");
		
		BT_SET_LANG_RUS 		= hm.get("BT_SET_LANG_RUS");
		BT_SET_LANG_ENG 		= hm.get("BT_SET_LANG_ENG");
		
		BT_SET_FSIZE_NORM 		= hm.get("BT_SET_FSIZE_NORM");
		BT_SET_FSIZE_BIG 		= hm.get("BT_SET_FSIZE_BIG");
		BT_SET_FSIZE_HUGE 		= hm.get("BT_SET_FSIZE_HUGE");
		BT_SET_OK_BT 			= hm.get("BT_SET_OK_BT");
		
		BT_SHOW_HIDE_PL 		= hm.get("BT_SHOW_HIDE_PL");
		
		BT_HELP_HELP 			= hm.get("BT_HELP_HELP");
		BT_ABOUT_HELP  			= hm.get("BT_ABOUT_HELP");

		// menus
		
		MU_FILE 				= hm.get("MU_FILE");
		MU_EDIT 				= hm.get("MU_EDIT");
		MU_EXER 				= hm.get("MU_EXER");
		MU_TASK 				= hm.get("MU_TASK");
		MU_ADD 					= hm.get("MU_ADD");
		MU_TABLE 				= hm.get("MU_TABLE");
		MU_SOUND 				= hm.get("MU_SOUND");
		MU_HELP 				= hm.get("MU_HELP");
		
		// tips
		
		TIP_SAVE_FILE 			= hm.get("TIP_SAVE_FILE");
		TIP_EXER_PL 			= hm.get("TIP_EXER_PL");
		TIP_TASK_PL 			= hm.get("TIP_TASK_PL");
		TIP_ADD_PL 				= hm.get("TIP_ADD_PL");
		TIP_TABLE_PL 			= hm.get("TIP_TABLE_PL");
		TIP_TAG_PL 				= hm.get("TIP_TAG_PL");
		TIP_SEARCH_PL 			= hm.get("TIP_SEARCH_PL");
		TIP_SOUND_PL 			= hm.get("TIP_SOUND_PL");
		
		TIP_SELECT 				= hm.get("TIP_SELECT");
		TIP_SEL_ALL 			= hm.get("TIP_SEL_ALL");
		TIP_DELETE 				= hm.get("TIP_DELETE");
		TIP_TOTASK 				= hm.get("TIP_TOTASK");
		TIP_CLEAR_FILTER 		= hm.get("TIP_CLEAR_FILTER");
		TIP_FIND 				= hm.get("TIP_FIND");
		TIP_TOPHR 				= hm.get("TIP_TOPHR");
		TIP_ADD 				= hm.get("TIP_ADD");
		TIP_ADD_HELP 			= hm.get("TIP_ADD_HELP");		
		
		TIP_EXER_PLAY 			= hm.get("TIP_EXER_PLAY");
		TIP_EXER_ENTER 			= hm.get("TIP_EXER_ENTER");
		TIP_EXER_HELP 			= hm.get("TIP_EXER_HELP");
		TIP_EXER_RUN 			= hm.get("TIP_EXER_RUN");
		TIP_EXER_CHOOSE 		= hm.get("TIP_EXER_CHOOSE");
		
		TIP_REC_SOUND 			= hm.get("TIP_REC_SOUND");
		TIP_STOP_SOUND 			= hm.get("TIP_STOP_SOUND");
		TIP_PLAY_SOUND 			= hm.get("TIP_PLAY_SOUND");
		TIP_SAVE_SOUND 			= hm.get("TIP_SAVE_SOUND");
		
		// paths
		
		PH_EXT_DB 				= hm.get("PH_EXT_DB");
		PH_EXT_TSK 				= hm.get("PH_EXT_TSK");
		PH_EXT_SOUND 			= hm.get("PH_EXT_SOUND");
		
		// labels
		
		LB_COL_CHECK 			= hm.get("LB_COL_CHECK");
		LB_COL_NUM 				= hm.get("LB_COL_NUM");
		LB_COL_PHRASE 			= hm.get("LB_COL_PHRASE");
		LB_COL_TRANSL 			= hm.get("LB_COL_TRANSL");
		LB_COL_COMMENT 			= hm.get("LB_COL_COMMENT");
		LB_COL_TAG 				= hm.get("LB_COL_TAG");
		LB_COL_PLAY 			= hm.get("LB_COL_PLAY");

		LB_HEADER_EXER_PL 		= hm.get("LB_HEADER_EXER_PL");
		LB_HEADER_TASK 			= hm.get("LB_HEADER_TASK");
		LB_HEADER_ASWER 		= hm.get("LB_HEADER_ASWER");

		LB_HEADER_TABLE_PL 		= hm.get("LB_HEADER_TABLE_PL");
		LB_HEADER_TAG_PL 		= hm.get(" LB_HEADER_TAG_PL");
		LB_HEADER_SEARCH_PL 	= hm.get("LB_HEADER_SEARCH_PL");

		LB_HEADER_ADD_PHRASE 	= hm.get("LB_HEADER_ADD_PHRASE");
		LB_HEADER_ADD_TRANSL 	= hm.get("LB_HEADER_ADD_TRANSL");
		LB_HEADER_ADD_COMM 		= hm.get("LB_HEADER_ADD_COMM");
		LB_HEADER_ADD_TAG 		= hm.get("LB_HEADER_ADD_TAG");
		
		LB_HEADER_SET_LANG 		= hm.get("LB_HEADER_SET_LANG");
		LB_HEADER_SET_FSIZE 	= hm.get("LB_HEADER_SET_FSIZE");
		
		// titles
		
		TL_TITLE 				= hm.get("TL_TITLE");
		TL_ERROR 				= hm.get("TL_ERROR");
		TL_CONF_EXIT 			= hm.get("TL_CONF_EXIT");
		TL_CONF_REMOVE 			= hm.get("TL_CONF_REMOVE");
		TL_CONF_EDIT 			= hm.get("TL_CONF_EDIT");
		TL_CONF_SELECT 			= hm.get("TL_CONF_SELECT");
		TL_CONF_TASK 			= hm.get("TL_CONF_TASK");
		TL_CONF_REPLACE 		= hm.get("TL_CONF_REPLACE");
		TL_CONF_NEW		 		= hm.get("TL_CONF_NEW");
		TL_NO_SELECTION 		= hm.get("TL_NO_SELECTION");
		TL_NO_RECORD 			= hm.get("TL_NO_RECORD");

		// messages
		
		MG_DB_LOADED 			= hm.get("MG_DB_LOADED");
		MG_NO_DB 				= hm.get("MG_NO_DB");
		MG_FILE_NOT_LOADED 		= hm.get("MG_FILE_NOT_LOADED");
		MG_FILE_NOT_SAVED 		= hm.get("MG_FILE_NOT_SAVED");
		MG_FILE_IS_DAMAGED 		= hm.get("MG_FILE_IS_DAMAGED");
		MG_REC_NOT_SAVED 		= hm.get("MG_REC_NOT_SAVED");
		MG_EXIT_QUESTION 		= hm.get("MG_EXIT_QUESTION");
		MG_REMOVE_QUESTION 		= hm.get("MG_REMOVE_QUESTION");
		MG_EDIT_QUESTION 		= hm.get("MG_EDIT_QUESTION");
		MG_EDIT_REC_QUESTION 	= hm.get("MG_EDIT_REC_QUESTION");
		MG_TO_TASK_QUESTION 	= hm.get("MG_TO_TASK_QUESTION");
		MG_TO_PHRASE_QUESTION 	= hm.get("MG_TO_PHRASE_QUESTION");
		MG_SELECT_ROWS 			= hm.get("MG_SELECT_ROWS");
		MG_SAVED_REPORT 		= hm.get("MG_SAVED_REPORT");
		MG_CORRECT_ANSW 		= hm.get("MG_CORRECT_ANSW");
		MG_WRONG_ANSW 			= hm.get("MG_WRONG_ANSW");
		MG_DONE 				= hm.get("MG_DONE");
		MG_BY_PHRASE_INFO 		= hm.get("MG_BY_PHRASE_INFO");
		MG_BY_TRANSL_INFO 		= hm.get("MG_BY_TRANSL_INFO");
		MG_BY_PH_SND_INFO 		= hm.get("MG_BY_PH_SND_INFO");
		MG_BY_TR_SND_INFO 		= hm.get("MG_BY_TR_SND_INFO");
		MG_SOUND_INFO 			= hm.get("MG_SOUND_INFO");
		MG_ERROR_ADD 			= hm.get("MG_ERROR_ADD");
		MG_LIST_CONT_PHRASE 	= hm.get("MG_LIST_CONT_PHRASE");
		MG_ATTEMPT 				= hm.get("MG_ATTEMPT");
		MG_PHRASES 				= hm.get("MG_PHRASES");
		MG_MISTAKES 			= hm.get("MG_MISTAKES");
		MG_REST 				= hm.get("MG_REST");
		MG_NO_RECORD_FULL 		= hm.get("MG_NO_RECORD_FULL");
		MG_NO_RECORD 			= hm.get("MG_NO_RECORD");
		MG_RECORDING 			= hm.get("MG_RECORDING");
		MG_RECORDED				= hm.get("MG_RECORDED");
		MG_RECORD_SOUND 		= hm.get("MG_RECORD_SOUND");
		MG_FILE_DESCRIPT 		= hm.get("MG_FILE_DESCRIPT");
		MG_SET_REST_TO_APL 		= hm.get("MG_SET_REST_TO_APL");
		MG_NEW_DOC_INFO 		= hm.get("MG_NEW_DOC_INFO");
		MG_ADD_QUESTION 		= hm.get("MG_ADD_QUESTION");
		MG_NEW_TASK_QUESTON 	= hm.get("MG_NEW_TASK_QUESTON");
		MG_HELP_LINK 			= hm.get("MG_HELP_LINK");
		MG_FILE_EXISTS 			= hm.get("MG_FILE_EXISTS");
		MG_NEW_QUESTION			= hm.get("MG_NEW_QUESTION");
		
		// URLs
		
		URL_WEBSITE 			= hm.get("URL_WEBSITE");
		URL_SUPPORT 			= hm.get("URL_SUPPORT");
	}

//
// Methods
//

	/**
	 * Returns the first <code>char</code> from <code>String</code>.
	 * @param map - a map to get value.
	 * @param key - a key to get value.
	 * @return the first <code>char</code> from <code>String</code>.
	 */
	private char getChar(Map<String, String> map, String key)
	{
		String s = map.get(key);
		if (s == null || s.length() == 0) return ' ';
		return s.charAt(0);
	}

//
// TODO Variables
//
	
	// logger
	
	public final static String LG_LOGGER = "com.pinin.alex.lang_tutor";
	
	// preferences
	
	public final static String PR_PATH          = "/com/pinin/alex/langH";
	public final static String PR_DB_PATH       = "db_path";
	public final static String PR_FRAME_HEIGHT  = "frame_height";
	public final static String PR_FRAME_STATE   = "frame_state";
	public final static String PR_FRAME_WIDTH   = "frame_width";
	public final static String PR_LAST_PATH     = "last_path";
	public final static String PR_LANGUAGE      = "lang";
	public final static String PR_FONT_SIZE     = "font_size";
	
	// paths
	
	public final static String PH_TEXT          = "texts/texts.txt";
	public final static String PH_TEXT_RUS      = "texts/texts_rus.txt";
	public final static String PH_REPLACE       = "texts/replacements.txt";
	public final static String PH_INFO       	= "texts/info.txt";
	
	public final static String PH_ICON_TITLE    = "icons/icon_title.png";
	public final static String PH_ICON_REC      = "icons/icon_rec.png";
	public final static String PH_ICON_STOP     = "icons/icon_stop.png";
	public final static String PH_ICON_PLAY     = "icons/icon_play.png";
	public final static String PH_ICON_SAVE     = "icons/icon_save.png";
	public final static String PH_ICON_NEW      = "icons/icon_new.png";
	public final static String PH_ICON_OPEN     = "icons/icon_open.png";
	public final static String PH_ICON_LIST     = "icons/icon_list.png";
	public final static String PH_ICON_EXER     = "icons/icon_exer.png";
	public final static String PH_ICON_BOOK     = "icons/icon_book.png";
	public final static String PH_ICON_ADD      = "icons/icon_add.png";
	public final static String PH_ICON_SEARCH   = "icons/icon_search.png";
	public final static String PH_ICON_SOUND    = "icons/icon_sound.png";
	public final static String PH_ICON_NO_SOUND = "icons/icon_no_sound.png";
	public final static String PH_ICON_TAG      = "icons/icon_tag.png";
	public final static String PH_ICON_FILTER   = "icons/icon_filter.png";
	public final static String PH_ICON_DELETE   = "icons/icon_delete.png";
	public final static String PH_ICON_SELECT   = "icons/icon_select.png";
	public final static String PH_ICON_SEL_ALL  = "icons/icon_sel_all.png";
	public final static String PH_ICON_TOTASK   = "icons/icon_totask.png";
	public final static String PH_ICON_TOPHR    = "icons/icon_tophr.png";
	public final static String PH_ICON_ADDNEW   = "icons/icon_addnew.png";
	public final static String PH_ICON_ENTER    = "icons/icon_enter.png";
	public final static String PH_ICON_HELP     = "icons/icon_help.png";
	public final static String PH_ICON_RUN      = "icons/icon_run.png";
	public final static String PH_ICON_REPL     = "icons/icon_repl.png";
	public final static String PH_ICON_SETT     = "icons/icon_sett.png";
	
	// hot keys
	
	public String HK_NEW_FILE;
	public String HK_OPEN_FILE;
	public String HK_SAVE_FILE;
	public String HK_SAVE_AS_FILE;
	public String HK_EXIT_FILE;
	
	public String HK_REPL_EDIT;
	public String HK_SET_EDIT;
	
	public String HK_EXER_PL;
	public String HK_BY_PHRASE_EXER_PL;
	public String HK_BY_TRANSL_EXER_PL;
	public String HK_ENTER_EXER_PL;
	public String HK_CLEAN_EXER_PL;
	public String HK_PLAY_EXER_PL;
	public String HK_HELP_EXER_PL;
	public String HK_RUN_EXER_PL;
	
	public String HK_TASK_PL;
	public String HK_SELECT_TASK_PL;
	public String HK_SEL_ALL_TASK_PL;
	public String HK_DELETE_TASK_PL;
	
	public String HK_ADD_PL;
	public String HK_NEW_ADD_PL;
	
	public String HK_TABLE_PL;	// !!!
	public String HK_SELECT_TABLE_PL;
	public String HK_SEL_ALL_TABLE_PL;
	public String HK_DELETE_TABLE_PL;
	public String HK_TO_TASK_TABLE_PL;
	
	public String HK_TAG_PL;
	public String HK_SELECT_TAG_PL;
	public String HK_SEL_ALL_TAG_PL;
	public String HK_FIND_TAG_PL;
	public String HK_TO_PHRASE_TAG_PL;
	public String HK_UPDATE_TAG_PL;	// !!!
	
	public String HK_SEARCH_PL;
	public String HK_FIND_SEARCH_PL;	// !!!
	public String HK_CLEAN_SEARCH_PL;	// !!!
	
	public String HK_SOUND_PL;
	public String HK_REC_SOUND_PL;
	public String HK_STOP_SOUND_PL;
	public String HK_PLAY_SOUND_PL;
	public String HK_SAVE_SOUND_PL;
	
	public String HK_HELP_HELP;
	public String HK_ABOUT_HELP;
	
	// mnemonics
	
	public Character MN_FILE;
	public Character MN_EDIT;
	public Character MN_EXER_PL;
	public Character MN_TASK_PL;
	public Character MN_ADD_PL;
	public Character MN_TABLE_PL;
	public Character MN_SOUND_PL;
	public Character MN_HELP;
	
	// buttons
	
	public String BT_NEW_FILE;
	public String BT_OPEN_FILE;
	public String BT_SAVE_FILE;
	public String BT_SAVE_AS_FILE;
	public String BT_EXIT_FILE;

	public String BT_REPL_EDIT;
	public String BT_SET_EDIT;
	
	public String BT_EXER_PL;
	public String BT_BY_PHRASE_EXER_PL;
	public String BT_BY_TRANSL_EXER_PL;
	public String BT_BY_PH_SND_EXER_PL;
	public String BT_BY_TR_SND_EXER_PL;
	public String BT_ENTER_EXER_PL;
	public String BT_CLEAN_EXER_PL;
	public String BT_PLAY_EXER_PL;
	public String BT_HELP_EXER_PL;
	public String BT_RUN_EXER_PL;

	public String BT_TASK_PL;
	public String BT_SELECT_TASK_PL;
	public String BT_SEL_ALL_TASK_PL;
	public String BT_DELETE_TASK_PL;

	public String BT_ADD_PL;
	public String BT_NEW_ADD_PL;

	public String BT_TABLE_PL;
	public String BT_SELECT_TABLE_PL;
	public String BT_SEL_ALL_TABLE_PL;
	public String BT_DELETE_TABLE_PL;
	public String BT_TO_TASK_TABLE_PL;
	public String BT_FILTER_TABLE_PL;

	public String BT_TAG_PL;
	public String BT_SELECT_TAG_PL;
	public String BT_SEL_ALL_TAG_PL;
	public String BT_FIND_TAG_PL;
	public String BT_TO_PHRASE_TAG_PL;
	public String BT_UPDATE_TAG_PL;	// !!!

	public String BT_SEARCH_PL;
	public String BT_FIND_SEARCH_PL;
	public String BT_CLEAN_SEARCH_PL;	// !!!

	public String BT_SOUND_PL;
	public String BT_REC_SOUND_PL;
	public String BT_STOP_SOUND_PL;
	public String BT_PLAY_SOUND_PL;
	public String BT_SAVE_SOUND_PL;
	
	public String BT_SET_LANG_RUS;
	public String BT_SET_LANG_ENG;
	public String BT_SET_FSIZE_NORM;
	public String BT_SET_FSIZE_BIG;
	public String BT_SET_FSIZE_HUGE;	
	public String BT_SET_OK_BT;
	
	public String BT_SHOW_HIDE_PL;
	
	public String BT_HELP_HELP;
	public String BT_ABOUT_HELP;
	
	// menus
	
	public String MU_FILE;
	public String MU_EDIT;
	public String MU_EXER;
	public String MU_TASK;
	public String MU_ADD;
	public String MU_TABLE;
	public String MU_SOUND;
	public String MU_HELP;
	
	// tips
	
	public String TIP_SAVE_FILE;
	public String TIP_EXER_PL;
	public String TIP_TASK_PL;
	public String TIP_ADD_PL;
	public String TIP_TABLE_PL;
	public String TIP_TAG_PL;
	public String TIP_SEARCH_PL;
	public String TIP_SOUND_PL;
	
	public String TIP_SELECT;
	public String TIP_SEL_ALL;
	public String TIP_DELETE;
	public String TIP_TOTASK;
	public String TIP_CLEAR_FILTER;
	public String TIP_FIND;
	public String TIP_TOPHR;
	public String TIP_ADD;
	public String TIP_ADD_HELP;
	
	public String TIP_EXER_PLAY;
	public String TIP_EXER_ENTER;
	public String TIP_EXER_HELP;
	public String TIP_EXER_RUN;
	public String TIP_EXER_CHOOSE;
	
	public String TIP_REC_SOUND;
	public String TIP_STOP_SOUND;
	public String TIP_PLAY_SOUND;
	public String TIP_SAVE_SOUND;
	
	// paths

	public String PH_EXT_DB;
	public String PH_EXT_TSK;
	public String PH_EXT_SOUND;
	
	// labels
	
	public String LB_COL_CHECK;
	public String LB_COL_NUM;
	public String LB_COL_PHRASE;
	public String LB_COL_TRANSL;
	public String LB_COL_COMMENT;
	public String LB_COL_TAG;
	public String LB_COL_PLAY;

	public String LB_HEADER_EXER_PL;
	public String LB_HEADER_TASK;
	public String LB_HEADER_ASWER;

	public String LB_HEADER_TABLE_PL;
	public String LB_HEADER_TAG_PL;
	public String LB_HEADER_SEARCH_PL;

	public String LB_HEADER_ADD_PHRASE;
	public String LB_HEADER_ADD_TRANSL;
	public String LB_HEADER_ADD_COMM;
	public String LB_HEADER_ADD_TAG;
	
	public String LB_HEADER_SET_LANG;
	public String LB_HEADER_SET_FSIZE;

	// titles

	public String TL_TITLE;
	public String TL_ERROR;
	public String TL_CONF_EXIT;
	public String TL_CONF_REMOVE;
	public String TL_CONF_EDIT;
	public String TL_CONF_SELECT;
	public String TL_CONF_TASK;
	public String TL_CONF_REPLACE;
	public String TL_CONF_NEW;
	public String TL_NO_SELECTION;
	public String TL_NO_RECORD;

	// messages

	public String MG_DB_LOADED;
	public String MG_NO_DB;
	public String MG_FILE_NOT_LOADED;
	public String MG_FILE_NOT_SAVED;
	public String MG_FILE_IS_DAMAGED;
	public String MG_REC_NOT_SAVED;
	public String MG_EXIT_QUESTION;
	public String MG_REMOVE_QUESTION;
	public String MG_EDIT_QUESTION;
	public String MG_EDIT_REC_QUESTION;
	public String MG_TO_TASK_QUESTION;
	public String MG_TO_PHRASE_QUESTION;
	public String MG_SOUND_INFO;
	public String MG_SELECT_ROWS;
	public String MG_SAVED_REPORT;
	public String MG_CORRECT_ANSW;
	public String MG_WRONG_ANSW;
	public String MG_DONE;
	public String MG_BY_PHRASE_INFO;
	public String MG_BY_TRANSL_INFO;
	public String MG_BY_PH_SND_INFO;
	public String MG_BY_TR_SND_INFO;
	public String MG_ERROR_ADD;
	public String MG_LIST_CONT_PHRASE;
	public String MG_ATTEMPT;
	public String MG_PHRASES;
	public String MG_MISTAKES;
	public String MG_REST;
	public String MG_NO_RECORD_FULL;
	public String MG_NO_RECORD;
	public String MG_RECORDING;
	public String MG_RECORDED;
	public String MG_RECORD_SOUND;	// !!!
	public String MG_FILE_DESCRIPT;
	public String MG_SET_REST_TO_APL;
	public String MG_NEW_DOC_INFO;
	public String MG_ADD_QUESTION;
	public String MG_NEW_TASK_QUESTON;
	public String MG_HELP_LINK;
	public String MG_FILE_EXISTS;
	public String MG_NEW_QUESTION;
	
	// URLs
	
	public String URL_WEBSITE;	
	public String URL_SUPPORT;
	
} // end Text
