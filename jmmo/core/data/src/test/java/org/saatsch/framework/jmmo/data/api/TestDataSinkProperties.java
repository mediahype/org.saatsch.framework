package org.saatsch.framework.jmmo.data.api;

import java.util.Properties;

import org.saatsch.framework.base.util.properties.AbstractProperties;

public class TestDataSinkProperties extends AbstractProperties {

  private static final String SERVER = "jmmo.mongo.server";
  private static final String DATABASE_NAME = "jmmo.mongo.database.name";

  public TestDataSinkProperties() {
    super.init();
  }
  
  @Override
  protected void checkProps(Properties props) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected String getPropertiesFileName() {
    return "testDataSink.properties";
  }

  @Override
  protected void defaultProps(Properties props) {
    props.put(SERVER, "localhost:27017");
    props.put(DATABASE_NAME, "jmmo-test");
    
  }
  
  public String getServer() {
    return getProperty(SERVER);
  }

  public String getDatabaseName() {
    return getProperty(DATABASE_NAME);
  }

  
}
