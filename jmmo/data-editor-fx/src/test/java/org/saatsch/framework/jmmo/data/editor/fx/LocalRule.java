package org.saatsch.framework.jmmo.data.editor.fx;

import org.junit.rules.ExternalResource;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;

public class LocalRule extends ExternalResource {

  @Override
  protected void before() throws Throwable {
      JmmoContext.alias(MorphiaMongoDataSink.class, TestDataSink.class);
  }
  
  public <T> T getBean(Class<T> type) {
    return JmmoContext.getBean(type);
}
  
}
