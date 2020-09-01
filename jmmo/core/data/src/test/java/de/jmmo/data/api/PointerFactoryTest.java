package de.jmmo.data.api;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jmmo.data.ExampleBean;

public class PointerFactoryTest {

  
  @Test
  public void test() {
    
    Pointer<Object> classPointer = PointerFactory.newPointer(Object.class);
    assertNotNull(classPointer);
    assertFalse(classPointer.isValid());
    
    Pointer objectPointer = PointerFactory.newPointer(new ExampleBean());
    assertNotNull(objectPointer);
    assertTrue(objectPointer.isValid());
    
    Pointer<ExampleBean> typesafePointer = PointerFactory.newPointer(ExampleBean.class, new ExampleBean());
    assertNotNull(typesafePointer);
    
  }
  
}
