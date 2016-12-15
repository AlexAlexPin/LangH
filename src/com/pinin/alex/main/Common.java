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

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Contains common methods for different cases
 */
public class Common 
{

//
// TODO Random numbers
//
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * @param min - a minimum value
	 * @param max - a maximum value. Must be greater than min.
	 * @return <code>Integer</code> between min and max, inclusive.
	 * @throws GetRandomException if case of any exceptions during the process.
	 */
	public static int randInt(int min, int max) throws GetRandomException
	{
		try 
		{
			Random rand = new Random();
			int bound = (max - min) + 1;
			int randomNum = rand.nextInt(bound) + min;
			return randomNum;
		}
		catch (Exception e) 
		{
			GetRandomException ee = new GetRandomException(e.getClass() + " exception in Common.randInt()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Returns a pseudo-random number between 0 and 2147483646, inclusive.
	 * @return Integer 0 and 2147483646, inclusive.
	 */
	public static int randInt() 
	{
		return randInt(0, 2147483646);
	}
	
	/**
	 * Chooses one random element from a list. Returns it and removes it from the list.
	 * @param list - the list of elements.
	 * @return chosen element which has been removed.
	 * @throws GetRandomException if case of any exceptions during the process.
	 */
	public static <T> T getRandomOne(List<T> list) throws GetRandomException 
	{
		try 
		{
			int min = 0;
			int max = list.size()-1;
			
			int rand = Common.randInt(min, max--);
			T result = list.remove(rand);
			
			return result;
		}
		catch (Exception e) 
		{
			GetRandomException ee = new GetRandomException(e.getClass() + " exception in Common.getRandomOne()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Represents during the process of getting random elements from the <code>List</code>
	 */
	public static class GetRandomException extends RuntimeException 
	{		
		private static final long serialVersionUID = 1L;
		public GetRandomException() {}
		public GetRandomException(String text) {super(text);}
	}

//
// TODO Reading and writing files
//
	
	/**
	 * Returns content of the specified <code>File</code>.
	 * <b>Note</b> that this method is intended for simple cases where it is convenient to read 
	 * all bytes into a byte array. It is not intended for reading in large files.<br>
	 * @param file - a <code>File</code> to be read.
	 * @return content of the specified <code>File</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileReadException in case of other exceptions during the process.
	 */
	public static String readFile(File file) throws NullPointerException, FileReadException 
	{
		try 
		{
			if (file == null) throw new NullPointerException("null value in Common.readFile()");
			byte[] encoded = Files.readAllBytes(file.toPath());
			return new String(encoded);
		}
		catch (Exception e) 
		{
			FileReadException ee = new FileReadException(e.getClass() + " exception in Common.readFile()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Writes the specified <code>String</code> to the specified <code>File</code>.<br>
	 * <b>Note</b> that this method catches all exceptions and returns <code>false</code>.
	 * @param file - a <code>File</code> to be written.
	 * @param toFile - a <code>String</code> to be written to the file.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileWriteException in case of other exceptions during the process.
	 */
	public static void writeFile(File file, String toFile) throws NullPointerException, FileWriteException 
	{
		try 
		{
			if (file == null) throw new NullPointerException("null value in Common.writeFile()");
			byte[] bytes = toFile.getBytes();
			Files.write(file.toPath(), bytes);
		}
		catch (Exception e) 
		{
			FileReadException ee = new FileReadException(e.getClass() + " exception in Common.writeFile()");
			ee.initCause(e);
			throw ee;
		}
	}

	/**
	 * Reads <code>Integer</code> values from the specified <code>File</code> to the
	 * specified <code>Collection</code>.
	 * @param file - a <code>File</code> to be read.
	 * @param collecion - a <code>Collection</code> to be filled.
	 * @return <code>true</code> if the <code>Collection</code> has been filled successfully.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileReadException in case of other exceptions during the process.
	 */
	public static boolean readIntFile(File file, Collection<Integer> collecion) 
			throws FileReadException, NullPointerException
	{
		try 
		{
			if (file == null || collecion == null) 
				throw new NullPointerException("null value in Common.readIntFile()");
			
			Scanner in = null;			
			try 
			{
				if (!file.exists()) return false;
				
				String s = Common.readFile(file);
				in = new Scanner(s);
				
				while(in.hasNextInt()) collecion.add(in.nextInt());
			}
			finally 
			{
				if (in != null) in.close();
			}
			return true;
		}
		catch (Exception e) 
		{
			FileReadException ee = new FileReadException(e.getClass() + " exception in Common.readIntFile()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Writes <code>Integer</code> values from the specified <code>Collection</code> to the
	 * specified <code>File</code>.
	 * @param file - a <code>File</code> to be written.
	 * @param collecion - a <code>Collection</code> to be read.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileWriteException in case of other exceptions during the process.
	 */
	public static void writeIntFile(File file, Collection<Integer> collecion) 
			throws FileReadException, NullPointerException
	{
		try 
		{	
			if (file == null || collecion == null) 
				throw new NullPointerException("null value in Common.writeIntFile()");
			
			Writer out = null;
			try 
			{
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
				for (Integer each : collecion) out.append(each + " ");
			}
			finally
			{
				if (out != null) out.close();
			}
		}
		catch (Exception e) 
		{
			FileWriteException ee = new FileWriteException(e.getClass() + " exception in Common.writeIntFile()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Returns content of the specified <code>File</code> as <code>CharSequence</code>.
	 * @param file - a file to be read.
	 * @return content of the specified <code>File</code> as <code>CharSequence</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileReadException in case of other problems.
	 */
	public static CharSequence getFileContent(File file) 
			throws NullPointerException, FileReadException
	{
		if (file == null) 
			throw new NullPointerException("null value in Common.getFileContent()");
		
	    try
	    {
			if (!file.exists()) 
				throw new FileReadException("file [" 
						+ file + "] does not exist in Common.getFileContent()");
			
			if (file.isDirectory())
				throw new FileReadException("file [" 
						+ file + "] is directory in Common.getFileContent()");
			
			final String charset = "UTF8";
	    	StringBuilder result = new StringBuilder();
	    	
	    	BufferedReader in = new BufferedReader( 
	    			new InputStreamReader( new FileInputStream(file), charset));
	    	
			try 
			{
				while (true) 
				{
					String s = in.readLine();
					if (s == null) break;
					result.append(s);
				}
			}
			finally 
			{
				if (in != null) in.close();
			}
			return result;
		}
		catch (Exception e) 
	    {
			FileReadException ee = new FileReadException(e.getClass() 
					+ " exception in Common.getFileContent()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Returns content of the specified resource as <code>CharSequence</code>.
	 * @param c - a class to find the resource.
	 * @param resource - a resource to be read.
	 * @return content of the specified resource as <code>CharSequence</code>.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileReadException in case of other problems.
	 */
	public static CharSequence getResourceContent(Class<? extends Object> c, String resource) 
			throws NullPointerException, FileReadException
	{
		if (c == null || resource == null) 
			throw new NullPointerException("null value in Common.getResourceContent()");
		
	    try
	    {
	    	InputStream is = c.getResourceAsStream(resource);
	    	
			final String charset = "UTF8";
	    	StringBuilder result = new StringBuilder();
	    	
	    	BufferedReader in = new BufferedReader( new InputStreamReader(is, charset) );
			try 
			{
				while (true) 
				{
					String s = in.readLine();
					if (s == null) break;
					result.append(s).append("\n");
				}
			}
			finally 
			{
				if (in != null) in.close();
				if (is != null) is.close();
			}
			return result;
		}
		catch (Exception e) 
	    {
			FileReadException ee = new FileReadException(e.getClass() 
					+ " exception in Common.getResourceContent()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Puts the specified <code>CharSequence</code> to the specified <code>File</code>.
	 * @param file - a file to be written.
	 * @param csq - a char sequence to be put to the file.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileWriteException in case of other problems.
	 */
	public static void putFileContent(File file, CharSequence csq)
			throws NullPointerException, FileWriteException
	{
		if (file == null || csq == null) 
			throw new NullPointerException("null value in Common.putFileContent()"); 
		
		try 
		{
			final String charset = "UTF8";
			
			Writer out = new BufferedWriter( 
					new OutputStreamWriter( new FileOutputStream(file), charset));
			
			try 
			{
				out.append(csq);
			}
			finally 
			{
				if (out != null) out.close();
			}
		}
		catch (Exception e) 
	    {
			FileWriteException ee = new FileWriteException(e.getClass() 
					+ " exception in Common.putFileContent()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	
	/**
	 * Represents exceptions during writing a file
	 */
	public static class FileWriteException extends RuntimeException 
	{
		private static final long serialVersionUID = 1L;
		public FileWriteException() {}
		public FileWriteException(String text) {super(text);}
	}
		
	/**
	 * Represents exceptions during reading a file
	 */
	public static class FileReadException extends RuntimeException 
	{
		private static final long serialVersionUID = 1L;
		public FileReadException() {}
		public FileReadException(String text) {super(text);}
	}
	
//
// TODO File choosers
//
	
	/**
	 * Runs the file chooser that allows to chose one file.
	 * @param parent - the parent component of the dialog, can be <code>null</code>.
	 * @param description - textual description for the filter, may be <code>null</code>.
	 * @param path - a path to start choice.
	 * @param extensions - the accepted file name extensions.
	 * @return the chosen file name as <code>String</code> or an empty <code>String</code>
	 * if the user canceled operation.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileChooseException in case of other exceptions during the process.
	 */
	public static String showOpenDialog(Component parent, String description, File path, String... extensions) 
			throws NullPointerException, FileChooseException 
	{
		if (parent == null || description == null || path == null || extensions == null)
			throw new NullPointerException("null value in Common.showOpenDialog()");
		
		try 
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
			chooser.setCurrentDirectory(path);
			
			int result = chooser.showOpenDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				return chooser.getSelectedFile().getPath();
			}
			return "";
		}
		catch (Exception e) 
		{
			FileChooseException ee = new FileChooseException(e.getClass() + " exception in Common.showOpenDialog()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Runs the file chooser that allows to save one file.
	 * @param parent - the parent component of the dialog, can be <code>null</code>.
	 * @param description - textual description for the filter, may be <code>null</code>.
	 * @param path - a path to start choice.
	 * @param extensions - the accepted file name extensions.
	 * @return the chosen file name as <code>String</code> or an empty <code>String</code>
	 * if the user canceled operation.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileChooseException in case of other exceptions during the process.
	 */
	public static String showSaveDialog(Component parent, String description, File path, String... extensions) 
			throws NullPointerException, FileChooseException 
	{
		if (parent == null || description == null || path == null || extensions == null)
			throw new NullPointerException("null value in Common.showSaveDialog()");
		
		try 
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
			chooser.setCurrentDirectory(path);
			
			int result = chooser.showSaveDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				return chooser.getSelectedFile().getPath();
			}
			return "";
		}
		catch (Exception e) 
		{
			FileChooseException ee = new FileChooseException(e.getClass() + " exception in Common.showSaveDialog()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Runs the file chooser that allows to chose one folder.
	 * @param parent - the parent component of the dialog, can be <code>null</code>.
	 * @param description - textual description for the filter, may be <code>null</code>.
	 * @param path - a path to start choice.
	 * @return the chosen folder name as <code>String</code> or an empty <code>String</code>
	 * if the user canceled operation.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileChooseException in case of other exceptions during the process.
	 */
	public static String showOpenFolderDialog(Component parent, String description, File path) 
			throws NullPointerException, FileChooseException 
	{
		if (parent == null || description == null || path == null)
			throw new NullPointerException("null value in Common.showOpenFolderDialog()");
		
		try 
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(GET_FOLDERS);
			chooser.setCurrentDirectory(path);
			
			int result = chooser.showOpenDialog(parent);
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				return chooser.getSelectedFile().getPath();
			}
			return "";
		}
		catch (Exception e) 
		{
			FileChooseException ee = new FileChooseException(e.getClass() + " exception in Common.showOpenFolderDialog()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Represents exceptions during file choosing.
	 */
	public static class FileChooseException extends RuntimeException 
	{
		private final static long serialVersionUID = 1L;
		public FileChooseException() {}
		public FileChooseException(String text) {super(text);}
	}
	
//
// TODO Getting file lists
//
	
	public final static int GET_ALL = 0;
	public final static int GET_FILES = 1;
	public final static int GET_FOLDERS = 2;	
	
	/**
	 * Returns the list of files or folders in specified directory.
	 * @param path - a path of the folder with files.
	 * @param option - option <b>GET_FILES</b>: only files; option <b>GET_FOLDERS</b>: only folders; 
	 * option <b>GET_ALL:</b> whole list.
	 * @return the list of files or folders in specified directory.
	 * @throws NullPointerException in case of <code>null</code> value.
	 * @throws FileListException in case of other exceptions during the process.
	 */
	public static File[] getListFiles(File path, int option) throws NullPointerException, FileListException 
	{
		if (path == null)
			throw new NullPointerException("null value in Common.getListFiles()");
		
		try 
		{
			// get all resources in the specified folder
			File[] wholeList = path.listFiles();
			
			// get only selected resources in the specified folder
			LinkedList<File> only = new LinkedList<File>();
			
			switch (option) 
			{
				case GET_FILES: // only files
				{
					for (File each : wholeList) 
					{
						if (each.isFile()) only.add(each);
					}
					break;
				}
				case GET_FOLDERS: // only folders
				{
					for (File each : wholeList)
					{
						if (each.isDirectory()) only.add(each);
					}
					break;
				}
				default: // whole list
				{
					return wholeList;
				}
			}
			
			File[] result = new File[only.size()];
			
			for (int i=0; i<result.length; i++)
			{
				result[i] = only.get(i);
			}
			return result;
		}
		catch (Exception e) 
		{
			FileListException ee = new FileListException(e.getClass() + " exception in Common.getListFiles()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Returns an up level of a path of the specified file.
	 * @param file - a file to get an up level.
	 * @return an up level of a path of the specified file.
	 * @throws NullPointerException in case of <code>null</code> values.
	 * @throws FilePathException in case of other exceptions during the process.
	 */
	public final static File getUpPath(File file) throws NullPointerException, FilePathException 
	{
		if (file == null) throw new NullPointerException("null value in Common.getUpPath()");
		
		try 
		{
			final String filePath = file.getAbsolutePath();
			final int sepLastIndex = filePath.lastIndexOf(File.separator);
			if (sepLastIndex == -1) return file;
			final String upPath = filePath.substring(0, sepLastIndex);
			return new File(upPath);
		}
		catch (Exception e) {
			FilePathException ee = new FilePathException(e.getClass() + " exception in Common.getUpPath()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Represents exceptions during getting file lists.
	 */
	public static class FileListException extends RuntimeException 
	{
		private final static long serialVersionUID = 1L;
		public FileListException() {}
		public FileListException(String text) {super(text);}
	}
	
	/**
	 * Represents exceptions during getting file path.
	 */
	public static class FilePathException extends RuntimeException 
	{
		private final static long serialVersionUID = 1L;
		public FilePathException() {}
		public FilePathException(String text) {super(text);}
	}
	
//
// TODO Time counters
//
	
	/**
	 * Returns the current time in milliseconds.<br>
	 * Calls <code>System.currentTimeMillis()</code>.
	 * @return returns the current time in milliseconds.
	 */
	public static long getTime() 
	{
		return System.currentTimeMillis();
	}
	
	/**
	 * Counts the time elapsed since the specified time.
	 * @param start - starting time.
	 * @return the time elapsed since the specified time.
	 */
	public static long countTime(long start) 
	{
		long startTime = start;
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return totalTime;
	}

//
// TODO Logger
//
	
	/**
	 * Prepares, makes and returns a folder of logging.
	 * @param progname - a name of a program to be logged
	 * @return - a name of the folder for logging
	 * @throws getLogFolderException in case of any exceptions during the process
	 */
	public static String getLogFolder(String progname) throws getLogFolderException 
	{
		try 
		{
			final String logFolder = "." + progname;
			final String path = System.getProperty("user.home") + File.separator + logFolder;
			final File log = new File(path);
			
			boolean check = false;
			check = log.exists();
			if (!check) check = log.mkdir();
			
			String pattern = "%h/" + logFolder + "/" + progname + "%u.log";
			if (!check) pattern = "%h/" + progname + "%u.log";
			
			return pattern;
		}
		catch (Exception e) 
		{
			getLogFolderException ee = new getLogFolderException(e.getClass() + " exception in Common.getLogFolder()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	/**
	 * Returns a formatter for a logger
	 * @return a formatter for a logger
	 * @throws getFormatterException in case of any exceptions during the process
	 */
	public static Formatter getFormatter() throws getFormatterException 
	{
		try 
		{
			return new Formatter() 
			{		
				public String format(LogRecord record) 
				{
					Throwable thr = record.getThrown();
	
					@SuppressWarnings("deprecation")
					String message = "Date: "      + new Date(record.getMillis()).toGMTString() + "\r\n" 
								   + "  Level:   " + record.getLevel()            + "\r\n" 
								   + "  Message: " + record.getMessage()          + "\r\n" 
							       + "  Thread:  " + record.getThreadID()         + "\r\n"
							       + "  Class:   " + record.getSourceClassName()  + "\r\n"
							       + "  Method:  " + record.getSourceMethodName() + "\r\n"
							       + ((thr == null) ? "" :
							    	 "  Thrown:  "    + thr.getClass()            + "\r\n"
							       + "    Cause:  "   + thr.getCause()            + "\r\n")
							       + "\r\n";
					return message;
				}
			};
		}
 		catch (Exception e) 
		{
			getLogFolderException ee = new getLogFolderException(e.getClass() + " exception in Common.getFormatter()");
			ee.initCause(e);
			throw ee;
 		}
	}
	
	/**
	 * Represents exceptions during getting a log folder.
	 */
	public static class getLogFolderException extends RuntimeException 
	{
		private final static long serialVersionUID = 1L;
		public getLogFolderException() {}
		public getLogFolderException(String text) {super(text);}
	}
	
	/**
	 * Represents exceptions during getting a formatter.
	 */
	public static class getFormatterException extends RuntimeException 
	{
		private final static long serialVersionUID = 1L;
		public getFormatterException() {}
		public getFormatterException(String text) {super(text);}
	}
}
// end Common