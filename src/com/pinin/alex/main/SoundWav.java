package com.pinin.alex.main;

import java.io.*;
import java.nio.file.*;
import javax.sound.sampled.*;

/**
 * Allows to record, play and save audio files.
 */
public class SoundWav implements AudioContainer
{
//
// Variables
//
	
	private TargetDataLine line;
	private File tempFolder;
	private File file;
	private boolean isTemp;

//
// Implementation AudioContainer
//
	
	/** @throws AudioCaptureException in case of exceptions during the process. */
	@Override
	public void capture() throws AudioCaptureException
	{
		try
		{	
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			
			if (!AudioSystem.isLineSupported(info))
			{
				throw new AudioCaptureException("line is not supported");
			}
			
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			
			if (tempFolder == null) tempFolder = new File("");
			Path path = Files.createTempFile(tempFolder.toPath(), "sound", ".tmp");
			isTemp = true;
			file = path.toFile();
			file.deleteOnExit();
			
			Thread capturing = new Thread(new Runnable() 
			{
				public void run() 
				{
					AudioInputStream ais = new AudioInputStream(line);
					try
					{
						AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
					}
					catch (Exception e)
					{
						AudioCaptureException ee = new AudioCaptureException(
								"can not write temporary file");
						ee.initCause(e);
						throw ee;
					}
					finally
					{
						try { ais.close(); }
						catch (Exception e) {}
					}
				}
			});
			capturing.start();
		}
		catch (Exception e)
		{
			AudioCaptureException ee = new AudioCaptureException(e.getClass() 
					+ " exception during capturing");
			ee.initCause(e);
			throw ee;
		}
	}

	/** @throws AudioCaptureException in case of exceptions during the process. */
	@Override
	public void stop() throws AudioCaptureException
	{
		try
		{
			if (line == null) return;
			line.stop();
	        line.close();
		}
		catch (Exception e)
		{
			AudioCaptureException ee = new AudioCaptureException(e.getClass() 
					+ " exception during stopping capturing");
			ee.initCause(e);
			throw ee;
		}
	}

	/** @throws AudioPlayException in case of exceptions during the process. */
	@Override
	public void play() throws AudioPlayException
	{
		try
		{
			if (file == null) return;
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		    AudioFormat format = stream.getFormat();
		    DataLine.Info info = new DataLine.Info(Clip.class, format);
		    Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
			clip.addLineListener(event -> 
			{ 
				if (!clip.isRunning()) clip.close();
			});
			stream.close();
			if (isTemp) System.gc(); 	// TODO workaround to close AudioInputStream 
										// and release the temporary files to remove it
		}
		catch (Exception e)
		{
			AudioPlayException ee = new AudioPlayException(e.getClass() 
					+ " exception during playing");
			ee.initCause(e);
			throw ee;
		}
	}
	
	@Override
	public boolean save(File file)
	{
		if (file == null) return false;
		try 
		{
			Files.copy(this.file.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (Exception e) 
		{
			return false;
		}	
		return true;
	}

	@Override
	public boolean load(File file)
	{
		if (file == null || !file.exists()) return false;
		try 
		{
			this.file = file;
			isTemp = false;
		}
		catch (Exception e) 
		{
			return false;
		}	
		return true;
	}

//
// Methods
//
	
	/**
	 * Returns this temporary folder.
	 * @return this temporary folder.
	 */
	public File getTempFolder()
	{
		return tempFolder;
	}
	
	/**
	 * Sets this temporary folder.
	 * @param tempFolder - a new value.
	 * @return <code>true</code> if the new value has been set successfully.
	 */
	public boolean setTempFolder(File tempFolder)
	{
		try
		{
			if (tempFolder == null)
			{
				this.tempFolder = null;
				return true;
			}
			if (!tempFolder.exists())
			{
				if(!tempFolder.mkdirs()) return false;
			}
			if (!tempFolder.isDirectory())
			{
				return false;
			}
			this.tempFolder = tempFolder;
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * Returns an <code>AudioFormat</code> for this object.
	 * @return an <code>AudioFormat</code> for this object.
	 */
	private AudioFormat getAudioFormat()
	{
		final float   sampleRate 	   = 32000; // Hz
		final int 	  sampleSizeInBits = 16; 	// bit
		final int 	  channels 		   = 2;  	// stereo
		final boolean signed 		   = true;
		final boolean bigEndian 	   = false;
		
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
//
// Exceptions
//
	
	class AudioCaptureException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
		public AudioCaptureException() {}
		public AudioCaptureException(String text) {super(text);}
	}
	
	class AudioPlayException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
		public AudioPlayException() {}
		public AudioPlayException(String text) {super(text);}
	}
	
} // end SoundWav
