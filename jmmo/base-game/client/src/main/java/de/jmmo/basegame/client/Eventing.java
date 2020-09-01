package de.jmmo.basegame.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

/**
 * An {@link EventBus} for a jmmo client. The consumer keeps one instance of this class per jvm.
 * 
 * @author saatsch
 * 
 */
public class Eventing extends EventBus {
  private static final Logger LOG = LoggerFactory.getLogger(Eventing.class);

  public Eventing() {

    super("osrgEventing");

  }

  @Override
  public void post(Object event) {
    LOG.debug("Event posted: {} : {}", event.getClass(), event);
    super.post(event);
  }

}
