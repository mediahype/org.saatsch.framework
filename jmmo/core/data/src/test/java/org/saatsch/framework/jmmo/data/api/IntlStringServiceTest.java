package org.saatsch.framework.jmmo.data.api;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class IntlStringServiceTest {

  @Rule
  public LocalRule ctx = new LocalRule();
  
  @Test
  public void test() {
    
    
      IntlStringService stringService = ctx.getBean(IntlStringService.class);
      stringService.saveLocalizedText("testCoordinate", "TestContent");
      String localizedText = stringService.loadLocalizedText("testCoordinate");
      
      Assert.assertEquals("TestContent", localizedText);
      
      
      
  }
  
}
