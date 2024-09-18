package zoeque.jarfileutil.applicaton;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import zoeque.jarfileutil.domain.exception.JarFileReferenceException;

/**
 * The application to find package name from jar references
 */
public class JarPackageFinder {
  /**
   * Constructor
   */
  public JarPackageFinder() {

  }

  /**
   * Get all package name from referred Jar files.
   *
   * @param rootPath root path of referred package name of a jar file
   * @return List of Package names
   * @throws JarFileReferenceException Jar exception
   */
  public static List<String> collectPackageNameFromJar(String rootPath) throws JarFileReferenceException {
    List<String> packages = new ArrayList<>();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    // Get root path from referred jar file
    URL root = classLoader.getResource(rootPath.replace('.', '/'));

    try (JarFile jarFile = ((JarURLConnection) root.openConnection()).getJarFile()) {
      Enumeration<JarEntry> entries = jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        if (entry.getName().endsWith(".class")) {
          String className = entry.getName().replace("/", ".").replace(".class", "");

          if (className.startsWith(rootPath + ".")) {
            // get only package name
            String packageName = className.substring(0, className.lastIndexOf('.'));
            if (!packages.contains(packageName)) {
              packages.add(packageName);
            }
          }
        }
      }
    } catch (IOException e) {
      throw new JarFileReferenceException(e);
    }
    return packages;
  }

  public static List<String> collectPackageNameFromJarPath(String jarFilePath) throws JarFileReferenceException {
    List<String> packages = new ArrayList<>();
    try {
      URL jarUrl = new URL("jar:file:" + jarFilePath + "!/");
      JarFile jarFile = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
      Enumeration<JarEntry> entries = jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        if (entry.getName().endsWith(".class")) {
          String className = entry.getName().replace("/", ".").replace(".class", "");
          String packageName = className.substring(0, className.lastIndexOf('.'));
          if (!packages.contains(packageName)) {
            packages.add(packageName);
          }
        }
      }
    } catch (IOException e) {
      throw new JarFileReferenceException(e);
    }
    return packages;
  }
}
