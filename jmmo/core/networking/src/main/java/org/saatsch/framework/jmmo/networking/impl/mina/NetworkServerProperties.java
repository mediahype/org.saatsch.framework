package org.saatsch.framework.jmmo.networking.impl.mina;

import java.util.Properties;

import org.saatsch.framework.base.util.properties.AbstractProperties;

public class NetworkServerProperties extends AbstractProperties {

  /**
   * the default name of the properties file
   */
  private static final String JMMOSERVER_PROPERTIES_FILENAME = "jmmoserver.properties";

  /**
   * system property under which the name of a properties file can be given. Takes precedence over
   * the default properties file name.
   */
  private static final String JMMOSERVER_PROPERTIES_FILENAME_SYSPROP = "jmmoserver.properties.file";



  private static final String SERVER_TCP_PORT = "serverTcpPort";
  private static final String SERVER_ASSETS_DIR = "assetsDir";

  public NetworkServerProperties() {
    super.init();
  }


  @Override
  protected void checkProps(Properties props) {
    // no check required

  }

  @Override
  protected String getPropertiesFileName() {

    String property = System.getProperty(JMMOSERVER_PROPERTIES_FILENAME_SYSPROP);
    if (null != property) {
      return property;
    }

    return JMMOSERVER_PROPERTIES_FILENAME;
  }

  @Override
  protected void defaultProps(Properties props) {
    props.put(SERVER_TCP_PORT, "54555");
  }

  public Integer getServerTcpPort() {
    return Integer.parseInt(getProperty(SERVER_TCP_PORT));
  }
  
  public String getAssetsDir() {
    return getProperty(SERVER_ASSETS_DIR);
  }

}
