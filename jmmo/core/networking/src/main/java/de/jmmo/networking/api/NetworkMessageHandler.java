package de.jmmo.networking.api;

import org.apache.mina.core.session.IoSession;

import de.jmmo.cdi.container.JmmoContext;

/**
 * Let your network message handlers implement this interface. Apply the
 * {@link ANetworkMessageHandler} annotation to those classes in order to specify which message
 * type(s) they want to handle.
 * 
 * NetworkMessageHandlers must be thread safe. They cannot use CDI (no Inject, no Scope), but can
 * lookup beans via the {@link JmmoContext} utility.
 * 
 * 
 * 
 * 
 * @author saatsch
 * 
 */
public interface NetworkMessageHandler {

  /**
   * handle network Message(s) here. The message is guaranteed to be of one of the types specified
   * in the {@link ANetworkMessageHandler} annotation.
   * 
   * @param connection the NetworkConnection on which the message arrived.
   * @param message the message.
   */
  public void handleMessage(IoSession connection, Object message);

}
