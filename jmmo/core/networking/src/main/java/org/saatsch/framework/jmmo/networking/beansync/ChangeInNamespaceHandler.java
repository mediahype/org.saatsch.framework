package org.saatsch.framework.jmmo.networking.beansync;

import org.apache.mina.core.session.IoSession;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.networking.api.ANetworkMessageHandler;
import org.saatsch.framework.jmmo.networking.api.NetworkMessageHandler;
import org.saatsch.framework.base.beans.change.BeanNamespace;
import org.saatsch.framework.base.beans.change.ChangeInNamespace;
import org.saatsch.framework.base.beans.change.NamespaceChangeApplier;

@ANetworkMessageHandler(listenTo = {ChangeInNamespace.class})
public class ChangeInNamespaceHandler implements NetworkMessageHandler {


  @Override
  public void handleMessage(IoSession connection, Object message) {
    BeanNamespace namespace = JmmoContext.getBean(BeanNamespace.class);
    ((ChangeInNamespace) message).getChanges().stream().forEach(change -> NamespaceChangeApplier.apply(change, namespace));

  }
}
