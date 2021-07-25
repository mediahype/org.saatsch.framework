package org.saatsch.framework.jmmo.server.network.handlers;

import org.apache.mina.core.session.IoSession;
import org.saatsch.framework.jmmo.basegame.common.resources.GiveResourceRequest;
import org.saatsch.framework.jmmo.basegame.common.resources.GiveStringsRequest;
import org.saatsch.framework.jmmo.networking.api.ANetworkMessageHandler;
import org.saatsch.framework.jmmo.networking.api.NetworkMessageHandler;
import org.saatsch.framework.jmmo.server.tasks.ProcessResourceTask;

@ANetworkMessageHandler(listenTo = {GiveStringsRequest.class})
public class ResourceRequestHandler implements NetworkMessageHandler {


  @Override
  public void handleMessage(IoSession connection, Object message) {

    new ProcessResourceTask((GiveResourceRequest) message, connection).enqueue();

  }
}
