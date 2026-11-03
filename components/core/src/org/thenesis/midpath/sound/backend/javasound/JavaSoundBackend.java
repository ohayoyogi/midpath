/*
 * This file is part of MIDPath.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details.
 */
package org.thenesis.midpath.sound.backend.javasound;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import org.thenesis.midpath.sound.AudioFormat;
import org.thenesis.midpath.sound.Mixer;
import org.thenesis.midpath.sound.SoftMixer;
import org.thenesis.midpath.sound.SoundBackend;
import org.thenesis.midpath.sound.SoundToolkit;

/**
 * Sound backend for Java SE runtimes, based on javax.sound.sampled.
 *
 * Unlike the ALSA, ESD and PulseAudio backends it needs no native library, so it also works
 * on runtimes such as CheerpJ (Java in the browser). Select it with
 * org.thenesis.midpath.sound.backend: JAVASOUND.
 */
public class JavaSoundBackend implements SoundBackend {

	private SourceDataLine line;
	private SoftMixer mixer;
	private AudioFormat audioFormat;
	private int bufferSize;
	private boolean isOpen = false;

	public synchronized void open() throws IOException {
		if (isOpen) {
			return;
		}
		audioFormat = SoundToolkit.getAudioFormat();
		bufferSize = SoundToolkit.getBufferSize();

		javax.sound.sampled.AudioFormat format = new javax.sound.sampled.AudioFormat(audioFormat.sampleRate,
				audioFormat.bytePerSample * 8, audioFormat.channels, audioFormat.signed, audioFormat.bigEndian);
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			// Room for two mixer buffers: one being played, one being written.
			line.open(format, bufferSize * 2);
			line.start();
		} catch (Throwable e) {
			// Also Errors: e.g. UnsatisfiedLinkError when the audio library is missing.
			line = null;
			throw new IOException("Can not open Java Sound line: " + e);
		}
		mixer = new SoftMixer(this);
		isOpen = true;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public int available() {
		return (line != null) ? line.available() : 0;
	}

	public int write(byte[] buf, int offset, int len) {
		if (line == null) {
			return 0;
		}
		// Blocks until the data fits in the line buffer, which paces the mixer.
		return line.write(buf, offset, len);
	}

	public synchronized void close() {
		if (line != null) {
			line.drain();
			line.close();
			line = null;
		}
		isOpen = false;
	}

	public Mixer getMixer() {
		return mixer;
	}

}
