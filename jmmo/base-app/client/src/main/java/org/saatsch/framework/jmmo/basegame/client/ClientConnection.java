package org.saatsch.framework.jmmo.basegame.client;

import java.io.IOException;

import org.apache.mina.core.future.WriteFuture;

import org.saatsch.framework.jmmo.basegame.client.boot.ConnectionEvent;
import org.saatsch.framework.jmmo.basegame.client.eventing.SystemMessageEvent;
import org.saatsch.framework.jmmo.basegame.common.MessageSeverity;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.client.Eventing;
import org.saatsch.framework.jmmo.client.NetworkClient;

/**
 * This Bean wraps the Jmmo {@link NetworkClient}. It posts the following Events to
 * {@link Eventing}: {@link UserMessageEvent} of type error if the connection is already
 * established and {@link ConnectionEvent} if the connection failed or succeeded.
 * 
 * @author saatsch
 *
 */

public class ClientConnection {


  private NetworkClient networkClient = JmmoContext.getBean(NetworkClient.class);


  /**
   * connects and does not wait for the connection to be established. 
   */
  public synchronized void connect() {
    if (!networkClient.isConnected()) {
      // connect async so that the caller will not be blocked
      Thread t = new Thread(new Conn());
      t.start();
    } else {
      JmmoContext.getBean(Eventing.class)
          .post(new SystemMessageEvent(MessageSeverity.ERROR, "already connected."));
    }
  }

  public boolean isConnected() {
    return networkClient.isConnected();
  }

  /**
   * connects and waits until the connection is established or an error occurs.
   */
  public synchronized void connectSync() {
    internalConnect();
  }

  private void internalConnect() {
    try {
      JmmoContext.getBean(Eventing.class).post(ConnectionEvent.STARTING);
      networkClient.connect();
      JmmoContext.getBean(Eventing.class).post(ConnectionEvent.SUCCESS);
    } catch (IOException e) {
      JmmoContext.getBean(Eventing.class).post(ConnectionEvent.FAILED);
    }
  }

  /**
   * a {@link Runnable} that connects the network client:
   * 
   * @author saatsch
   *
   */
  private class Conn implements Runnable {
    @Override
    public void run() {
      internalConnect();
    }

  }

  public WriteFuture sendTCP(Object o) {
    return networkClient.sendTCP(o);
  }

  public void disconnect() {
    networkClient.shutdown();
    
  }

}
