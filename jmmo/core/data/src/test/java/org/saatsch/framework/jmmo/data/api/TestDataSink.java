package org.saatsch.framework.jmmo.data.api;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.DataSink;

import dev.morphia.Morphia;

/**
 * data sink scoped to the unit tests of jmmo.
 * 
 * @author saatsch
 *
 */
public class TestDataSink extends DataSink {

  private Lazy<TestDataSinkProperties> props =
      Lazy.of(() -> JmmoContext.getBean(TestDataSinkProperties.class));

  public TestDataSink() {
    super.init();
  }

  @Override
  protected void mapPackages(Morphia morphia) {

    morphia.mapPackage("org.saatsch.framework.jmmo.data.api.model");
    
  }

  @Override
  protected String dbName() {

    return props.get().getDatabaseName();
  }

  @Override
  protected String dbServer() {
    return props.get().getServer();
  }

}
