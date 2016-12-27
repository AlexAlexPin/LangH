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

import java.io.*;
import java.nio.file.*;
import javax.sound.sampled.*;

/**
 * Captures an audio from the microphone, keeps and plays it.
 */
public class RecordedSound implements AudioContainer
{
	private boolean running;
	private ByteArrayOutputStream capturedSound;
	private byte[] capturedSoundRaw;

	/**
	 * Creates the AudioFormat object for 2 channels recording in 16 kHz
	 */
	private AudioFormat getAudioFormat() {
		capturedSound = new ByteArrayOutputStream();
		
		float sampleRate = 16000; // Hz
		int sampleSizeInBits = 16; 	// bit
		int channels = 2;  	// stereo
		boolean signed = true;
		boolean bigEndian = false;

		//noinspection ConstantConditions
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	@Override
	public void capture() throws AudioCapturingException {
		try {
			final AudioFormat audioFormat = this.getAudioFormat();
				
			// DataLine interface represents an audio feed from which the audio has been captured
			// In this case is used like an input stream
			DataLine.Info audioFeed = new DataLine.Info(TargetDataLine.class, audioFormat);
			
			// targetDataLine is a subinterface of DataLine to do the actual capturing
			final TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(audioFeed);
			microphone.open(audioFormat);
			microphone.start();
			
			// capture sound
			Thread captureThread = new Thread(new Runnable() {
				// saveSound a captured audio to a byte array for later playing
				final int  bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
				final byte buffer[]   = new byte[bufferSize];
					
				public void run() throws AudioCapturingException {
					running = true; // external trigger
					try {
						while (running) {
							int count = microphone.read(buffer, 0, buffer.length);
							if (count > 0) capturedSound.write(buffer, 0, count);
						}
						capturedSoundRaw = capturedSound.toByteArray();
					}
					finally {
						try {
							capturedSound.close();
							microphone.close();
						}
						catch (IOException e) {
							AudioCapturingException ee = new AudioCapturingException(
									e.getClass() + " " + e.getMessage());
							ee.initCause(e);
							//noinspection ThrowFromFinallyBlock
							throw ee;
						}
					}
				}
			});
			captureThread.start();
		}
		catch (Exception e) {
			AudioCapturingException ee = new AudioCapturingException(e.getClass() + " " + e.getMessage());
			ee.initCause(e);
			throw ee;
		}
	}
	
	@Override
	public void stopCapturing() {
		running = false;
	}

	@Override
	public void play() throws AudioPlayingException {
		try {
			if (capturedSoundRaw == null) throw new AudioPlayingException("RecordedSound in not loaded");
			
			InputStream is = new ByteArrayInputStream(capturedSoundRaw);
			AudioFormat audioFormat = getAudioFormat();
			int audioLength = capturedSoundRaw.length / audioFormat.getFrameSize();
            AudioInputStream audioSource = new AudioInputStream(is, audioFormat, audioLength);
				
			// fetch a SourceDataLine instead of a TargetDataLine for playing audio
			DataLine.Info audioFeed = new DataLine.Info(SourceDataLine.class, audioFormat);
			
			final SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(audioFeed);
			audioLine.open(audioFormat);
			audioLine.start();
			 
			// read the sound
			Thread playThread = new Thread(new Runnable() {
				// read from the buffer and write to the data line
				int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
				byte buffer[]  = new byte[bufferSize];
					
				public void run() {
					try {
						int count;
						while ((count = audioSource.read(buffer, 0, buffer.length)) != -1) {// read from the buffer
							if (count > 0) audioLine.write(buffer, 0, count); // write to the data line
						}
					}
					catch (IOException e) {
						AudioCapturingException ee = new AudioCapturingException(
								e.getClass() + " " + e.getMessage());
						ee.initCause(e).getCause();
						throw ee;
					}
					finally {
						audioLine.drain();
						audioLine.close();
					}
				}
			});
			playThread.start();
		}
		catch (Exception e) {
			AudioPlayingException ee = new AudioPlayingException(e.getClass() + " " + e.getMessage());
			ee.initCause(e);
			throw ee;
		}
	}
	
	@Override
	public boolean loadSound(File file) throws NullPointerException {
		if (file == null) throw new NullPointerException("File is null");
		if (!file.exists()) return false;
		
		try {
			capturedSoundRaw = Files.readAllBytes(file.toPath());
		}
		catch (Exception e) {
			return false;
		}	
		return true;
	}
	
	@Override
	public boolean saveSound(File file) throws NullPointerException {
		if (file == null) throw new NullPointerException("File is null");
		if (capturedSoundRaw == null) return false;

		try {
			Files.write(file.toPath(), capturedSoundRaw);
		}
		catch (Exception e) {
			return false;
		}	
		return true;
	}

    @SuppressWarnings("WeakerAccess")
    public class AudioCapturingException extends RuntimeException
    {
		private static final long serialVersionUID = 1L;
        private AudioCapturingException(String text) { super(text); }
	}

    @SuppressWarnings("WeakerAccess")
    public class AudioPlayingException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
        private AudioPlayingException(String text) { super(text); }
	}
}
