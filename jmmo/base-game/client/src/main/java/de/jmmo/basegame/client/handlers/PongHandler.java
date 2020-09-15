package de.jmmo.basegame.client.handlers;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.basegame.common.Ping;
import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;

@ANetworkMessageHandler(listenTo = {Ping.class})
public class PongHandler implements NetworkMessageHandler {

  private static final Logger LOG = LoggerFactory.getLogger(PongHandler.class);
  
  @Override
  public void handleMessage(IoSession connection, Object message) {
    
    Ping m = (Ping) message;
    
    long now = System.currentTimeMillis();
    
    long then = Long.parseLong(m.getId());
    
    
    LOG.info("Ping rountrip took: {} ms." , now - then );
    
  }

  
  
}
