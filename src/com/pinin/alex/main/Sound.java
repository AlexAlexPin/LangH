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

import java.io.*;
import java.nio.file.*;
import javax.sound.sampled.*;

/**
 * Captures an audio from the microphone, keeps and plays it.
 */
public class Sound implements AudioContainer
{
//
// Variables
//
	
	/** Indicates is this thread running or not */
	protected boolean running;
	
	/** Keeps the captured sound */
	private ByteArrayOutputStream soundStream;
	
	/** Keeps content of the <code>ByteArrayOutputStream soundStream</code> */
	private byte[] soundArr;
	
//
// Methods
//
	
	/**
	 * Creates the <code>AudioFormat</code> object for 2 channels recording in 16 kHz 
	 * @return the <code>AudioFormat</code> object
	 */
	private AudioFormat getAudioFormat() 
	{
		soundStream = new ByteArrayOutputStream();
		
		final float   sampleRate 	   = 16000; // Hz
		final int 	  sampleSizeInBits = 16; 	// bit
		final int 	  channels 		   = 2;  	// stereo
		final boolean signed 		   = true;
		final boolean bigEndian 	   = false;
		
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
	/** @throws AudioCapturingException in case of any exceptions during the process */
	@Override
	public void capture() throws AudioCapturingException 
	{
		try 
		{
			final AudioFormat audioFormat = this.getAudioFormat();
				
			// DataLine interface represents an audio feed from which the audio has been captured
			// In this case is used like an input stream
			DataLine.Info audioFeed = new DataLine.Info(TargetDataLine.class, audioFormat);
			
			// targetDataLine is a subinterface of DataLine to do the actual capturing
			final TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(audioFeed);
			microphone.open(audioFormat);
			microphone.start();
			
			// capture sound
			Thread captureThread = new Thread(new Runnable() 
			{
				// save a captured audio to a byte array for later playing
				final int  bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
				final byte buffer[]   = new byte[bufferSize];
					
				public void run() throws AudioCapturingException 
				{
					running = true; // external trigger
					try 
					{
						while (running) 
						{
							int count = microphone.read(buffer, 0, buffer.length);
							if (count > 0) soundStream.write(buffer, 0, count);
						}
						soundArr = soundStream.toByteArray();
					}
					finally 
					{
						try 
						{
							soundStream.close();
						}
						catch (IOException e) 
						{
							AudioCapturingException ee = new AudioCapturingException(
									e.getClass() + " exception in Sound.capture()");
							ee.initCause(e).getCause();
							throw ee;
						}
						microphone.close();
					}
				}
			});
			captureThread.start();
		}
		catch (Exception e) 
		{
			AudioCapturingException ee = new AudioCapturingException(e.getClass() + " exception in Sound.capture()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	@Override
	public void stop() 
	{
		running = false;
	}
	
	/** @throws AudioPlayingException in case of any exceptions during the process */
	@Override
	public void play() throws AudioPlayingException 
	{
		try 
		{
			if (soundArr == null) throw new AudioPlayingException("object has not been loaded in Sound.play()");
			
			final InputStream is = new ByteArrayInputStream(soundArr);
			final AudioFormat audioFormat = getAudioFormat();
			final int audioLength = soundArr.length / audioFormat.getFrameSize();
			final AudioInputStream audioSourse = new AudioInputStream(is, audioFormat, audioLength);
				
			// fetch a SourceDataLine instead of a TargetDataLine for playing audio
			DataLine.Info audioFeed = new DataLine.Info(SourceDataLine.class, audioFormat);
			
			final SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(audioFeed);
			audioLine.open(audioFormat);
			audioLine.start();
			 
			// read the sound
			Thread playThread = new Thread(new Runnable() 
			{
				// read from the buffer and write to the data line
				int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
				byte buffer[]  = new byte[bufferSize];
					
				public void run() 
				{
					try 
					{
						int count;
						while ((count = audioSourse.read(buffer, 0, buffer.length)) != -1) // read from the buffer
						{ 
							if (count > 0) audioLine.write(buffer, 0, count); // write to the data line
						}
					}
					catch (IOException e) 
					{
						AudioCapturingException ee = new AudioCapturingException(
								e.getClass() + " exception in Sound.play()");
						ee.initCause(e).getCause();
						throw ee;
					}
					finally 
					{
						audioLine.drain();
						audioLine.close();
					}
				}
			});
			playThread.start();
		}
		catch (Exception e) 
		{
			AudioPlayingException ee = new AudioPlayingException(e.getClass() + " exception in Sound.play()");
			ee.initCause(e);
			throw ee;
		}
	}
	
	@Override
	public boolean load(File file) throws NullPointerException
	{
		if (file == null) throw new NullPointerException("null value in Sound.load()");
		if (!file.exists()) return false;
		
		try 
		{
			soundArr = Files.readAllBytes(file.toPath());
		}
		catch (Exception e) 
		{
			return false;
		}	
		return true;
	}
	
	@Override
	public boolean save(File file) throws NullPointerException
	{
		if (file == null) throw new NullPointerException("null value in Sound.save()");
		if (soundArr == null) return false;
		
		try 
		{
			Files.write(file.toPath(), soundArr);
		}
		catch (Exception e) 
		{
			return false;
		}	
		return true;
	}

//	
// Exceptions	
//	
	
	/**
	 * Represents exceptions of sound capturing
	 */
	public class AudioCapturingException extends RuntimeException 
	{
		private static final long serialVersionUID = 1L;
		public AudioCapturingException() {}
		public AudioCapturingException(String text) { super(text); }
	}
	
	/**
	 * Represents exceptions of sound playing
	 */
	public class AudioPlayingException extends RuntimeException 
	{
		private static final long serialVersionUID = 1L;
		public AudioPlayingException() {}
		public AudioPlayingException(String text) { super(text); }
	}
	
} // end Sound
