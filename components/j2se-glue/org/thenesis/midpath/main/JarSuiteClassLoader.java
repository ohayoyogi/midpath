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
package org.thenesis.midpath.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Loads a MIDlet suite's classes and resources from its jar, for Java SE runtimes.
 *
 * MIDlets were written for phone VMs where InputStream.read(byte[], int, int) on a
 * resource filled the whole buffer, and many rely on it, e.g. reading each image of a
 * resource file with a single read() into an array of exactly its size. A Java SE jar
 * stream returns at most a buffer's worth per call, so such images came out truncated.
 * Here resources are served from memory (ByteArrayInputStream), where read() and skip()
 * return everything that is asked for and available, as on a phone.
 *
 * Suite classes are loaded child-first, so this also holds when the jar is on the
 * classpath as well (as older launch scripts put it there).
 */
public class JarSuiteClassLoader extends ClassLoader implements SuiteClassLoader {

	private final Hashtable entries = new Hashtable();

	public JarSuiteClassLoader() {
		super(JarSuiteClassLoader.class.getClassLoader());
	}

	public void open(String path) throws IOException {
		byte[] file = readAll(new FileInputStream(path));
		int offset = isKjx(file) ? kjxJarOffset(file) : 0;
		ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(file, offset, file.length - offset));
		try {
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					entries.put(entry.getName(), readAll(zip));
				}
			}
		} finally {
			zip.close();
		}
	}

	/* MIDletClassLoader */

	public synchronized Class getMIDletClass(String className) throws ClassNotFoundException, InstantiationException {
		Class c = loadClass(className);
		if (!javax.microedition.midlet.MIDlet.class.isAssignableFrom(c)) {
			throw new InstantiationException("Class not a MIDlet");
		}
		return c;
	}

	/** Also java.lang.ClassLoader.getResourceAsStream(): Class.getResourceAsStream() lands here. */
	public InputStream getResourceAsStream(String name) {
		String key = name.startsWith("/") ? name.substring(1) : name;
		byte[] data = (byte[]) entries.get(key);
		if (data != null) {
			return new ByteArrayInputStream(data);
		}
		return super.getResourceAsStream(key);
	}

	/* ClassLoader */

	protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class c = findLoadedClass(name);
		if (c == null) {
			byte[] data = (byte[]) entries.get(name.replace('.', '/') + ".class");
			if (data != null) {
				c = defineClass(name, data, 0, data.length);
			} else {
				return super.loadClass(name, resolve);
			}
		}
		if (resolve) {
			resolveClass(c);
		}
		return c;
	}

	/* Helpers */

	private static byte[] readAll(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int n;
		while ((n = in.read(buf)) > 0) {
			out.write(buf, 0, n);
		}
		return out.toByteArray();
	}

	private static boolean isKjx(byte[] b) {
		return b.length > 3 && b[0] == 'K' && b[1] == 'J' && b[2] == 'X';
	}

	/** Where the jar starts in a .kjx: the same header fields KjxInspectorME reads. */
	private static int kjxJarOffset(byte[] b) throws IOException {
		try {
			int p = 4; // "KJX" and the JAD offset byte
			int nameLength = b[p++] & 0xFF;
			p += nameLength;
			int jadLength = ((b[p] & 0xFF) << 8) | (b[p + 1] & 0xFF);
			p += 2;
			int jadNameLength = b[p++] & 0xFF;
			p += jadNameLength + jadLength;
			if (p >= b.length) {
				throw new IOException("Not a valid kjx file");
			}
			return p;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IOException("Not a valid kjx file");
		}
	}
}
