package org.saatsch.framework.base.jfxbase;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import org.junit.Test;
import org.saatsch.framework.base.jfxbase.binding.RegexBinding;
import org.saatsch.framework.base.jfxbase.control.*;
import org.saatsch.framework.base.jfxbase.demo.AbstractDemo;

public class RegexBindingDemo extends AbstractDemo {

  @Test
  public void test() {
    Application.launch(this.getClass());
  }

  @Override
  protected void fill(VBox root) {

    GridPane content = new GridPane().withColumns(2).withStretchedColumn(1);

    new Label("Text to test: ").withParent(content).withLayoutColRow(0,0);
    TextField txtTest = new TextField("contains one forward / slash").withParent(content).withLayoutColRow(1, 0);

    new Label("Regex:").withParent(content).withLayoutColRow(0,1);
    StringProperty regex = new TextField(".*.\\/.*.").withParent(content).withLayoutColRow(1,1).withMonospace().textProperty();

    ObservableBooleanValue matches = RegexBinding.matches(txtTest.textProperty(), regex);

    new Label("matches (at least once):").withParent(content).withLayoutColRow(0,2);
    CheckBox checkBox = new CheckBox().withParent(content).withLayoutColRow(1,2);
    checkBox.disableProperty().setValue(true);

    checkBox.selectedProperty().bind(matches);

    root.withChildren(content);



  }
}
