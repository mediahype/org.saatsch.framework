package de.jmmo.server.tasks;

import org.apache.mina.core.session.IoSession;

import de.jmmo.basegame.common.Ping;
import de.jmmo.clustering.SessionLocalTask;

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
