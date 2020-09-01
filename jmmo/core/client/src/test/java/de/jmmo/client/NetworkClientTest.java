package de.jmmo.client;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class NetworkClientTest {

  @Rule
  public LocalContext context = new LocalContext();
  
  @Test
  public void test()  {
    
    NetworkClient bean = context.getBean(NetworkClient.class);
    Assert.assertNotNull(bean);
    try {
      bean.connect();
    } catch (IOException e) {
    }
    
  }
  
}
