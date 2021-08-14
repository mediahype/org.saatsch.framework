package org.saatsch.framework.jmmo.client;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.saatsch.framework.base.util.properties.AbstractProperties;

/**
 * Exposes some client properties. Properties are read from a file named
 * {@value #JMMOCLIENT_PROPERTIES_FILENAME}, which must be located in the directory from which the
 * JVM was started. If the file is not present, it will be created and populated with default
 * properties.
 * 
 * The ClientConfig maintains a list of servers: {@link #getServerLocations()}.
 * 
 * 
 * 
 * @author saatsch
 * 
 */
public class ClientConfig extends AbstractProperties {

  /**
   * constructs a new {@link ClientConfig}
   * 
   */
  public ClientConfig() {
    super();
  }

  /**
   * the name of the properties file
   */
  private static final String JMMOCLIENT_PROPERTIES_FILENAME = "jmmoclient.properties";


  private static final String SERVER_TCP_PORT = "jmmoclient.serverTcpPort";
  private static final String SERVER_NAME = "jmmoclient.serverName";
  
  private static final String SERVER_LOCATIONS = "jmmoclient.serverLocations";
  
  private static final String ASSETS_DIR = "jmmoclient.assetsDir";
  private static final String LANGUAGE = "jmmoclient.assetsDir";
  
  
  /**
   * we check the props here so that an exception is thrown very early.
   * @param props2Check
   */
  @Override
  protected void checkProps(Properties props2Check) {

    getPropertyInt(SERVER_TCP_PORT);
    
  }

  /**
   * creates the default properties
   * 
   * @param props
   */
  @Override
  protected void defaultProps(Properties props) {

    props.put(SERVER_NAME, "localhost");
    props.put(SERVER_TCP_PORT, "54555");
    // TODO: set this to false sometime.
    
    props.put(ASSETS_DIR, "data/assets");
    props.put(LANGUAGE, defaultLanguage());
  }



  public String getServerName() {
    return getProperty(SERVER_NAME);
  }

  public Integer getServerTcpPort() {
    return Integer.parseInt(getProperty(SERVER_TCP_PORT));
  }

  public String getAssetsDir() {
    Objects.requireNonNull(getProperty(ASSETS_DIR), "Asset directory not configured. please configure: " + ASSETS_DIR);
    return getProperty(ASSETS_DIR);
  }


  @Override
  protected String getPropertiesFileName() {
    return JMMOCLIENT_PROPERTIES_FILENAME;
  }

  public Optional<String> getServerLocations() {
    return Optional.ofNullable(getProperty(SERVER_LOCATIONS));
  }

  public String getLanguage() {
    String property = getProperty(LANGUAGE);
    if (null == property) {
      return defaultLanguage();
    }
    return property;
  }

  private String defaultLanguage() {
    return Locale.getDefault().getLanguage();
  }
  
}
