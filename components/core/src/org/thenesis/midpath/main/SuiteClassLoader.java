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

import java.io.IOException;

import com.sun.midp.main.MIDletClassLoader;

/**
 * A MIDletClassLoader that loads a suite's classes and resources from its jar (or .kjx).
 *
 * Implemented in j2se-glue, because defining classes needs java.lang.ClassLoader, which
 * CLDC does not have. MIDletLauncher finds it by name and falls back to the default
 * classpath loading when it is not available.
 */
public interface SuiteClassLoader extends MIDletClassLoader {

	/** Name of the Java SE implementation, looked up by MIDletLauncher. */
	public static final String IMPLEMENTATION = "org.thenesis.midpath.main.JarSuiteClassLoader";

	/** Read the suite from a .jar or .kjx file. Called once, before any other method. */
	public void open(String path) throws IOException;
}
