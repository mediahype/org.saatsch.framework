package org.saatsch.framework.jmmo.data.api;

import org.junit.rules.ExternalResource;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.DataSink;

public class LocalRule extends ExternalResource {

  @Override
  protected void before() throws Throwable {
      JmmoContext.alias(DataSink.class, TestDataSink.class);
  }
  
  public <T> T getBean(Class<T> type) {
    return JmmoContext.getBean(type);
}
  
}
