package de.jmmo.client;

import org.junit.rules.ExternalResource;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.networking.api.AllNetworkMessagesHandler;
import de.jmmo.networking.api.ConnectionListener;
import de.jmmo.networking.impl.NoOpAllHandler;

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
