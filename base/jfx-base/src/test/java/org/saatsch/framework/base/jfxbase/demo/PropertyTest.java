package org.saatsch.framework.base.jfxbase.demo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;

/**
 * simple Demo of {@link Bindings}.
 */
public class PropertyTest {

  @Test
  public void test(){

    SimpleStringProperty ssp1 = new SimpleStringProperty("ssp1 ");
    SimpleStringProperty ssp2 = new SimpleStringProperty("ssp2");
    StringExpression concat = Bindings.concat(ssp1, ssp2);

    ssp1.bind(ssp2);

    System.out.println(ssp1.get() + " ::: " + ssp2.get());
    System.out.println(ssp1.isBound() + " ::: " + ssp2.isBound());
    System.out.println(ssp1.getBean() + " ::: " + ssp2.getBean());
    System.out.println(concat.getValue());
  }
}
