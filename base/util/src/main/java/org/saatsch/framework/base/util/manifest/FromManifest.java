package org.saatsch.framework.base.util.manifest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility that loads {@link Attributes} from a jar file's manifest.
 */
public class FromManifest {

  private FromManifest() {}
  
  private static final Logger LOG = LoggerFactory.getLogger(FromManifest.class);

  /**
   * loads the main manifest attributes.
   *
   * @param clazz a class in the jar from which to load the manifest.
   * @return the loaded {@link Attributes} or null if the attributes could not be loaded.
   * @throws MalformedURLException
   * @throws IOException
   */
  public static Attributes load(Class clazz) throws IOException {
    String className = clazz.getSimpleName() + ".class";
    String classPath = clazz.getResource(className).toString();
    if (!classPath.startsWith("jar")) {
      LOG.error("can only load manifest from jar. classpath:{}", classPath);
      return null;
    }
    String manifestPath =
        classPath.substring(0, classPath.lastIndexOf('!') + 1) + "/META-INF/MANIFEST.MF";
    Manifest manifest = new Manifest(new URL(manifestPath).openStream());
    return manifest.getMainAttributes();

  }


  /**
   * same as {@link #load(Class)} but swallows (logs to info) any IOException.
   * 
   * @param clazz any Class known to reside in the same jar as the desired manifest.
   * @return
   */
  public static Attributes loadQuietly(Class clazz) {
    String className = clazz.getSimpleName() + ".class";
    String classPath = clazz.getResource(className).toString();
    if (!classPath.startsWith("jar")) {
      return null;
    }
    String manifestPath =
        classPath.substring(0, classPath.lastIndexOf('!') + 1) + "/META-INF/MANIFEST.MF";
    Manifest manifest = null;
    try {
      manifest = new Manifest(new URL(manifestPath).openStream());
    } catch (IOException e) {
      LOG.info("Could not load MANIFEST: {}", e.getMessage());
      return null;
    }
    return manifest.getMainAttributes();

  }

}
