package de.jmmo.networking.impl;

import org.apache.mina.core.session.IoSession;

import de.jmmo.networking.api.AllNetworkMessagesHandler;

public class NoOpAllHandler implements AllNetworkMessagesHandler {

  @Override
  public void handleMessage(IoSession connection, Object message) {

    
  }

}
