package org.thenesis.midpath.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import com.sun.midp.installer.ManifestProperties;
import com.sun.midp.log.Logging;

class MIDletRepository {

	private File repositoryDir;
	private Vector installedJars = new Vector();
	private Vector notInstalledJars = new Vector();

	public MIDletRepository(String path) {
		repositoryDir = new File(path);
		repositoryDir = repositoryDir.getAbsoluteFile();
	}

	public void scanRepository() throws IOException {
		//		File[] files = repositoryDir.listFiles(new FilenameFilter() {
		//			public boolean accept(File dir, String name) {
		//				if (name.endsWith("jar")) {
		//					return true;
		//				}
		//				return false;
		//			}
		//		});

		installedJars.removeAllElements();
		notInstalledJars.removeAllElements();

		File[] files = repositoryDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (name.toLowerCase().endsWith(".jar") && files[i].isFile()) {
				File jad = new File(repositoryDir, getJadFileName(name));
				if (jad.exists()) {
					JarInspectorSE jar = new JarInspectorSE(files[i]);
					installedJars.addElement(jar);
					//					System.out.println("[DEBUG] J2SEMidletSuiteLauncher.scanRepository(): installed "
					//							+ files[i].getName());
				} else {
					//					System.out.println("[DEBUG] J2SEMidletSuiteLauncher.scanRepository(): not installed "
					//							+ files[i].getName());
					notInstalledJars.addElement(new JarInspectorSE(files[i]));
				}
			}
		}

	}

	//	public String[] getInstalledSuiteNames() {
	//		Vector installedList = getInstalledJars();
	//		for (int i = 0; i < installedList.size(); i++) {
	//			MIDletSuiteJar jar = (MIDletSuiteJar)installedList.elementAt(i);
	//			ManifestProperties manifestProperties;
	//			try {
	//				manifestProperties = jar.getManifestProperties();
	//				suiteName = manifestProperties.getProperty(ManifestProperties.SUITE_NAME_PROP);
	//				installedGroup.append("Suite 1", null);
	//			} catch (IOException e) {
	//				e.printStackTrace();
	//			}
	//			
	//		}
	//	}

	public void uninstallSuite(String suiteName) throws IOException {
		for (int i = 0; i < installedJars.size(); i++) {
			JarInspectorSE jar = (JarInspectorSE) installedJars.elementAt(i);
			if (jar.getSuiteName().equals(suiteName)) {
				uninstallSuite(jar.getFile());
				break;
			}
		}
	}

	private void uninstallSuite(File jarFile) {
		// Remove the directory matching with the given jar file
		File jad = new File(repositoryDir, getJadFileName(jarFile.getName()));
		jad.delete();
	}

	public JarInspectorSE getJarFromSuiteName(String suiteName) throws IOException {
		for (int i = 0; i < installedJars.size(); i++) {
			JarInspectorSE jar = (JarInspectorSE) installedJars.elementAt(i);
			if (jar.getSuiteName().equals(suiteName)) {
				return jar;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param fileName
	 * @return true if the jar is already installed, false otherwise
	 * @throws IOException
	 */
	public boolean installJar(String fileName) throws IOException {

		if (Logging.TRACE_ENABLED)
			System.out.println("[DEBUG] J2SEMidletSuiteLauncher.installJar(): " + notInstalledJars.size());
		
		for (int i = 0; i < notInstalledJars.size(); i++) {
			JarInspectorSE jar = (JarInspectorSE) notInstalledJars.elementAt(i);
			File file = jar.getFile();
			if (file.getName().equals(fileName)) {
				installJar(jar);
				return true;
			}
		}
		
		return false;
	}

	private void installJar(JarInspectorSE jar) throws IOException {
		// Create a jad with the same name of the jar file
		File jad = new File(repositoryDir, getJadFileName(jar.getFile().getName()));
		PrintWriter writer = new PrintWriter(new FileWriter(jad));

		ManifestProperties properties = jar.getManifestProperties();
		for (int i = 0; i < properties.size(); i++) {
			String key = properties.getKeyAt(i);
			String value = properties.getValueAt(i);
			writer.println(key + ":" + value);
		}

		// Add attributes required by JAD specs
		// References:
		// - http://developers.sun.com/techtopics/mobility/midp/ttips/getAppProperty/index.html)
		// - http://www.onjava.com/pub/a/onjava/excerpt/j2menut_3/index2.html?page=3
		writer.println(ManifestProperties.JAR_URL_PROP + ":" + jar.getFile().getName());
		writer.println(ManifestProperties.JAR_SIZE_PROP + ":" + jar.getFile().length());
		writer.flush();
		writer.close();

	}

	public void removeJar(String fileName) throws IOException {

		if (Logging.TRACE_ENABLED)
			System.out.println("[DEBUG] J2SEMidletSuiteLauncher.removeJar(): " + notInstalledJars.size());

		for (int i = 0; i < notInstalledJars.size(); i++) {
			JarInspectorSE jar = (JarInspectorSE) notInstalledJars.elementAt(i);
			File file = jar.getFile();
			if (file.getName().equals(fileName)) {
				removeJar(file);
				break;
			}
		}
	}

	private void removeJar(File jarFile) {
		jarFile.delete();
	}

	private String getJadFileName(String jarFileName) {
		return (jarFileName.substring(0, jarFileName.length() - 4) + ".jad");
	}

	public Vector getInstalledJars() {
		return installedJars;
	}

	public Vector getNotInstalledJars() {
		return notInstalledJars;
	}

}
