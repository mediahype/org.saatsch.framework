package org.saatsch.propertybinding;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Assert;
import org.junit.Test;

public class BindingTest {

  @Test
  public void stringTest(){
    Notifyable n = new Notifyable();
    SimpleStringProperty p1 = new SimpleStringProperty("Hello ");
    SimpleStringProperty p2 = new SimpleStringProperty("World!");
    StringExpression concat = p1.concat(p2);
    // this adds an INVALIDATION listener
    concat.addListener( observable -> n.notifyMe() );
    p2.set("Cosmos!");
    Assert.assertTrue(concat.get().equals("Hello Cosmos!"));
    Assert.assertTrue(n.wasNotifiedOnce());
  }

  @Test
  public void booleanTest(){
    SimpleBooleanProperty p1 = new SimpleBooleanProperty(true);
    SimpleBooleanProperty p2 = new SimpleBooleanProperty(false);
    BooleanBinding and = p1.and(p2);
    Assert.assertEquals(false, and.get());
    p2.set(true);
    Assert.assertEquals(true, and.get());
//    boolean b1 = and.get();
//    Boolean b2 = and.getValue();

  }

  @Test
  public void doubleTest(){
    SimpleDoubleProperty p1 = new SimpleDoubleProperty(1);
    SimpleDoubleProperty p2 = new SimpleDoubleProperty(2);
    DoubleBinding add = p1.add(p2);
    Assert.assertEquals(3, add.get(), 0.0);
    p1.set(0);
    Assert.assertEquals(2, add.get(), 0.0);
  }

  @Test
  public void integerTest(){
    SimpleIntegerProperty p1 = new SimpleIntegerProperty(1);
    SimpleIntegerProperty p2 = new SimpleIntegerProperty(2);
    NumberBinding add = p1.add(p2);
    Assert.assertEquals(3, add.intValue());
    p1.set(0);
    Assert.assertEquals(2, add.intValue());
  }

}
