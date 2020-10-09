package org.saatsch.framework.jmmo.networking.impl.mina;

import org.junit.rules.ExternalResource;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.networking.api.AllNetworkMessagesHandler;
import org.saatsch.framework.jmmo.networking.api.ConnectionListener;
import org.saatsch.framework.jmmo.networking.impl.NoOpAllHandler;

public class LocalContext extends ExternalResource {

  @Override
  protected void before() throws Throwable {
      JmmoContext.alias(ConnectionListener.class, LocalConnectionListener.class);
      JmmoContext.alias(AllNetworkMessagesHandler.class, NoOpAllHandler.class);
  }
  
  public <T> T getBean(Class<T> type) {
    return JmmoContext.getBean(type);
}
  
}
