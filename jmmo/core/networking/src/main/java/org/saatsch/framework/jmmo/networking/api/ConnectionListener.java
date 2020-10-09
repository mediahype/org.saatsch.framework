package org.saatsch.framework.jmmo.networking.api;

import org.apache.mina.core.session.IoSession;

public interface ConnectionListener {

  void exceptionCaught(IoSession session, Throwable cause);

  void sessionClosed(IoSession session);

}
