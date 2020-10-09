package org.saatsch.framework.jmmo.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.joda.beans.MetaProperty;
import org.junit.Assert;
import org.junit.Test;

import org.saatsch.framework.jmmo.data.api.Pointer;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class PropertyUtilTest {


  @Test
  public void test() {

    ExampleBean bean = new ExampleBean();

    Assert.assertTrue(
        PropertyUtil.genericClass(bean.property("stringProperty").metaProperty()) == String.class);

    // listProperty is a List<String>
    Assert.assertTrue(
        PropertyUtil.genericClass(bean.property("listProperty").metaProperty()) == String.class);
    Assert.assertTrue(
        PropertyUtil.firstTypeArg(bean.property("listProperty").metaProperty()) == String.class);
    Assert.assertTrue(PropertyUtil
        .firstTypeArgRecurse(bean.property("listProperty").metaProperty()) == String.class);


    // arrayListProperty is an ArrayList<String>
    Assert.assertTrue(PropertyUtil
        .genericClass(bean.property("arrayListProperty").metaProperty()) == String.class);

    // untypedListProperty is a List
    Assert.assertTrue(
        PropertyUtil.genericClass(bean.property("untypedListProperty").metaProperty()) == null);


    //
    Assert.assertTrue(Pointer.class
        .equals(PropertyUtil.genericClass(bean.property("listOfPointers").metaProperty())));


    Assert.assertTrue(
        PropertyUtil.firstTypeArg(bean.property("stringProperty").metaProperty()) == null);
    Assert.assertTrue(
        PropertyUtil.firstTypeArg(bean.property("pointer").metaProperty()) == ExampleBean.class);

    // the pointer property is not a collection
    Assert.assertFalse(PropertyUtil.isSupportedCollection(bean.property("pointer")));

    // the following are collections
    Assert.assertTrue(PropertyUtil.isSupportedCollection(bean.property("untypedListProperty")));
    Assert.assertTrue(PropertyUtil.isSupportedCollection(bean.property("arrayListProperty")));
    Assert.assertTrue(PropertyUtil.isSupportedCollection(bean.property("listProperty")));
    Assert.assertTrue(PropertyUtil.isSupportedCollection(bean.property("setProperty")));

    Assert.assertTrue(Pointer.class
        .equals(PropertyUtil.firstTypeArg(bean.property("listOfPointers").metaProperty())));

    Assert.assertTrue(String.class
        .equals(PropertyUtil.firstTypeArgRecurse(bean.property("listOfPointers").metaProperty())));

    Assert.assertTrue(String.class.equals(
        PropertyUtil.firstTypeArgRecurse(bean.property("listOfListOfPointers").metaProperty())));

    Assert.assertTrue(PropertyUtil.isBean(bean.property("nestedBean")));



  }

  @Test
  public void enumTest() {
    ExampleBean bean = new ExampleBean();

    // the given property is not an enum, so null is expected
    Assert.assertNull(PropertyUtil.getEnumConstants(bean.property("listProperty")));

    // the given property is an enum and has 3 constants
    Assert.assertTrue(PropertyUtil.getEnumConstants(bean.property("enumeration")).length == 3);

  }

  @Test
  public void getAppIdPropertyTest() {
    MetaProperty<?> appIdProperty = PropertyUtil.getAppIdProperty(ExampleBean.class);

    // ExampleBean has an appId property
    assertNotNull(appIdProperty);

    // and it's name is appId
    assertEquals("appId", PropertyUtil.getAppIdPropertyName(ExampleBean.class));

  }

  @Test
  public void initPointersTest() {

    ExampleBean bean = new ExampleBean();
    PropertyUtil.initPointers(bean);

    assertNotNull(bean.pointer().get().getBaseClass());

  }
  
  @Test
  public void visiblePropertiesTest() {
    ExampleBean bean = new ExampleBean();
    assertFalse(PropertyUtil.visiblePropertiesOf(bean).contains(bean.invisibleProperty()));
  
  }
  
  @Test
  public void isCollectionOfPointersTest() {
    ExampleBean bean = new ExampleBean();
    assertTrue(PropertyUtil.isCollectionOfPointers(bean.property("listOfPointers")));
    assertFalse(PropertyUtil.isCollectionOfPointers(bean.property("listOfListOfPointers")));
  }
  
}
