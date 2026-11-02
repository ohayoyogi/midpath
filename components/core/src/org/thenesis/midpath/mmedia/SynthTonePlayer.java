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
package org.thenesis.midpath.mmedia;

import java.util.Vector;

import javax.microedition.media.MediaException;

import org.thenesis.midpath.sound.AudioFormat;
import org.thenesis.midpath.sound.Line;
import org.thenesis.midpath.sound.Mixer;
import org.thenesis.midpath.sound.SoundBackend;

/**
 * Manager.playTone() implementation that synthesizes a square wave and plays it through the
 * MIDPath sound backend (SoundToolkit).
 *
 * playTone() only queues the tone and returns, as the MIDP spec requires. A single worker
 * thread renders queued tones one after another. If a MIDlet requests tones faster than they
 * can be played, the oldest pending ones are dropped so that sound does not lag behind the
 * game.
 */
public class SynthTonePlayer implements TonePlayer, Runnable {

	/** Tones waiting to be played; older ones are dropped beyond this. */
	private static final int MAX_PENDING = 4;
	/** Fade in/out length, to avoid clicks at the tone edges. */
	private static final int FADE_MS = 3;
	/** Peak amplitude at volume 100 (16-bit signed). */
	private static final int MAX_AMPLITUDE = 9000;

	private final SoundBackend backend;
	private final Vector pending = new Vector();
	private Thread worker;
	/** Set when the backend could not be opened; later calls fail fast. */
	private String openError;
	private Line line;
	private AudioFormat format;
	private int bufferSize;

	public SynthTonePlayer(SoundBackend backend) {
		this.backend = backend;
	}

	public void playTone(int note, int duration, int volume) throws MediaException {
		if (volume <= 0 || duration <= 0) {
			return;
		}
		if (volume > 100) {
			volume = 100;
		}
		synchronized (pending) {
			if (openError != null) {
				throw new MediaException(openError);
			}
			if (worker == null) {
				try {
					open();
				} catch (Throwable e) {
					openError = "Can not open sound backend: " + e;
					System.err.println("[MIDPath] " + openError);
					throw new MediaException(openError);
				}
				worker = new Thread(this);
				worker.start();
			}
			while (pending.size() >= MAX_PENDING) {
				pending.removeElementAt(0);
			}
			pending.addElement(new int[] { note, duration, volume });
			pending.notify();
		}
	}

	private void open() throws Exception {
		if (!backend.isOpen()) {
			backend.open();
		}
		format = backend.getAudioFormat();
		bufferSize = backend.getBufferSize();
		Mixer mixer = backend.getMixer();
		line = mixer.createLine(format);
		mixer.addLine(line);
	}

	public void run() {
		while (true) {
			int[] tone;
			synchronized (pending) {
				while (pending.isEmpty()) {
					// Not running while idle, so the mixer does not wait for this line.
					line.stop();
					try {
						pending.wait();
					} catch (InterruptedException e) {
						return;
					}
				}
				tone = (int[]) pending.elementAt(0);
				pending.removeElementAt(0);
			}
			line.start();
			byte[] pcm = render(tone[0], tone[1], tone[2]);
			line.write(pcm, 0, pcm.length);
		}
	}

	/**
	 * Renders a tone as 16-bit signed little-endian PCM in the mixer's format, padded with
	 * silence to a whole number of mixer buffers so the mixer plays it without waiting for more.
	 */
	private byte[] render(int note, int duration, int volume) {
		int rate = format.sampleRate;
		int channels = format.channels;
		int frameBytes = 2 * channels;
		int frames = (int) ((long) rate * duration / 1000);
		int bytes = frames * frameBytes;
		int padded = ((bytes + bufferSize - 1) / bufferSize) * bufferSize;
		byte[] pcm = new byte[padded];

		// Frequency of the MIDI note (A4 = note 69 = 440 Hz), as a 32-bit fixed-point fraction of a cycle per sample.
		double freq = 440.0 * pow2((note - 69) / 12.0);
		long step = (long) (freq * 65536.0 * 65536.0 / rate);
		long phase = 0;

		int amplitude = MAX_AMPLITUDE * volume / 100;
		int fade = Math.max(1, rate * FADE_MS / 1000);
		int pos = 0;
		for (int i = 0; i < frames; i++) {
			int env = amplitude;
			if (i < fade) {
				env = amplitude * i / fade;
			} else if (frames - i < fade) {
				env = amplitude * (frames - i) / fade;
			}
			// Upper half of the phase cycle is high, lower half low: a square wave.
			int sample = ((phase & 0x80000000L) == 0) ? env : -env;
			phase = (phase + step) & 0xFFFFFFFFL;
			byte lo = (byte) (sample & 0xFF);
			byte hi = (byte) ((sample >> 8) & 0xFF);
			for (int c = 0; c < channels; c++) {
				pcm[pos++] = lo;
				pcm[pos++] = hi;
			}
		}
		return pcm;
	}

	/** 2^x for the small range MIDI notes need (CLDC has no Math.pow). */
	private static double pow2(double x) {
		double result = 1.0;
		int whole = (int) Math.floor(x);
		double frac = x - whole;
		for (int i = 0; i < whole; i++) {
			result *= 2.0;
		}
		for (int i = 0; i > whole; i--) {
			result /= 2.0;
		}
		// 2^frac = e^(frac ln 2), by Taylor series; frac is in [0, 1).
		double y = frac * 0.6931471805599453;
		double term = 1.0, sum = 1.0;
		for (int n = 1; n < 16; n++) {
			term *= y / n;
			sum += term;
		}
		return result * sum;
	}

}
