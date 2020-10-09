package org.saatsch.framework.base.classpath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.reflections8.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * helps to speed up the process of starting {@link Reflections} by saving a list of all packages
 * 
 * @author saatsch
 *
 */
public abstract class ConfiguringReflections {

  private static final Logger LOG = LoggerFactory.getLogger(ConfiguringReflections.class);

  private static final String AUTOCONFIG_DIR = "data/autoconfig";

  /**
   * reads the config file if it exists and returns a configured {@link Reflections}. If it does not
   * exist, returns a new {@link Reflections}. In that case the user should create one by calling {@link #write(Set)}.
   * 
   * @return
   */
  public Reflections read() {

    if (Files.exists(getPath())) {
      try {
        Stream<String> lines = Files.lines(getPath());
        Object[] array = lines.toArray();
        lines.close();
        return new Reflections(array);
      } catch (IOException e) {
        LOG.error("Error reading file: ", e);
        return new Reflections();
      }
    }

    return new Reflections();

  }

  /**
   * writes a HashSet of package names, given a Set of classes into the config file.
   * 
   * @param result
   */
  public void write(Set<Class<?>> result) {

    try {
      writeInternal(result);
    } catch (IOException e) {
      LOG.error("Error: ", e);
    }

  }


  private void writeInternal(Set<Class<?>> result) throws IOException {

    Set<String> packages = new HashSet<>();
    for (Class c : result) {
      packages.add(c.getPackage().getName());
    }

    createConfigFile();

    try (BufferedWriter writer = Files.newBufferedWriter(getPath())) {
      for (String s : packages) {
        writer.write(s + "\n");
      }
    }
  }

  private Path getPath() {
    return Paths.get(AUTOCONFIG_DIR, getConfigName());
  }

  private void createConfigFile() {

    try {
      Files.createDirectories(getPath().getParent());
      if (!Files.exists(getPath())) {
        Files.createFile(getPath());
      }
    } catch (IOException e) {
      LOG.error("Error: ", e);
    }


  }

  /**
   * implemetations provide the name of their config file here.
   * 
   * @return the name of the config file
   */
  public abstract String getConfigName();


}
