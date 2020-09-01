package de.jmmo.data.api;

import org.junit.rules.ExternalResource;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.mongo.MorphiaMongoDataSink;

public class LocalRule extends ExternalResource {

  @Override
  protected void before() throws Throwable {
      JmmoContext.alias(MorphiaMongoDataSink.class, TestDataSink.class);
  }
  
  public <T> T getBean(Class<T> type) {
    return JmmoContext.getBean(type);
}
  
}
