package org.saatsch.framework.jmmo.client;

import org.junit.Assert;
import org.junit.Test;

public class ClientConfigTest {


  @Test
  public void test() {
    ClientConfig conf = new ClientConfig();
    Assert.assertNotNull(conf.getServerLocations()); 
  }
}
