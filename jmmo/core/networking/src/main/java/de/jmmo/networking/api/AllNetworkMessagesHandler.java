package de.jmmo.networking.api;

import org.apache.mina.core.session.IoSession;


public interface AllNetworkMessagesHandler {

  public void handleMessage(IoSession connection, Object message);


}
