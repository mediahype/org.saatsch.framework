package de.jmmo.networking.impl.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.networking.api.AllNetworkMessagesHandler;
import de.jmmo.networking.api.ConnectionListener;
import de.jmmo.networking.impl.MessageHandlers;
import de.osrg.base.serializing.JsonSerializer;
import de.osrg.base.serializing.SerializerFactory;

/**
 * this is currently running on the server as well as on the client.
 * 
 * @author saatsch
 *
 */
public class ObjectProtocolHandler extends IoHandlerAdapter {

  private static final JsonSerializer SERIALIZER = SerializerFactory.newJsonSerializer();
  
  private static final Logger LOG = LoggerFactory.getLogger(ObjectProtocolHandler.class);

  private MessageHandlers handlers  = JmmoContext.getBean(MessageHandlers.class);

  private ConnectionListener connectionListener = JmmoContext.getBean(ConnectionListener.class);
  
  private AllNetworkMessagesHandler allHandler = JmmoContext.getBean(AllNetworkMessagesHandler.class);

  @Override
  public void messageReceived(IoSession session, Object message) {
    // every network message passes through here.


    LOG.debug("Message received: {}", message.getClass().getSimpleName());
    LOG.debug("{} : {}", message.getClass().getSimpleName(), SERIALIZER.objectToString(message));

    if (handlers.getHandlersForMessage(message).isEmpty()) {
      LOG.warn("Unhandled Message: No specific handler found for message: {} (sending to AllHandler nevertheless)", message.getClass());
    }

    allHandler.handleMessage(session, message);

    
    if (!handlers.getHandlersForMessage(message).isEmpty()) {
      handlers.handleMessage(session, message);
    }
    


  }

  @Override
  public void sessionOpened(IoSession session) throws Exception {
    LOG.info("Session opened: {}", session.getServiceAddress().toString());
  }

  @Override
  public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
    LOG.warn("Exception: {}", cause.getMessage());
    try {
      connectionListener.exceptionCaught(session, cause);      
    } finally {
      // when the container is shutdown, the connection listener does not work anymore.
    }
    // TODO: react differently on client and on server
  }

  @Override
  public void inputClosed(IoSession session) throws Exception {
    LOG.info("Input closed: {}", session);
    super.inputClosed(session);
  }

  @Override
  public void sessionClosed(IoSession session) throws Exception {
    connectionListener.sessionClosed(session);
  }

  /**
   * @return the serializer. Can be customized.
   */
  public JsonSerializer getSerializer(){
    return SERIALIZER;
  }

}
