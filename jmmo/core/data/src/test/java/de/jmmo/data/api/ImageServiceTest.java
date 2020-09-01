package de.jmmo.data.api;

import org.junit.Rule;
import org.junit.Test;

import de.jmmo.data.DataException;

public class ImageServiceTest {

  @Rule
  public LocalRule ctx = new LocalRule();
  
  @Test(expected = DataException.class)
  public void test() {
    ImageService service = ctx.getBean(ImageService.class);
    
    service.getImageAsStream("doesNotExist");
    
  }
  
}
