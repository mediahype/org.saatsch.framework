package org.saatsch.framework.jmmo.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.networking.api.ConnectionListener;
import org.saatsch.framework.jmmo.networking.impl.mina.ObjectProtocolHandler;

/**
 * 
 * @author saatsch
 * 
 */
public class NetworkClient implements ConnectionListener {

  private static final Logger LOG = LoggerFactory.getLogger(NetworkClient.class);
  private IoSession session;
  private ClientConfig config = new ClientConfig();
  private NioSocketConnector connector;
  private boolean shutdown = false;
  private boolean connected;


  public NetworkClient() {
    init();
  }
  
  
  
  public void init() {
    if (shutdown){
      LOG.info("Network client NOT initializing as it is shutdown.");
    }else{
      LOG.info("Network client initializing ...");
    }
  }

  /**
   * shutdown this client.
   */
  public void shutdown() {
    
    if (null != session) {
      session.closeNow().awaitUninterruptibly();
    }
    shutdown = true;
    connected = false;
    LOG.info("Network client shut down ...");
  }



  /**
   * calls {@link IoSession#write(Object)}
   * 
   * @param o the Object to send
   * @return the {@link WriteFuture}
   */
  public WriteFuture sendTCP(Object o) {
    if (session == null) {
      LOG.error("NetworkClient not connected.");
      return null;
    }
    return session.write(o);
  }

  /**
   * tries to connect the client to the server
   * 
   * @throws IOException when the connection to the server failed.
   */
  public void connect() throws IOException {

    if (null == session) {
      connector = new NioSocketConnector();
      connector.setHandler(JmmoContext.getBean(ObjectProtocolHandler.class));

      connector.getFilterChain().addLast("codec",
          new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

      
      newConnect();
      
      if (!connected) {
        legacyConnect();        
      } 
    }
  }



  /**
   * new method of connecting using alternative servers
   */
  private void newConnect() {
    config.getServerLocations().ifPresent(locs -> {
      String[] locations = locs.split(",");
      
      for (String loc : locations) {
        String[] serverAndPort = loc.trim().split(":");
        if (tryConnect(serverAndPort[0], Integer.parseInt(serverAndPort[1] ))) {
          break;
        }
      }
    });
  }



  private void legacyConnect() throws IOException {
    if (!tryConnect(config.getServerName(), config.getServerTcpPort())) {
      throw new IOException("Failed to connect to server");
    }
  }



  private boolean tryConnect(String server, Integer port){
    LOG.info("Trying to connect to: {}:{}", server, port);
    
    ConnectFuture connFuture =
        connector.connect(new InetSocketAddress(server, port));
    connFuture.awaitUninterruptibly();
    try {
      session = connFuture.getSession();
    } catch (RuntimeIoException e) {
      return false;
    }
    connected=true;
    return true;
  }

  @Override
  public void exceptionCaught(IoSession session, Throwable cause) {
    LOG.error("Exception in Network Client: ", cause);
  }

  // Observation: this happens when the server is stopped.
  @Override
  public void sessionClosed(IoSession session) {
    LOG.warn("Session to server closed!");
    if (null != connector) {
      connector.dispose(false);      
    }
    this.session = null;
    connected = false;
    
  }

  public boolean isConnected() {
    return connected;
  }
  
}

