package org.saatsch.framework.jmmo.server.network.handlers;

import org.apache.mina.core.session.IoSession;

import org.saatsch.framework.jmmo.basegame.common.Ping;
import org.saatsch.framework.jmmo.networking.api.ANetworkMessageHandler;
import org.saatsch.framework.jmmo.networking.api.NetworkMessageHandler;
import org.saatsch.framework.jmmo.server.tasks.PingTask;

@ANetworkMessageHandler(listenTo = {Ping.class})
public class PingHandler implements NetworkMessageHandler {

  @Override
  public void handleMessage(IoSession connection, Object message) {
    
    new PingTask((Ping) message, connection).enqueue();
    
  }

  
  
}
