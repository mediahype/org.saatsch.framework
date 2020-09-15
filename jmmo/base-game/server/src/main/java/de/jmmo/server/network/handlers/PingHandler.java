package de.jmmo.server.network.handlers;

import org.apache.mina.core.session.IoSession;

import de.jmmo.basegame.common.Ping;
import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;
import de.jmmo.server.tasks.PingTask;

@ANetworkMessageHandler(listenTo = {Ping.class})
public class PingHandler implements NetworkMessageHandler {

  @Override
  public void handleMessage(IoSession connection, Object message) {
    
    new PingTask((Ping) message, connection).enqueue();
    
  }

  
  
}
