package org.saatsch.framework.jmmo.data.editor.fx.types;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.saatsch.framework.jmmo.data.annotations.JmmoInt;
import org.saatsch.framework.jmmo.data.api.PropertyUtil;

public class IntEditor extends AbstractEditor {

  private final Spinner spinner;
  private final Label lblRange;

  public IntEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    spinner = new Spinner();
    lblRange = new Label();

    getChildren().add(spinner);
    getChildren().add(lblRange);

    fillContents();

  }

  private void fillContents() {

    if (PropertyUtil.isPropertyAnnotatedWith(property, JmmoInt.class)) {
      JmmoInt meta = property.metaProperty().annotation(JmmoInt.class);
      lblRange.setText(makeRangeString(meta));

      spinner.setValueFactory(
          new SpinnerValueFactory.IntegerSpinnerValueFactory(meta.lowerBound(), meta.upperBound(),
              (Integer) property.get()
          ));


    } else {
      spinner.setValueFactory(
          new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE,
              (Integer) property.get()
          ));


    }




  }


  private static String makeRangeString(JmmoInt meta) {

    String ret = String.valueOf(meta.lowerBound()) + " ... " + String.valueOf(meta.upperBound());
    return ret;
  }

}
