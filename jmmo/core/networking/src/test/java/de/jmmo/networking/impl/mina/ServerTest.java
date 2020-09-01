package de.jmmo.networking.impl.mina;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.networking.impl.MessageHandlers;

/**
 * this test does not need the database
 * 
 * @author saatsch
 *
 */
public class ServerTest {

  private static final Logger LOG = LoggerFactory.getLogger(ServerTest.class);
  
  @Rule
  public LocalContext container = new LocalContext();

  @Test
  public void networkServerTest() throws InterruptedException {

    try {
      Assert.assertNotNull(container.getBean(NetworkServer.class));      
    }catch (RuntimeException e) {
      LOG.error("Error: ", e);
      throw e;
    }

  }
  
  
  @Test
  public void messagesAndHandlersTest() {
    
    MessageHandlers bean = container.getBean(MessageHandlers.class);
    
    Assert.assertNotNull(bean); 
    Assert.assertNotNull(bean.getHandlerClasses()); 
    
  }

}
