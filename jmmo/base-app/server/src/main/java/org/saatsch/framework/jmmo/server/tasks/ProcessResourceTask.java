package org.saatsch.framework.jmmo.server.tasks;

import org.apache.mina.core.session.IoSession;
import org.saatsch.framework.jmmo.basegame.common.resources.GiveResourceRequest;
import org.saatsch.framework.jmmo.basegame.common.resources.GiveStringsRequest;
import org.saatsch.framework.jmmo.basegame.common.resources.GiveStringsResponse;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.clustering.SessionLocalTask;
import org.saatsch.framework.jmmo.data.api.model.IntlString;
import org.saatsch.framework.jmmo.data.DataSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * processes a resource request
 */
public class ProcessResourceTask extends SessionLocalTask {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessResourceTask.class);

  private GiveResourceRequest request;

  public ProcessResourceTask(GiveResourceRequest request, IoSession session) {
    super(session);
    this.request = request;
  }

  @Override
  public void runTask() {

    LOG.info("ProcessResourceRequest");
    
    if (request instanceof GiveStringsRequest) {
      giveStrings();
    }
    
    

  }

  /**
   * responds with all available strings.
   */
  private void giveStrings() {

    DataSink data = JmmoContext.getBean(DataSink.class);
    
    respond(new GiveStringsResponse(data.getAll(IntlString.class)));
    
  }



}
