This repository is originated from [j2me-preservation/midpath](https://github.com/j2me-preservation/midpath).

This project currently based on `v0.3-rc2` tag in original midpath project. New default branch is named `main` and will be maintained mainly.

MIDPath README
===============

MIDPath is a project to create a free software implementation of the MIDP2 
core class libraries.

MIDPath works on top of various graphical libraries (SDL, AWT, SWT, X11, GTK, 
Qt3/Qt4/Qtopia4, Linux framebuffer) and audio libraries (SDL, ALSA, ESounD, PulseAudio)

MIDPath works with free Java SE environments: JVMs working with GNU Classpath 
(Cacao, Kaffe , JamVM), OpenJDK. It also works with the J2ME/CLDC version of 
the Cacao VM and so can be used as an alternative to phoneME.
	
The Java code is based on phoneME, a free implementation of MIDP2 from Sun.

Features
--------

- JSR118: MIDP2.1 API
- JSR172: J2ME Web Services API
- JSR179: Location API
- JSR184: Mobile 3D Graphics API
- JSR205: Wireless Messaging API (not functional yet)
- JSR226: Scalable 2D Vector Graphics API
- JSR239: OpenGL ES API (1.0)

Licensing
---------

MIDPath is released under the GPL license. 
See the LICENSE file.

Repository structure:
-----------------------
	
	|-- bin: script files
	|-- components: sources of the MIDPath components
	|-- configuration: configuration files
	|-- demos: sources of the demos 
	|-- external: sources of external libraries
	|-- lib: libraries needed by MIDPath's core library
	|-- native: native code (JNI)
	|-- tests: sources of the test classes
	. AUTHORS
	. build.sh: the build script
	. CHANGES
	. COPYING
	. INSTALL
	. LICENSE
	. README
	. THANKYOU
