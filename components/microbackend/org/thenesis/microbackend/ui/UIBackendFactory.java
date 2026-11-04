/*
 * MIDPath - Copyright (C) 2006-2008 Guillaume Legris, Mathieu Legris
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation. 
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details. 
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA  
 */
package org.thenesis.microbackend.ui;


public class UIBackendFactory {

    //public static final String BACKEND_PACKAGE_PREFIX = "org.thenesis.microbackend.ui.";

    public static final String BACKEND_SDL = "SDL";
    public static final String BACKEND_AWT = "AWT";
    public static final String BACKEND_AWTGRABBER = "AWTGRABBER";
    public static final String BACKEND_SWT = "SWT";
    public static final String BACKEND_X11 = "X11";
    public static final String BACKEND_GTK = "GTK";
    public static final String BACKEND_QT = "QT";
    public static final String BACKEND_FB = "FB";
    public static final String BACKEND_LIBRETRO = "LIBRETRO";
    public static final String BACKEND_NULL = "NULL";

    /**
     * Backends are resolved by name instead of being referenced directly.
     *
     * Naming them in code made the VM resolve -- and therefore load -- every backend
     * class the moment this method was first executed, together with whatever each one
     * drags in. On a constrained target that is a large part of startup, and the
     * backends whose libraries are not shipped (SWT, AWT) cost the most, because a
     * failed class lookup scans every classpath entry and finds nothing. The SDL, X11,
     * SWT and AWT backends were all loaded even when the configuration selected none
     * of them.
     */
    private static final String[][] BACKEND_CLASSES = {
        { BACKEND_SDL,        "org.thenesis.microbackend.ui.sdl.SDLBackend" },
        { BACKEND_AWT,        "org.thenesis.microbackend.ui.awt.AWTBackend" },
        { BACKEND_AWTGRABBER, "org.thenesis.microbackend.ui.awtgrabber.AWTGrabberBackend" },
        { BACKEND_SWT,        "org.thenesis.microbackend.ui.swt.SWTBackend" },
        { BACKEND_X11,        "org.thenesis.microbackend.ui.x11.X11Backend" },
        { BACKEND_GTK,        "org.thenesis.microbackend.ui.gtk.GTKBackend" },
        { BACKEND_QT,         "org.thenesis.microbackend.ui.qt.QTBackend" },
        { BACKEND_FB,         "org.thenesis.microbackend.ui.fb.FBBackend" },
        { BACKEND_LIBRETRO,   "org.thenesis.microbackend.ui.libretro.LibRetroBackend" },
    };

    public static UIBackend createBackend(String name) {

        for (int i = 0; i < BACKEND_CLASSES.length; i++) {
            if (name.equalsIgnoreCase(BACKEND_CLASSES[i][0])) {
                try {
                    return (UIBackend) Class.forName(BACKEND_CLASSES[i][1]).newInstance();
                } catch (Throwable t) {
                    Logging.log("Backend " + name + " is not available: " + t, Logging.WARNING);
                    return null;
                }
            }
        }
        return null;
    }

    public static UIBackend createBackend(Object m) {
        
        if (m == null) {
            return null;
        }

        if (m instanceof UIBackend) {
            return (UIBackend) m;
        }

        /* Both wrappers are reached through reflection for the same reason the table
         * above uses it: a VM that resolves a class's whole constant pool when the class
         * is initialised would load the AWT/SWT wrapper classes for a direct
         * "new AWTWrapperBackend(m)", even on devices that have neither. The guard classes are looked up first, so
         * on such a device nothing beyond the failed lookup happens. */
        UIBackend wrapped = wrap(m, "java.awt.Container", AWT_WRAPPER_CLASS);
        if (wrapped != null) {
            return wrapped;
        }
        wrapped = wrap(m, "org.eclipse.swt.widgets.Canvas", SWT_WRAPPER_CLASS);
        if (wrapped != null) {
            return wrapped;
        }

        return null;

    }

    private static final String AWT_WRAPPER_CLASS = "org.thenesis.microbackend.ui.awt.AWTWrapperBackend";
    private static final String SWT_WRAPPER_CLASS = "org.thenesis.microbackend.ui.swt.SWTWrapperBackend";

    /**
     * Wrap m if it is an instance of guardClassName, by reflectively constructing
     * wrapperClassName. Returns null when the toolkit is absent or m is not one of its
     * containers -- the same outcome the direct code had, without loading the wrapper.
     */
    private static UIBackend wrap(Object m, String guardClassName, String wrapperClassName) {
        Class guardClass;
        try {
            guardClass = Class.forName(guardClassName);
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (!guardClass.isAssignableFrom(m.getClass())) {
            return null;
        }
        try {
            /* Only a no-argument constructor is reachable through CLDC reflection, so the
             * widget is handed over afterwards through WrapperBackend.setTarget(). */
            WrapperBackend backend = (WrapperBackend) Class.forName(wrapperClassName)
                    .newInstance();
            backend.setTarget(m);
            return backend;
        } catch (Exception e) {
            Logging.log("UIBackendFactory: cannot create " + wrapperClassName + ": " + e,
                    Logging.WARNING);
            return null;
        }
    }
    
//    private static void setNativeLibraryLoader(String jvmType) {
//        boolean success = false;
//        try {
//            Class loaderClass = null;
//            if (jvmType.equalsIgnoreCase("J2SE")) {
//                loaderClass = Class.forName("org.thenesis.microbackend.ui.NativeLoaderSE");
//            } else if (jvmType.equalsIgnoreCase("J2ME")) {
//                loaderClass = Class.forName("org.thenesis.microbackend.ui.NativeLoaderME");
//            }
//
//            if (loaderClass != null) {
//                NativeLibraryLoader loader = (NativeLibraryLoader) loaderClass.newInstance();
//                NativeLibraryLoader.setDefaultLoader(loader);
//                success = true;
//            } 
//        } catch (Exception e) {
//        }
//
//        if (!success)
//            Logging.log("A native library loader can't be found", Logging.ERROR);
//    }

    public static UIBackend createBackend(Object m, Configuration backendConfig, BackendEventListener listener) {

        // Create the native library loader required by some backends
//        String jvmType = backendConfig.getParameterDefault("org.thenesis.microbackend.ui.jvm", "J2SE");
//        setNativeLibraryLoader(jvmType);

        // Try to create an object from the given object
        UIBackend backend = createBackend(m);

        // Try to create an object from the configuration file or system properties
        if (backend == null) {
            String backendName = backendConfig.getParameterDefault("org.thenesis.microbackend.ui.backend", "AWT");
            backend = createBackend(backendName);
        }

        // If we fail to find a backend, create a fake one
        if (backend == null) {
            Logging.log("No Backend was not found: NULL backend will be used", Logging.WARNING);
            backend = new NullBackend();
        }

        // Get requested screen size
        int w = backendConfig.getIntParameter("org.thenesis.lwuit.microbackend.screenWidth", 100);
        int h = backendConfig.getIntParameter("org.thenesis.lwuit.microbackend.screenHeight", 100);

        backend.configure(backendConfig, w, h);
        backend.setBackendEventListener(listener);

        return backend;
    }

}
