package de.jmmo.data.mongo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.jmmo.data.ExampleBean;

public class ModelManagerTest {

  
  @Test
  public void test() throws Exception {

    Map<String, List<String>> classMappings = new HashMap<>();
    
    List<String> x = new ArrayList<>();
    x.add(ExampleBean.class.getName());
    
    classMappings.put(ExampleBean.class.getName(), x);
    
    ModelManager mm = new ModelManager(classMappings);
    
    Assert.assertTrue(mm.getValidator().isValidated());
    
  }
  
}
