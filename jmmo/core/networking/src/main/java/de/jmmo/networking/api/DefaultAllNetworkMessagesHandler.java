package de.jmmo.networking.api;

import org.apache.mina.core.session.IoSession;

/**
 * handles all Network Messages and does nothing (unless {@link #handleMessage(IoSession, Object)}
 * is overridden).
 * @author saatsch
 *
 */
public class DefaultAllNetworkMessagesHandler implements AllNetworkMessagesHandler {

  public void handleMessage(IoSession connection, Object message) {
    // do nothing
  }



}
