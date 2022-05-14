package org.saatsch.framework.jmmo.data.editor.fx;

import dev.morphia.Morphia;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

/**
 * data sink scoped to the unit tests of jmmo.data-editor-fx.
 * 
 * @author saatsch
 *
 */
public class TestDataSink extends MorphiaMongoDataSink {

  private Lazy<TestDataSinkProperties> props =
      Lazy.of(() -> JmmoContext.getBean(TestDataSinkProperties.class));

  public TestDataSink() {
    super.init();
  }

  @Override
  protected void mapPackages(Morphia morphia) {

    morphia.mapPackage("org.saatsch.framework.jmmo.data.api.model");
    morphia.mapPackage("org.saatsch.framework.jmmo.data.editor.fx.model");

    
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
