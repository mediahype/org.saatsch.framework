package org.saatsch.framework.base.jfxbase.demo;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.control.*;

public class BindingDemo extends AbstractDemo {

  @Override
  protected void fill(VBox root) {

    GridPane content = new GridPane().withColumns(2);

    new Label("Body:").withParent(content).withLayoutColRow(0, 0);

    Spinner<Integer> bodySpinner = new Spinner<Integer>(1, 6, 1).withParent(content).withLayoutColRow(1, 0);

    new Label("Hitpoints").withParent(content).withLayoutColRow(0, 1);


    IntegerExpression bodyProperty = IntegerProperty.integerExpression(bodySpinner.getValueFactory().valueProperty());


    new Label().withParent(content).withLayoutColRow(1, 1)
        .withTextProperty(Bindings.multiply(10, bodyProperty).asString());


    root.getChildren().add(content);

  }



  @Test
  public void test() {
    Application.launch(this.getClass());
  }

  @Test
  public void stringTest (){

    SimpleStringProperty p1 = new SimpleStringProperty("Hello ");
    SimpleStringProperty p2 = new SimpleStringProperty("World!");


    StringExpression concat = p1.concat(p2);

    // this adds an INVALIDATION listener
    // concat.addListener( observable -> System.out.println("notified!") );


    p2.set("Cosmos!");


    System.out.println(concat.get());


  }

}
