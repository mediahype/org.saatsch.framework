package org.saatsch.framework.jmmo.networking.impl.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.service.AbstractIoService;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;

/**
 * Server for JMMO. Note that the containing module (jmmo.core.networking) will exist on client and
 * on server and it is probably a security consideration whether or not to have the Server class on
 * the client.
 * 
 * @author saatsch
 *
 */
public class NetworkServer {

  private static final Logger LOG = LoggerFactory.getLogger(NetworkServer.class);

  private Lazy<ObjectProtocolHandler> handler =
      Lazy.of(() -> JmmoContext.getBean(ObjectProtocolHandler.class));

  private Lazy<NetworkServerProperties> props =
      Lazy.of(() -> JmmoContext.getBean(NetworkServerProperties.class));

  private NioSocketAcceptor s;

  public NetworkServer() {
    init();
  }

  /**
   * creates a server that listens on the configured tcpPort. The server is capable of receiving
   * messages that are java objects.
   * 
   * @return
   * @throws IOException
   */
  private NioSocketAcceptor createServer() throws IOException {

    NioSocketAcceptor acceptor = new NioSocketAcceptor();

    acceptor.getFilterChain().addLast("codec",
        new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));


    acceptor.setHandler(handler.get());
    acceptor.bind(new InetSocketAddress(props.get().getServerTcpPort()));

    return acceptor;
  }

  private void init() {
    LOG.info("Server init... Port:{}", props.get().getServerTcpPort());
    try {
      s = createServer();
    } catch (IOException e) {
      throw new RuntimeException("Cannot start server.", e);
    }

  }

  /**
   * delegates to {@link AbstractIoService#getManagedSessions()}
   */
  public Map<Long, IoSession> getSessions() {
    return s.getManagedSessions();
  }

}
