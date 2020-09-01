package de.osrg.base.jface.beantree;

import org.joda.beans.MetaProperty;
import org.junit.Assert;
import org.junit.Test;



public class BeanTreeUtilTest {

  
  @Test
  public void test() {
    
    MetaProperty<Object> stringList = TestBean.meta().metaProperty("stringList");
    Class<?> stringTypeArg = BeanTreeUtil.firstTypeArg(stringList);
    Assert.assertTrue(stringTypeArg.equals(String.class));

    
    MetaProperty<Object> wildcardList = TestBean.meta().metaProperty("wildcardList");
    Class<?> wildcardTypeArg = BeanTreeUtil.firstTypeArg(wildcardList);
    Assert.assertNull(wildcardTypeArg);
    
    
    MetaProperty<Object> string = TestBean.meta().metaProperty("string");
    Class<?> noTypeArg = BeanTreeUtil.firstTypeArg(string);
    Assert.assertNull(noTypeArg);
    
  }
  
}
