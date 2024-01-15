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
package org.thenesis.midpath.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.sun.midp.installer.ManifestProperties;
import com.sun.midp.main.BaseMIDletSuiteLauncher;
import com.sun.midp.main.Configuration;
import com.sun.midp.main.MIDletClassLoader;
import com.sun.midp.midlet.InternalMIDletSuiteImpl;
import com.sun.midp.midlet.MIDletSuite;
import com.sun.midp.midletsuite.MIDletInfo;

public class SuiteManager {

	static MIDletRepository repository;
	static String repositoryPath;

	public SuiteManager() {
		
		// Load system properties required by MIDP2 and JSR specs
		MIDletLauncherSE.callSystemPropertiesLoader();
		
		// Initialize repository stuff
		repositoryPath = Configuration.getPropertyDefault("org.thenesis.midpath.main.repositoryPath", "");
		//System.out.println("repositoryPath: " + repositoryPath);
		repository = new MIDletRepository(repositoryPath);
	}

	public void launchManager() throws Exception {

		BaseMIDletSuiteLauncher.initialize();

		// Initialize the manager MIDlet 
		BaseMIDletSuiteLauncher.launch("org.thenesis.midpath.main.SuiteManagerMIDlet", "Suite Manager");

		// Get the launch infos from the MIDlet
		final MIDletInfo info = SuiteManagerMIDlet.launchMidletInfo;
		final JarInspectorSE midletSuiteJar = SuiteManagerMIDlet.jarInspector;

		if (info != null) {

			// Launch the MIDlet returned by the manager
			BaseMIDletSuiteLauncher.setMIDletClassLoader(new J2SEMIDletClassLoader(midletSuiteJar));
			String suiteName = midletSuiteJar.getSuiteName();
			String suiteId = InternalMIDletSuiteImpl.buildSuiteID(suiteName);
			MIDletSuite midletSuite = InternalMIDletSuiteImpl.create(midletSuiteJar.getManifestProperties(), suiteName,
					suiteId);
			BaseMIDletSuiteLauncher.launch(midletSuite, info.classname);
		}

		// Clean all and stop the VM
		BaseMIDletSuiteLauncher.close();

	}

	void launchFirstMIDlet(File file) throws Exception {

		BaseMIDletSuiteLauncher.initialize();

		final JarInspectorSE midletSuiteJar = new JarInspectorSE(file);
		MIDletInfo[] infos = midletSuiteJar.getMIDletInfo();

		ManifestProperties manifestProperties = midletSuiteJar.getManifestProperties();
		int size = manifestProperties.size();
		for (int i = 0; i < size; i++) {
			System.out.println(manifestProperties.getKeyAt(i) + "=" + manifestProperties.getValueAt(i));
		}

		final MIDletInfo info = infos[0];
		if (infos.length == 0) {
			System.out.println("No MIDlet found");
			return;
		}

		BaseMIDletSuiteLauncher.setMIDletClassLoader(new J2SEMIDletClassLoader(midletSuiteJar));

		BaseMIDletSuiteLauncher.launch(info.classname, info.name);
		BaseMIDletSuiteLauncher.close();

	}

	void launch(File file, String className, String midletName) throws Exception {

		BaseMIDletSuiteLauncher.initialize();

		final JarInspectorSE midletSuiteJar = new JarInspectorSE(file);
		MIDletInfo[] infos = midletSuiteJar.getMIDletInfo();

		//		ManifestProperties manifestProperties = midletSuiteJar.getManifestProperties();
		//		int size = manifestProperties.size();
		//		for (int i = 0; i < size; i++) {
		//			System.out.println(manifestProperties.getKeyAt(i) + "=" + manifestProperties.getValueAt(i));
		//		}

		int index = -1;
		for (int i = 0; i < infos.length; i++) {
			if (className.equals(infos[i].classname)) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			System.out.println("No MIDlet with class name " + className + " was found");
			return;
		}

		BaseMIDletSuiteLauncher.setMIDletClassLoader(new J2SEMIDletClassLoader(midletSuiteJar));
		BaseMIDletSuiteLauncher.launch(className, midletName);
		BaseMIDletSuiteLauncher.close();
	}

	//	public void launchAndClose(URL url, String className, String midletName) throws Exception {
	//		BaseMIDletSuiteLauncher.initialize();
	//		launch(url, className, midletName);
	//		BaseMIDletSuiteLauncher.close();
	//	}

