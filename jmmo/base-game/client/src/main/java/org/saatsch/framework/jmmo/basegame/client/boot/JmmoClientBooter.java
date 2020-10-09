package org.saatsch.framework.jmmo.basegame.client.boot;

import org.saatsch.framework.jmmo.basegame.client.Eventing;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.client.ClientConfig;
import org.saatsch.framework.jmmo.data.mongo.MorphiaMongoDataSink;
import org.saatsch.framework.jmmo.data.mongo.NoopDataSink;

/**
 * entry point to the additional boot process. It depends on the {@link JmmoContext}, but does not
 * start it and thus does not depend on the CDI portion of the {@link JmmoContext}.
 * 
 * @author saatsch
 * 
 */
public class JmmoClientBooter {

  
  /**
   * implements boot process. This must be called at the very beginning of the program. It creates
   * {@link Eventing} and {@link ClientConfig}.
   * 
   */
  public JmmoClientBooter() {

    // initialize the Client EventBus
    JmmoContext.putBean(new Eventing());
    JmmoContext.putBean(new ClientConfig());
    
    JmmoContext.alias(MorphiaMongoDataSink.class, NoopDataSink.class);
    
  }
  

}
