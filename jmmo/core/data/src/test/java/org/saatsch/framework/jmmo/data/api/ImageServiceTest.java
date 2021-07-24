package org.saatsch.framework.jmmo.data.api;

import org.junit.Rule;
import org.junit.Test;

import org.saatsch.framework.jmmo.data.DataException;

public class ImageServiceTest {

  @Rule
  public LocalRule ctx = new LocalRule();
  
  @Test(expected = DataException.class)
  public void test() {
    FileService service = ctx.getBean(FileService.class);
    
    service.getFileAsStream("doesNotExist");
    
  }
  
}
