package de.jmmo.client;

import java.util.Properties;

import de.osrg.base.util.properties.AbstractProperties;

/**
 * Bean not managed by CDI exposing some client properties. Properties are read from a file named
 * {@value #JMMOCLIENT_PROPERTIES_FILENAME}, which must be located in the directory from which the
 * JVM was started. If the file is not present, it will be created and populated with default
 * properties.
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

  private static final String SERVER_UDP_PORT = "jmmoclient.serverUdpPort";
  private static final String SERVER_TCP_PORT = "jmmoclient.serverTcpPort";
  private static final String SERVER_NAME = "jmmoclient.serverName";
  private static final String IGNORE_SERVER_DOWN = "jmmoclient.ignoreServerDown";

  /**
   * we check the props here so that an exception is thrown very early.
   * @param props2Check
   */
  @Override
  protected void checkProps(Properties props2Check) {

    getPropertyInt(SERVER_TCP_PORT);
    getPropertyInt(SERVER_UDP_PORT);
    
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
    props.put(SERVER_UDP_PORT, "54777");
    // TODO: set this to false sometime.
    props.put(IGNORE_SERVER_DOWN, "true");
  }

  /**
   * if during startup of the network client the absence of the server should be ignored. This is a
   * feature for testing, when you dont't need a server.
   * 
   * @return
   */
  public boolean isIgnoreServerDown() {
    return Boolean.parseBoolean(getProperty(IGNORE_SERVER_DOWN));
  }

  public String getServerName() {
    return getProperty(SERVER_NAME);
  }

  public Integer getServerTcpPort() {
    return Integer.parseInt(getProperty(SERVER_TCP_PORT));
  }

  public Integer getServerUdpPort() {
    return Integer.parseInt(getProperty(SERVER_UDP_PORT));
  }

  @Override
  protected String getPropertiesFileName() {
    return JMMOCLIENT_PROPERTIES_FILENAME;
  }

}
