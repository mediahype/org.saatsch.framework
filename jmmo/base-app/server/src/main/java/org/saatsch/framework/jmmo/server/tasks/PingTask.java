package org.saatsch.framework.jmmo.server.tasks;

import org.apache.mina.core.session.IoSession;

import org.saatsch.framework.jmmo.basegame.common.Ping;
import org.saatsch.framework.jmmo.clustering.SessionLocalTask;

public class PingTask extends SessionLocalTask {

  private Ping message;

  public PingTask(Ping message, IoSession session) {
    super(session);
    this.message = message;
  }

  @Override
  public void runTask() {
    respond(message);
  }

}
