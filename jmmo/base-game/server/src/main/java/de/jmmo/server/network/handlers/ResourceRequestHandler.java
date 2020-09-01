package de.jmmo.server.network.handlers;

import org.apache.mina.core.session.IoSession;

import de.jmmo.basegame.common.resources.GiveResourceRequest;
import de.jmmo.basegame.common.resources.GiveStringsRequest;
import de.jmmo.basegame.common.resources.ResolveImagePointerRequest;
import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;
import de.jmmo.server.tasks.ProcessResourceTask;

@ANetworkMessageHandler(listenTo = {ResolveImagePointerRequest.class, GiveStringsRequest.class})
public class ResourceRequestHandler implements NetworkMessageHandler {


  @Override
  public void handleMessage(IoSession connection, Object message) {

    new ProcessResourceTask((GiveResourceRequest) message, connection).enqueue();

  }
}
