package org.saatsch.framework.jmmo.data.editor.fx.eventing;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eventing extends EventBus {

  private static final Logger LOG = LoggerFactory.getLogger(Eventing.class);

  public Eventing() {
    super("jmmoEventing");
  }

  @Override
  public void post(Object event) {
    LOG.debug("Event posted: {} : {}", event.getClass(), event);
    super.post(event);
  }


}
