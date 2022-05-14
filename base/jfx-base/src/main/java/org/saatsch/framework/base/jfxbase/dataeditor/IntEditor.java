package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.joda.beans.Bean;
import org.joda.beans.Property;

public class IntEditor extends AbstractEditor {

  private final Spinner<Integer> spinner = new Spinner<>();
  private final Label lblRange;

  public IntEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    lblRange = new Label();
    spinner.setEditable(true);

    getChildren().add(spinner);
    getChildren().add(lblRange);

    fillContents();

  }

  private void fillContents() {

    if (PropertyUtil.isPropertyAnnotatedWith(property, DataEditorInt.class)) {
      DataEditorInt meta = property.metaProperty().annotation(DataEditorInt.class);
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

    spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
      property.set(newValue);
      saveObject();
    });


  }


  private static String makeRangeString(DataEditorInt meta) {

    String ret = String.valueOf(meta.lowerBound()) + " ... " + String.valueOf(meta.upperBound());
    return ret;
  }

}
