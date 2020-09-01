package de.jmmo.networking.beansync;

import org.apache.mina.core.session.IoSession;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.networking.api.ANetworkMessageHandler;
import de.jmmo.networking.api.NetworkMessageHandler;
import de.osrg.base.beans.change.BeanNamespace;
import de.osrg.base.beans.change.ChangeInNamespace;
import de.osrg.base.beans.change.NamespaceChangeApplier;

@ANetworkMessageHandler(listenTo = {ChangeInNamespace.class})
public class ChangeInNamespaceHandler implements NetworkMessageHandler {


  @Override
  public void handleMessage(IoSession connection, Object message) {
    BeanNamespace namespace = JmmoContext.getBean(BeanNamespace.class);
    ((ChangeInNamespace) message).getChanges().stream().forEach(change -> NamespaceChangeApplier.apply(change, namespace));

  }
}