	//	public String getId() {
	//		String id = manifestProperties.getProperty(ManifestProperties.SUITE_NAME_PROP);
	//		id.replace(' ', '_');
	//		return id;
	//	}

	//	private void initialize(String url) throws MalformedURLException {
	//		jarURL = new URL(url);
	//		
	//	}

	//	public MIDletSuiteInfo[] getMidletSuiteInfo() throws IOException {
	//		
	//		ManifestProperties manifest = getManifestProperties();
	//		MIDletSuiteInfo[] suiteInfos = new MIDletSuiteInfo[manifests.length];
	//		
	//		for (int i = 0; i < manifests.length; i++) {
	//			ManifestProperties  p = manifests[i];
	//			MIDletInfo[] infos = getMIDletInfo(p);
	//			suiteInfos[i] = new MIDletSuiteInfo(p, infos);
	//		}
	//		
	//		return suiteInfos;
	//		
	//	}

	//	public MIDletInfo[] getMIDletInfo(ManifestProperties manifestProperties) throws IOException {
	//
	//		String midlet = null;
	//		MIDletInfo midletInfo = null;
	//		Vector infoList = new Vector();
	//
	//		for (int i = 1;; i++) {
	//			midlet = manifestProperties.getProperty("MIDlet-" + i);
	//			if (midlet == null) {
	//				break;
	//			}
	//
	//			/*
	//			 * Verify the MIDlet class is present in the JAR
	//			 * An exception thrown if not.
	//			 * Do the proper install notify on an exception
	//			 */
	//
	//			midletInfo = new MIDletInfo(midlet);
	//			infoList.addElement(midletInfo);
	//			//verifyMIDlet(midletInfo.classname);
	//
	//		}
	//
	//		MIDletInfo[] infos = new MIDletInfo[infoList.size()];
	//		for (int j = 0; j < infoList.size(); j++) {
	//			infos[j] = (MIDletInfo) infoList.elementAt(j);
	//		}
	//
	//		return infos;
	//
	//	}

	//	public ManifestProperties getManifestProperties(MidletJarClassLoader loader) throws IOException {
	//		InputStream is = loader.getManifest();
	//		ManifestProperties manifestProperties = new ManifestProperties();
	//		manifestProperties.load(is);
	//		return manifestProperties;
	//
	//	}

	//	public ManifestProperties[] getManifests() throws IOException {
	//		
	//		Vector v = new Vector();
	//		System.out.println("a");
	//		
	////		URL url =getClass().getResource("/META-INF/MANIFEST.MF");
	////		InputStream is = url.openStream();
	////		ManifestProperties manifestProperties = new ManifestProperties();
	////		manifestProperties.load(is);
	////		v.add(manifestProperties);
	////		System.out.println(url);
	//		
	//		Enumeration e = getClass().getClassLoader().getSystemResources("/META-INF/MANIFEST.MF");
	//		
	//		while(e.hasMoreElements()) {
	//			URL url = (URL)e.nextElement();
	//			InputStream is = url.openStream();
	//			ManifestProperties manifestProperties = new ManifestProperties();
	//			manifestProperties.load(is);
	//			v.add(manifestProperties);
	//			System.out.println(url);
	//		}
	//		
	//		ManifestProperties[] properties = new ManifestProperties[v.size()]; 
	//		for (int i = 0; i < v.size(); i++) {
	//			properties[i] = (ManifestProperties) v.get(i);
	//		}
	//		
	//		return properties;
	//		
	//	}

	//	public void verifyMIDlet(String classname)
	//    throws InvalidJadException
	//{
	//    if (classname == null ||
	//        classname.length() == 0) {
	//        throw new
	//            InvalidJadException(InvalidJadException.INVALID_VALUE);
	//    }
	//
	//    String file = classname.replace('.', '/').concat(".class");
	//
	//    try {
	//        /* Attempt to read the MIDlet from the JAR file. */
	//        if (JarReader.readJarEntry(state.tempFilename, file) != null) {
	//            return;                // File found, normal return
	//        }
	//        // Fall into throwing the exception
	//    } catch (IOException ioe) {
	//        // Fall into throwing the exception
	//    }
	//    // Throw the InvalidJadException
	//    throw new InvalidJadException(InvalidJadException.CORRUPT_JAR, file);
	//}

