package org.saatsch.propertybinding;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;

public class BindingTest {

  @Test
  public void test(){

    SimpleStringProperty p1 = new SimpleStringProperty("Hello ");
    SimpleStringProperty p2 = new SimpleStringProperty("World!");


    StringExpression concat = p1.concat(p2);

    // this adds an INVALIDATION listener
    // concat.addListener( observable -> System.out.println("notified!") );


    p2.set("Cosmos!");


    System.out.println(concat.get());


  }

}
