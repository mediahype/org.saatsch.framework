package org.saatsch.framework.base.jfxbase.dataeditor;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import org.joda.beans.Bean;
import org.joda.beans.Property;

/**
 * TODO: set precision
 */
public class FloatEditor extends AbstractEditor {

  private final Spinner spinner;
  private final Label lblRange;

  // TODO: use this in setting the precision
  private final DataEditorFloat meta;

  public FloatEditor(Property<Object> property, Bean objectToEdit) {
    super(property, objectToEdit);

    meta = property.metaProperty().annotation(DataEditorFloat.class);
    spinner = new Spinner();
    spinner.setEditable(true);
    lblRange = new Label();

    getChildren().add(spinner);
    getChildren().add(lblRange);

    fillContents();

  }

  private void fillContents() {

    if (PropertyUtil.isPropertyAnnotatedWith(property, DataEditorFloat.class)) {

      DataEditorFloat meta = property.metaProperty().annotation(DataEditorFloat.class);

      DoubleSpinnerValueFactory factory = new DoubleSpinnerValueFactory(
          meta.lowerBound(), meta.upperBound(), (Float) property.get(), meta.increment());

      spinner.setValueFactory(factory);


      lblRange.setText(makeRangeString(meta));
    }

    spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
      property.set(newValue);
      saveObject();
    });

  }


  private static String makeRangeString(DataEditorFloat meta) {

    String ret = meta.lowerBound() + " ... " + meta.upperBound();
    return ret;
  }
}