	//state.id = state.midletSuiteStorage.createSuiteID(state.vendor,
	//      state.suiteName);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			SuiteManager launcher = new SuiteManager();
			if (args.length > 0) {
				if (args[0].equals("-i") || args[0].equals("--install")) {
					repository.scanRepository();
					try {
						String repositoryAbsolutePath = new File(repositoryPath).getAbsolutePath();
						if (repository.installJar(args[1])) {
							System.out.println(args[1] + " was installed in " + repositoryAbsolutePath);
						} else {
							System.out.println(args[1] + " is already installed in " + repositoryAbsolutePath);
						}
					} catch (IOException e) {
						System.out.println("Error: " + args[1] + " can't be installed");
						e.printStackTrace();
						System.exit(1);
					}		
				} else {
					System.out.println("Usage:");
					System.out.println(" java org.thenesis.midpath.main.SuiteManager");
					System.out.println(" java org.thenesis.midpath.main.SuiteManager [-i|--install] <jar-name>");
				}
			} else {
				launcher.launchManager();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//File file = new File("ext/games/MobileSudoku/MobileSudoku.jar");
		//File file = new File("E:/Development/eclipse-3.2/workspace/mipd2-sdl-test/deployed/mipd2-sdl-test.jar");
		//		try {
		//			J2SEMidletSuiteLauncher launcher = new J2SEMidletSuiteLauncher();
		//			launcher.launchManager();
		//			//launcher.launchFirstMIDlet(file.toURL());
		//			//launcher.launchManager("E:/Development/eclipse-3.2/workspace/mipd2-sdl-test/deployed/");
		//
		//			//			MIDletRepository rep = new MIDletRepository("E:/Development/eclipse-3.2/workspace/mipd2-sdl-test/deployed/");
		//			//			System.out.println(rep.getInstallDirectory("mipd2-sdl-test.jar"));
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}

		//		File file = new File("ext/games/MobileSudoku/MobileSudoku.jar");
		//		System.out.println(file.exists());
		//		try {
		//			MidletJarClassLoader loader = new MidletJarClassLoader(file.toURL());
		////			Class c = loader.loadClass("asteroids.Game");
		////			System.out.println(c);
		////			URL url = loader.findResource("/meta-inf/manifest.mf");
		////			System.out.println(url);
		//			InputStream is = loader.getManifest();
		//			ManifestProperties manifestProperties = new ManifestProperties();
		//			manifestProperties.load(is);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}

		//		MidletSuiteLauncher launcher =  new MidletSuiteLauncher();
		//		
		//		URL url =	launcher.getClass(). getResource("/META-INF/MANIFEST.MF");
		//		System.out.println(url );

		//		File file = new File("ext/games/MobileSudoku/MobileSudoku.jar");
		//		try {
		//			MidletSuiteLauncher launcher =  new MidletSuiteLauncher();
		//			launcher.start();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}

	}

	//	class MIDletSuiteInfo {
	//
	//		MIDletInfo[] midletInfo;
	//		ManifestProperties manifestProperties;
	//
	//		public MIDletSuiteInfo(ManifestProperties p, MIDletInfo[] infos) {
	//			manifestProperties = p;
	//			midletInfo = infos;
	//		}
	//
	//		public String getId() {
	//			String id = manifestProperties.getProperty(ManifestProperties.SUITE_NAME_PROP);
	//			id.replace(' ', '_');
	//			return id;
	//		}
	//	}

}

class J2SEMIDletClassLoader implements MIDletClassLoader {

	private JarInspectorSE midletSuiteJar;
	private Class midletClass;

	public J2SEMIDletClassLoader(JarInspectorSE midletSuiteJar) {
		this.midletSuiteJar = midletSuiteJar;
	}

	public synchronized Class getMIDletClass(String className) throws ClassNotFoundException, InstantiationException {
		midletClass = midletSuiteJar.getURLClassLoader().loadClass(className);
		if (!Class.forName("javax.microedition.midlet.MIDlet").isAssignableFrom(midletClass)) {
			throw new InstantiationException("Class not a MIDlet");
		}
		return midletClass;
	}

	public synchronized InputStream getResourceAsStream(String name) {
		InputStream is = midletClass.getResourceAsStream(name);
		//System.out.println(name + " : " + is);
		return is;
	}
}
