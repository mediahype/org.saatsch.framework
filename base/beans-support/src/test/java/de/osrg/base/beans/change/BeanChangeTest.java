package de.osrg.base.beans.change;

import org.joda.beans.Bean;
import org.junit.Assert;
import org.junit.Test;

public class BeanChangeTest {

  
  private static final String NEW_STRING_VALUE = "new String Value";


  @Test
  public void simpleTest() {
    // given
    Bean bean = new TopLevelBean();
    
    // when
    PropertyChange change = new PropertyChange();
    change.addToPath(PropertyPathElement.builder().propName("stringProperty").build());
    change.setValue(NEW_STRING_VALUE);
    
    ChangeApplier.apply(change, bean);
    
    // then
    Assert.assertTrue(bean.property("stringProperty").get().equals(NEW_STRING_VALUE));
    
  }
  
  @Test
  public void nestedBeanTest() {
    // given
    Bean bean = new TopLevelBean();
    
    PropertyChange change = new PropertyChange();
    change.addToPath(PropertyPathElement.builder().propName("nestedBean").build());
    change.addToPath(PropertyPathElement.builder().propName("stringPropertyInNestedBean").build());
    
    change.setValue(NEW_STRING_VALUE);
    
    // when 
    ChangeApplier.apply(change, bean);
    
    // then
    Bean nestedBean = (Bean) bean.property("nestedBean").get();
    Assert.assertTrue(nestedBean.property("stringPropertyInNestedBean").get().equals(NEW_STRING_VALUE));
    
  }
  
  @Test
  public void listBeanTest() {
    //     given
   TopLevelBean bean = new TopLevelBean();
   bean.getListOfNestedBeans().add(new NestedBean());
   
   PropertyChange change = new PropertyChange();
   change.addToPath(PropertyPathElement.builder().propName("listOfNestedBeans").indexInList(0).build());
   change.addToPath(PropertyPathElement.builder().propName("stringPropertyInNestedBean").indexInList(0).build());
   change.setValue(NEW_STRING_VALUE);

   // when 
   ChangeApplier.apply(change, bean);

   //  then
   Bean targetBean = bean.getListOfNestedBeans().get(0);
   Assert.assertTrue(targetBean.property("stringPropertyInNestedBean").get().equals(NEW_STRING_VALUE));
   
  }
  
  
  @Test
  public void addToListTest() {
    // given
    TopLevelBean bean = new TopLevelBean();
    
    PropertyChange change = new PropertyChange();
    change.addToPath(PropertyPathElement.builder().propName("listOfNestedBeans").indexInList(0).build());
    change.setValue(new NestedBean());
    change.setOperation(ChangeOp.ADD);
    
    //when
    ChangeApplier.apply(change, bean);
    
    // then
    Assert.assertTrue( bean.getListOfNestedBeans().size() == 1);
        
  }
  
  
  
}
