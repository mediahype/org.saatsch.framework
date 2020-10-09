package org.saatsch.framework.base.util.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * abstract base class for classes that wish to load a properties file.
 * 
 * @author saatsch
 *
 */
public abstract class AbstractProperties {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractProperties.class);
  private Properties props;
  private File propsFile;

  /**
   * constructs {@link AbstractProperties}. Either loads existing file or creates new file with
   * defaults.
   *
   */
  public AbstractProperties() {
    init();
  }


  protected void init() {
    propsFile = new File(getPropertiesFileName());
    props = new Properties();

    if (propsFile.exists()) {
      readProps();
      checkProps(props);
    } else {
      LOG.warn("Properties File: {} not found. Creating default...", getPropertiesFileName());
      defaultProps(props);
      writeProps();
    }

  }

  public String getProperty(String key) {
    return getProperty(key, null);
  }

  /**
   * like {@link #getProperty(String)} but will parse to an Integer, throwing a
   * {@link NumberFormatException} if applicable.
   * 
   * @param key the property's key
   * @return the property's value or <code>null</code> if not found.
   */
  public Integer getPropertyInt(String key) {
    return Integer.parseInt(getProperty(key));
  }

  /**
   * Lookup a property value. Lookup order is
   * <ul>
   * <li>System property</li>
   * <li>Property file</li>
   * </ul>
   * 
   * returns the value of the first occurrence of the property or the given default.
   * 
   * @param key the key to look up
   * @param def the default
   * @return property value for the given key or the given default if the key was not found.
   */
  public String getProperty(String key, String def) {
    if (null != System.getProperty(key)) {
      return System.getProperty(key);
    }
    return props.getProperty(key, def);
  }

  /**
   * store the properties to disk.
   */
  public void store() {
    writeProps();
  }

  public void put(String key, String value) {
    props.put(key, value);
  }

  private void readProps() {
    try (FileInputStream fis = new FileInputStream(propsFile)) {
      props.load(fis);
    } catch (IOException e) {
      LOG.error("", e);
    }
  }

  private void writeProps() {
    try (FileOutputStream fos = new FileOutputStream(propsFile)) {
      props.store(fos, "properties");
    } catch (IOException e) {
      LOG.error("", e);
    }

  }

  /**
   * called after loading an existing properties file. Implementation may want to check the
   * properties for sanity.
   * 
   * @param props the loaded {@link Properties} to be checked.
   */
  protected abstract void checkProps(Properties props);

  /**
   * implementation must return the desired filename of the properties file.
   * 
   * @return the desired filename of the properties file.
   */
  protected abstract String getPropertiesFileName();

  /**
   * implementation must put default values for properties into the provided props. Provided props
   * will be empty.
   * 
   * @param props the properties.
   */
  protected abstract void defaultProps(Properties props);

}
